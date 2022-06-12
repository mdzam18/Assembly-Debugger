package DAP;

public interface Capabilities {
    /**
     * The debug adapter supports the 'configurationDone' request.
     */
    boolean supportsConfigurationDoneRequest = false;

    /**
     * The debug adapter supports function breakpoints.
     */
    boolean supportsFunctionBreakpoints = false;

    /**
     * The debug adapter supports conditional breakpoints.
     */
    boolean supportsConditionalBreakpoints = false;

    /**
     * The debug adapter supports breakpoints that break execution after a
     * specified number of hits.
     */
    boolean supportsHitConditionalBreakpoints = false;

    /**
     * The debug adapter supports a (side effect free) evaluate request for data
     * hovers.
     */
    boolean supportsEvaluateForHovers = false;

    /**
     * Available exception filter options for the 'setExceptionBreakpoints'
     * request.
     */
    //ExceptionBreakpointsFilter[] exceptionBreakpointFilters;

    /**
     * The debug adapter supports stepping back via the 'stepBack' and
     * 'reverseContinue' requests.
     */
    boolean supportsStepBack = false;

    /**
     * The debug adapter supports setting a variable to a value.
     */
    boolean supportsSetVariable = false;

    /**
     * The debug adapter supports restarting a frame.
     */
    boolean supportsRestartFrame = false;

    /**
     * The debug adapter supports the 'gotoTargets' request.
     */
    boolean supportsGotoTargetsRequest = false;

    /**
     * The debug adapter supports the 'stepInTargets' request.
     */
    boolean supportsStepInTargetsRequest = false;

    /**
     * The debug adapter supports the 'completions' request.
     */
    boolean supportsCompletionsRequest = false;

    /**
     * The set of characters that should trigger completion in a REPL. If not
     * specified, the UI should assume the '.' character.
     */
    String[] completionTriggerCharacters = new String[0];

    /**
     * The debug adapter supports the 'modules' request.
     */
    boolean supportsModulesRequest = false;;

    /**
     * The set of additional module information exposed by the debug adapter.
     */
    //ColumnDescriptor[] additionalModuleColumns ;

    /**
     * Checksum algorithms supported by the debug adapter.
     */
    //supportedChecksumAlgorithms?: ChecksumAlgorithm[];

    /**
     * The debug adapter supports the 'restart' request. In this case a client
     * should not implement 'restart' by terminating and relaunching the adapter
     * but by calling the RestartRequest.
     */
    boolean supportsRestartRequest = false;

    /**
     * The debug adapter supports 'exceptionOptions' on the
     * setExceptionBreakpoints request.
     */
    boolean supportsExceptionOptions = false;

    /**
     * The debug adapter supports a 'format' attribute on the stackTraceRequest,
     * variablesRequest, and evaluateRequest.
     */
    boolean supportsValueFormattingOptions = false;

    /**
     * The debug adapter supports the 'exceptionInfo' request.
     */
    boolean supportsExceptionInfoRequest = false;

    /**
     * The debug adapter supports the 'terminateDebuggee' attribute on the
     * 'disconnect' request.
     */
    boolean supportTerminateDebuggee = false;

    /**
     * The debug adapter supports the 'suspendDebuggee' attribute on the
     * 'disconnect' request.
     */
    boolean supportSuspendDebuggee = false;

    /**
     * The debug adapter supports the delayed loading of parts of the stack, which
     * requires that both the 'startFrame' and 'levels' arguments and an optional
     * 'totalFrames' result of the 'StackTrace' request are supported.
     */
    boolean supportsDelayedStackTraceLoading = false;

    /**
     * The debug adapter supports the 'loadedSources' request.
     */
    boolean supportsLoadedSourcesRequest = false;

    /**
     * The debug adapter supports logpoints by interpreting the 'logMessage'
     * attribute of the SourceBreakpoint.
     */
    boolean supportsLogPoints = false;

    /**
     * The debug adapter supports the 'terminateThreads' request.
     */
    boolean supportsTerminateThreadsRequest = false;

    /**
     * The debug adapter supports the 'setExpression' request.
     */
    boolean supportsSetExpression = false;

    /**
     * The debug adapter supports the 'terminate' request.
     */
    boolean supportsTerminateRequest = false;

    /**
     * The debug adapter supports data breakpoints.
     */
    boolean supportsDataBreakpoints = false;

    /**
     * The debug adapter supports the 'readMemory' request.
     */
    boolean supportsReadMemoryRequest = false;

    /**
     * The debug adapter supports the 'writeMemory' request.
     */
    boolean supportsWriteMemoryRequest = false;

    /**
     * The debug adapter supports the 'disassemble' request.
     */
    boolean supportsDisassembleRequest = false;

    /**
     * The debug adapter supports the 'cancel' request.
     */
    boolean supportsCancelRequest = false;

    /**
     * The debug adapter supports the 'breakpointLocations' request.
     */
    boolean supportsBreakpointLocationsRequest = false;

    /**
     * The debug adapter supports the 'clipboard' context value in the 'evaluate'
     * request.
     */
    boolean supportsClipboardContext = false;

    /**
     * The debug adapter supports stepping granularities (argument 'granularity')
     * for the stepping requests.
     */
    boolean supportsSteppingGranularity = false;

    /**
     * The debug adapter supports adding breakpoints based on instruction
     * references.
     */
    boolean  supportsInstructionBreakpoints = false;

    /**
     * The debug adapter supports 'filterOptions' as an argument on the
     * 'setExceptionBreakpoints' request.
     */
    boolean supportsExceptionFilterOptions = false;

    /**
     * The debug adapter supports the 'singleThread' property on the execution
     * requests ('continue', 'next', 'stepIn', 'stepOut', 'reverseContinue',
     * 'stepBack').
     */
    boolean supportsSingleThreadExecutionRequests = false;
}
