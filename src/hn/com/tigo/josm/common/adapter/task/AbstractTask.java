/**
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.adapter.task;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import hn.com.tigo.josm.common.adapter.AbstractAdapter;
import hn.com.tigo.josm.common.adapter.AdapterConstants;
import hn.com.tigo.josm.common.adapter.AdapterValidationType;
import hn.com.tigo.josm.common.adapter.config.AdapterConfig;
import hn.com.tigo.josm.common.adapter.config.MockTaskConfig;
import hn.com.tigo.josm.common.adapter.dto.ParameterType;
import hn.com.tigo.josm.common.adapter.dto.TaskRequestType;
import hn.com.tigo.josm.common.adapter.dto.TaskResponseType;
import hn.com.tigo.josm.common.exceptions.AdapterException;
import hn.com.tigo.josm.common.exceptions.enumerators.AdapterErrorCode;
import hn.com.tigo.josm.common.interfaces.MonitoringManagerLocal;
import hn.com.tigo.josm.common.jmx.event.ConnectionRefusedEventType;
import hn.com.tigo.josm.common.jmx.event.MXBeanType;
import hn.com.tigo.josm.common.jmx.event.PerformanceEvent;
import hn.com.tigo.josm.common.util.TaskMessage;
import hn.com.tigo.josm.common.validator.DatatypeFactoryImp;

/**
 * The Class AbstractTask, it is responsible for executing and managing
 * operations' tasks.
 *
 * @author Camilo Gutierrez <mailto:cgutierrez@stefanini.com />
 * @author Harold Castillo
 * @version 2.1.0
 * @param <R>
 *            Generic type that represents the request driver type.
 * @param <D>
 *            Generic type that represents the driver's type.
 * @since 06/10/2014 03:30:40 PM
 */
public abstract class AbstractTask<R, D> {

	/** Attribute that determine {@link Logger} instance. */
	private final transient Logger LOGGER = Logger.getLogger(this.getClass());

	/** Attribute that determine the interface for writing and reading in JMX. */
	@EJB
	private MonitoringManagerLocal monitoringManager;

	/** Attribute that determine the driver request instance. */
	protected R request;

	/** This flag indicates if it is necessary remove the driver reference. */
	protected Boolean flagRemoveDriver;

	/**
	 * Instantiates a new super instance.
	 */
	public AbstractTask() {

	}

	/**
	 * Method responsible for obtaining a singleton instance that manages the
	 * adapter.
	 * 
	 * @return the {@link AbstractAdapter} singleton instance
	 */
	protected abstract AbstractAdapter<D> getSingletonAdapter();

	/**
	 * Execute task.
	 *
	 * @param taskType
	 *            the request type instance for communication with the
	 *            implementation tasks
	 * @return the task response type
	 * @throws AdapterException
	 *             the {@link AdapterException} it's throw by the method when it
	 *             catch the await termination of the {@link ExecutorService} or
	 *             when the thread of the {@link TaskExecutor} it is interrupted or
	 *             if the task it's which throws the exception:
	 * @see AdapterException
	 * @see TaskExecutor
	 */
	@WebMethod
	public TaskResponseType executeTask(final TaskRequestType taskType) throws AdapterException {

		final String taskName = parseName(this.getClass().getSimpleName());
		LOGGER.info(String.format(TaskMessage.INIT_TASK.getValue(), taskName, taskType.getTransactionId(), this.getHostAddress()));

		TaskResponseType response = null;
		boolean result = false;
		final long initTransaction = System.currentTimeMillis();
		final AbstractAdapter<D> singletonAdapter = getSingletonAdapter();
		final String adapterName = singletonAdapter.getAdapterSimpleName();
		final AdapterConfig config = singletonAdapter.getConfigurationType();
		
		this.validateThroughput(adapterName, singletonAdapter, config);

		final ExecutorService executor = Executors.newSingleThreadExecutor(new TaskThreadFactory(adapterName));
		this.flagRemoveDriver = false;
		this.request = this.createRequest(taskType);

		try {
			final TaskExecutor<R, D> taskExecutor = new TaskExecutor<R, D>(this, this.monitoringManager);
			final Future<TaskResponseType> future = executor.submit(taskExecutor);
			executor.shutdown();

			LOGGER.info(String.format(TaskMessage.REQUEST_TIMEOUT.getValue(),
					config.getDriverConfig().getTimeout()));
			final long timeOut = config.getDriverConfig().getTimeout();

			if (!executor.awaitTermination(timeOut, TimeUnit.MILLISECONDS)) {
				future.cancel(true);
				final String messageError = String.format(AdapterErrorCode.TIMEOUT_CONNECTION_ERROR.getMessage(),
						InetAddress.getLocalHost().getHostAddress(), adapterName);
				LOGGER.error(messageError);
				throw new AdapterException(AdapterErrorCode.TIMEOUT_CONNECTION_ERROR.getError(), messageError);
			}
			response = future.get();
			result = true;
		} catch (InterruptedException | ExecutionException | UnknownHostException e) {
			LOGGER.error(String.format(TaskMessage.THREAD_ERROR.getValue(), taskName, e.getMessage()), e);

			if (e.getCause() instanceof AdapterException) {
				throw (AdapterException) e.getCause();
			}

			throw new AdapterException(AdapterErrorCode.INTERNAL_EXECUTOR_ERROR, e.getMessage(), e);
		} catch (AdapterException e) {
			LOGGER.error(String.format(TaskMessage.ADAPTER_ERROR.getValue(), taskName, e.getMessage()), e);
			throw (e);
		} finally {
			LOGGER.info("End Task: " + taskName);

			final PerformanceEvent event = new PerformanceEvent(MXBeanType.DRIVER);
			event.setComponent(adapterName);
			event.setObjectName(adapterName);
			event.setResult(result, System.currentTimeMillis() - initTransaction);
			this.monitoringManager.receiveEvent(event);

			executor.shutdownNow();
		}
		LOGGER.info(response.getResponseDescription());
		return response;
	}

	/**
	 * Responsible method of executing a specific task through the driver instance.
	 *
	 * @param driver
	 *            the driver instance, this instance it's created in the adapter and
	 *            called by the {@link TaskExecutor}
	 * @return the task response type instance
	 * @throws AdapterException
	 *             the adapter exception it's threw by the specific task
	 */
	protected abstract TaskResponseType executeDriverTask(final D driver) throws AdapterException;

	/**
	 * Method responsible for creating the request of the driver.
	 *
	 * @param taskType
	 *            the task type instance
	 * @return the request instance
	 * @throws AdapterException
	 *             the adapter exception for some reason in the creation of the
	 *             request
	 */
	protected abstract R createRequest(final TaskRequestType taskType) throws AdapterException;

	/**
	 * Method responsible to return the client IP address.
	 *
	 * @return the raw IP address in a string format.
	 */
	protected String getHostAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			LOGGER.warn(String.format(TaskMessage.UNKNOWN_HOST_ERROR.getValue(), e.getMessage()));
		}
		return AdapterConstants.UNKNOWN_HOST;
	}

	/**
	 * Method responsible for validating the drivers' throughput.
	 *
	 * @param adapterName
	 *            the adapter implementation name
	 * @param singleton
	 *            the singleton adapter instance
	 * @throws AdapterException
	 *             the adapter exception it's throw if the throughput has been
	 *             exceeded
	 */
	private void validateThroughput(final String adapterName, final AbstractAdapter<D> singleton, final AdapterConfig config)
			throws AdapterException {
		final long tps = monitoringManager.getTPS(adapterName, adapterName);
		if (tps >= config.getMaxThroughput()) {
			singleton.registerConnectionRefused(ConnectionRefusedEventType.THROUGHPUT);
			final String messageError = String.format(AdapterErrorCode.MAX_THROUGHPUT_ERROR.getMessage(), adapterName);
			LOGGER.error(messageError);
			throw new AdapterException(AdapterErrorCode.MAX_THROUGHPUT_ERROR, messageError);
		}
	}

	/**
	 * Prints a {@link ParameterType} value.
	 *
	 * @param name
	 *            the name of the parameter
	 * @param value
	 *            the value of the parameter
	 */
	protected void printParameterValue(final String name, final String value) {

		if (value != null) {
			final String paramsLogBase = "Parameter <%s, %s>";
			final String paramsListMsg = String.format(paramsLogBase, name, value);
			LOGGER.info(paramsListMsg);
		}
	}

	/**
	 * Method responsible for validating a specific parameter of the
	 * {@link TaskRequestType} instance.
	 *
	 * @param <A>
	 *            the generic type
	 * @param paramName
	 *            the parameter name
	 * @param paramValue
	 *            the parameter value
	 * @param validationType
	 *            the validation instance type
	 * @param required
	 *            the required
	 * @throws AdapterException
	 *             the adapter exception if the parameter it's doesn't valid
	 * @see AdapterValidationType
	 */
	protected <A extends AbstractAdapter<?>> void validateParameter(final String paramName, final String paramValue,
			final AdapterValidationType validationType, final boolean required) throws AdapterException {
		validateParameter(paramName, paramValue, validationType.getValidationExpression(), required);
	}

	/**
	 * Method responsible for validating a specific parameter of the
	 * {@link TaskRequestType} instance.
	 *
	 * @param paramName
	 *            the parameter name
	 * @param paramValue
	 *            the parameter value
	 * @param expression
	 *            the regular expression to which this string is to be matched
	 * @param required
	 *            the required parameter indicates if MUST be validated
	 * @throws AdapterException
	 *             the adapter exception if the parameter it's doesn't valid
	 * @see java.util.regex.Pattern.matches(regex, str)
	 */
	protected void validateParameter(final String paramName, final String paramValue, final String expression,
			final boolean required) throws AdapterException {

		if (required && paramValue == null) {
			LOGGER.error(String.format(AdapterConstants.MSG_MISSING_PARAMETER, paramName));
			throw new AdapterException(AdapterErrorCode.PARAMETERS_ERROR,
					String.format(AdapterConstants.MSG_MISSING_PARAMETER, paramName),
					String.valueOf(AdapterErrorCode.PARAMETERS_ERROR.getError()), null);
		}

		if (required && paramValue.isEmpty()) {
			LOGGER.error(String.format(AdapterConstants.MSG_INVALID_PARAMETER, paramName));
			throw new AdapterException(AdapterErrorCode.PARAMETERS_ERROR,
					String.format(AdapterConstants.MSG_INVALID_PARAMETER, paramName),
					String.valueOf(AdapterErrorCode.PARAMETERS_ERROR.getError()), null);
		}

		if (paramValue != null && !paramValue.isEmpty() && expression != null && !paramValue.matches(expression)) {
			LOGGER.error(String.format(AdapterConstants.MSG_INVALID_PARAMETER, paramName));
			throw new AdapterException(AdapterErrorCode.PARAMETERS_ERROR,
					String.format(AdapterConstants.MSG_INVALID_PARAMETER, paramName),
					String.valueOf(AdapterErrorCode.PARAMETERS_ERROR.getError()), null);
		}

	}

	/**
	 * Method responsible for validating the length of a specific
	 * {@link TaskRequestType} instance parameter.
	 *
	 * @param paramName
	 *            the parameter name
	 * @param paramValue
	 *            the parameter value
	 * @param paramSize
	 *            the parameter size
	 * @throws AdapterException
	 *             the adapter exception if the parameter it's doesn't valid
	 */
	protected void validateParameterLength(final String paramName, final String paramValue, final int paramSize)
			throws AdapterException {
		if (paramValue.length() > paramSize) {
			final String msg = String.format(AdapterConstants.MSG_INVALID_PARAMETER_LENGTH, paramSize, paramName);
			LOGGER.error(msg);
			throw new AdapterException(AdapterErrorCode.PARAMETERS_ERROR, msg);
		}
	}

	/**
	 * Method responsible for validating parameter length between a range size.
	 *
	 * @param paramName
	 *            the parameter name
	 * @param paramValue
	 *            the parameter value
	 * @param paramSizeBegin
	 *            the parameter size begin
	 * @param paramSizeEnd
	 *            the parameter size end
	 * @throws AdapterException
	 *             the adapter exception if the parameter it's doesn't valid
	 */
	protected void validateParameterLengthBetween(final String paramName, final String paramValue,
			final int paramSizeBegin, final int paramSizeEnd) throws AdapterException {
		if (paramValue.length() > paramSizeEnd || paramValue.length() < paramSizeBegin) {
			final String msg = String.format(AdapterConstants.MSG_INVALID_PARAMETER_LENGTH_BETWEEN, paramSizeBegin,
					paramSizeEnd, paramName);
			LOGGER.error(msg);
			throw new AdapterException(AdapterErrorCode.PARAMETERS_ERROR, msg,
					String.valueOf(AdapterErrorCode.PARAMETERS_ERROR.getError()), null);
		}
	}

	/**
	 * Method responsible for validating and formating the parameter value.
	 *
	 * @param name
	 *            the parameter name
	 * @param date
	 *            the date to format
	 * @param pattern
	 *            the pattern describing the date and time format
	 * @return the date
	 * @throws AdapterException
	 *             the adapter exception if parameter date it's null or if the
	 *             beginning of the specified string cannot be parsed.
	 * @see SimpleDateFormat
	 */
	protected Calendar toDate(final String name, final String date, final String pattern) throws AdapterException {

		if (date != null) {
			final Calendar dateTime = Calendar.getInstance();
			try {
				final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				dateTime.setLenient(false);
				dateTime.setTime(sdf.parse(date));
			} catch (ParseException e) {
				final String msg = String.format(AdapterConstants.MSG_INVALID_PARAMETER, name);
				LOGGER.error(msg, e);
				throw new AdapterException(AdapterErrorCode.PARAMETERS_ERROR, msg);
			}

			return dateTime;
		}

		throw new AdapterException(AdapterErrorCode.PARAMETERS_ERROR,
				String.format(AdapterConstants.MSG_MISSING_PARAMETER, name));
	}

	/**
	 * Method responsible for creating a and validating the
	 * {@link XMLGregorianCalendar} from a {@link ParameterType} value.
	 *
	 * @param name
	 *            the parameter name
	 * @param date
	 *            the date to format
	 * @param pattern
	 *            the pattern describing the date and time format
	 * @return a {@link XMLGregorianCalendar} instance
	 * @throws AdapterException
	 *             the adapter exception it's throw if exist a some error in
	 *             {@link #toDateFormatted(String, String, String)}
	 */
	protected XMLGregorianCalendar toXMLGregorianCalendar(final String name, final String date, final String pattern)
			throws AdapterException {
		final Date dateTime = toDate(name, date, pattern).getTime();
		final GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(dateTime);
		final XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactoryImp.getDatatypeFactoryHolder()
				.newXMLGregorianCalendar(gregorianCalendar);
		return xmlGregorianCalendar;
	}

	/**
	 * Compare two {@link ParameterType} with values with the format
	 * {@link XMLGregorianCalendar}.
	 *
	 * @param intial
	 *            the initial date
	 * @param end
	 *            the compared date
	 * @return the a {@link XMLGregorianCalendar} if the initial data it's less than
	 *         end date
	 * @throws AdapterException
	 *             the {@link AdapterException} it's throw if there are invalid
	 *             formats or initial date it's greater than end date
	 *             {@link NullPointerException} if the {@link ParameterType} is null
	 */
	protected XMLGregorianCalendar compareXMLGregorianCalendarParamter(final ParameterType intial,
			final ParameterType end) throws AdapterException {

		final Calendar initialDate = this.toDate(intial.getName(), intial.getValue(),
				AdapterConstants.XML_DATE_PATTERN);
		final Calendar endDate = this.toDate(end.getName(), end.getValue(), AdapterConstants.XML_DATE_PATTERN);

		if (initialDate.after(endDate)) {
			final String errorDate = String.format(AdapterConstants.MSG_INVALID_PARAMETER_COMPARE_DATE, intial, end);
			LOGGER.error(errorDate);
			throw new AdapterException(AdapterErrorCode.PARAMETERS_ERROR, errorDate);
		}
		final GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(endDate.getTime());

		return DatatypeFactoryImp.getDatatypeFactoryHolder().newXMLGregorianCalendar(gregorianCalendar);
	}

	/**
	 * Find a {@link MockTaskConfig} instance from the {@link AdapterConfig}.
	 *
	 * @param mockTaskName
	 *            the mock task name
	 * @return the {@link MockTaskConfig} instance or null if doesn't exist
	 * @throws AdapterException
	 *             the the {@link AdapterException} it's throw if the configuration
	 *             it's not possible to load.
	 * @see {@link hn.com.tigo.josm.common.adapter.AbstractAdapter#getSingletonAdapter()}
	 */
	protected MockTaskConfig findMockTaskConfig(final String mockTaskName) throws AdapterException {
		final AdapterConfig config = getSingletonAdapter().getConfigurationType();
		if (config != null) {
			for (MockTaskConfig mock : config.getMockTaskDelay()) {
				if (mock.getTaskName().equals(mockTaskName)) {
					return mock;
				}
			}
		}
		return null;
	}

	/**
	 * Method responsible to validate the {@link ParameterType} attributes from a
	 * specific request.
	 * 
	 * @param name
	 *            the parameter name
	 * @param value
	 *            the parameter value
	 * @param pattern
	 *            the regular expression to which this string is to be matched
	 * @throws AdapterException
	 *             the the {@link AdapterException} it's throw if the value doesn't
	 *             match with the regular expression pattern
	 */
	protected void validateParameterType(final String name, final String value, final String pattern)
			throws AdapterException {
		if (!value.matches(pattern)) {
			final String message = String.format(AdapterConstants.MSG_INVALID_PARAMETER, name);
			LOGGER.error(message);
			throw new AdapterException(AdapterErrorCode.PARAMETERS_ERROR, message,
					String.valueOf(AdapterErrorCode.PARAMETERS_ERROR.getError()), null);
		}
	}

	/**
	 * Method responsible to create the simple task name.
	 *
	 * @param name
	 *            the {@link AbstractTask} simple name implementation
	 * @return the string
	 */
	protected String parseName(final String name) {

		final int index = name.indexOf("_");
		if (index != -1) {
			return name.substring(0, index);
		}

		return name;
	}

}