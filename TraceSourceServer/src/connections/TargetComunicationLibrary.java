package connections;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public interface TargetComunicationLibrary extends Library{
	
	TargetComunicationLibrary INSTANCE = (TargetComunicationLibrary) Native.loadLibrary("TCL", TargetComunicationLibrary.class);
	
	/**
	 * Initialize TCL library.
	 * Call this function prior to other library function calls.
	 * \return RESULT_OK - if initialization completed successfully, RESULT_ERROR - otherwise
	 * \sa tcl_destroy()
	 * \sa tcl_getLastError()
	 * \sa tcl_clearError()
	 */
	int tcl_init();
	//------------------------------------------------------------------------------
	/**
	 * Set the text log file path.
	 * Log file will be created if it doesn't exist. Otherwise it will be overwritten.
	 * \param filePath - pointer to the const null teriminated string with a full path to the log file
	 * \return RESULT_OK - if text log file path was set, RESULT_ERROR - otherwise
	 * \sa tcl_getLastError()
	 * \sa tcl_clearError()
	 */
	int tcl_setTextLogFilePath( String filePath );
	//------------------------------------------------------------------------------
	/**
	 * Set the CNLogger-like log file path.
	 * CNLogger-like log file will contain all raw incoming and outcoming data.
	 * Log file will be created if it doesn't exist. Otherwise it will be overwritten.
	 * \param filePath - pointer to the const null teriminated string with a full path to the log file
	 * \return RESULT_OK - if CNLogger file path was set, RESULT_ERROR - otherwise
	 * \sa tcl_getLastError()
	 * \sa tcl_clearError()
	 */
	int tcl_setCNLogFilePath( String filePath );
	//------------------------------------------------------------------------------
	/**
	 * Connect to the trace source ( Head Unit or CNLogger ).
	 * This function initializes communication protocol, loads process list
	 * and callback count for each process.
	 * \param host - pointer to the null terminated string with a host name or IP address of the tarce source
	 * \param port - port number
	 * \param protocol - protocol type ( TRACE_SERVER_PROTOCOL - in case of direct connection to the HU, MULTICORE_PROTOCOL - connection to the CNLogger )
	 * \return RESULT_OK - if connection was established, RESULT_ERROR - otherwise
	 * \sa tcl_disconnect()
	 * \sa tcl_getLastError()
	 * \sa tcl_clearError()
	 */
	int tcl_connect_to_trace_consumer( String consumerHost, int consumerPort, int sourceId, String host, /*UINT16*/int port, /*PROTOCOL_TYPE*/ int protocol );
	//------------------------------------------------------------------------------
	/**
	 * Load detailed info about process ( scopes, callbacks with parameters ).
	 * Call this function prior to the tcl_executeCallback() in order to preload
	 * additional info about process and it's callbacks.
	 * \param processName - pointer to the null terminated string with process name to load info about
	 * \return RESULT_OK - if information was loaded, RESULT_ERROR - otherwise
	 * \sa tcl_getLastError()
	 * \sa tcl_clearError()
	 */
	int tcl_loadProcessInfo( String processName );
	//------------------------------------------------------------------------------
	/**
	 * Wait for a single trace message that satisfies REGEXP pattern.
	 * Returned result should be freed using call to the tcl_free() function.
	 * \param regexp - pointer to the const null terminated string with regexp pattern
	 * \param[out] resultBuffer - pointer to the variable where to place pointer to the null terminated string with a tarce message
	 * \param timeout - wait timeout in milliseconds ( by default - INFINITY )
	 * \return RESULT_OK - if trace message that satisfies pattern was captured, RESULT_TIMEOUT - if timeout was exceeded, RESULT_ERROR - otherwise
	 */
	int tcl_waitForTraceMsg( String regexp, PointerByReference resultBuffer, /*DWORD*/ int timeout );
	//------------------------------------------------------------------------------
	/**
	 * Wait for trace messages that satisfy the set of REGEXP patterns.
	 * This function returns captured trace messages joined with line feed character ( each trace on the new line ).
	 * Returned result should be freed using call to the tcl_free() function.
	 * \param patterns - pointer to the array of null terminated strings with patterns
	 * \param patternsCount - patterns count in array
	 * \param[out] resultTraces - pointer to the variable where to place pointer to the null terminated string with a result
	 * \param waitAll - wait until all patterns will be triggered ( by default - TRUE ), otherwise wait until at least one pattern will be triggered
	 * \param timeout - wait timeout in milliseconds ( by default - INFINITY )
	 * \return RESULT_OK - if trace messages that satisfy pattern were captured, RESULT_TIMEOUT - if timeout was exceeded, RESULT_ERROR - otherwise
	 * \sa tcl_getLastError()
	 * \sa tcl_clearError()
	 */
	int tcl_waitForMultipleTraceMsg( String[] patterns, /*DWORD*/ int patternsCount, PointerByReference resultTraces, /*BOOL*/ boolean waitAll, /*DWORD*/ int timeout );
	//------------------------------------------------------------------------------
	/**
	 * Execute callback. 
	 * \param processName - pointer to the null terminated string with target process name
	 * \param callbackName - pointer to the null terminated string with callback name
	 * \param def - default parameter value
	 * \param params - pointer to the array of the null terminated strings with parameter values
	 * \param count - parameters count
	 * \return RESULT_OK - if callback was executed successfully, RESULT_ERROR - otherwise
	 * \sa tcl_getLastError()
	 * \sa tcl_clearError()
	 */
	int tcl_executeCallback( String processName, String callbackName, /*INT*/ int def, String[] params, /*DWORD*/ int count );
	//------------------------------------------------------------------------------
	/**
	 * Disconnect form the tarce source.
	 * \return RESULT_OK - if success, RESULT_ERROR - otherwise
	 * \sa tcl_getLastError()
	 * \sa tcl_clearError()
	 */
	int tcl_disconnect();
	//------------------------------------------------------------------------------
	/**
	 * Free memeory that was allocated by library.
	 * Use this function to free returned results.
	 * \param mem - pointer to the memory that will be freed
	 * \return RESULT_OK - if sucess, RESULT_ERROR - otherwise
	 * \sa tcl_getLastError()
	 * \sa tcl_clearError()
	 */
	int tcl_free( Pointer mem );
	//------------------------------------------------------------------------------
	/**
	 * Get error code and error message of the last error.
	 * Not implemented yet.
	 */
	int tcl_getLastError( /*PINT*/ int  errorCode, String[] errorMessage );
	//------------------------------------------------------------------------------
	/**
	 * Clear error state.
	 * Not implemented yet.
	 */
	int tcl_clearError();
}
