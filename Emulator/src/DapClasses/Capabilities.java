package DapClasses;
public class Capabilities {
    private Boolean supportsConfigurationDoneRequest;
    private Boolean supportsFunctionBreakpoints;
    private Boolean supportsConditionalBreakpoints;
    private Boolean supportsHitConditionalBreakpoints;
    private Boolean supportsEvaluateForHovers;
    private ExceptionBreakpointsFilter[] exceptionBreakpointFilters;
    private Boolean supportsStepBack;
    private Boolean supportsSetVariable;
    private Boolean supportsRestartFrame;

    public Boolean getSupportsConfigurationDoneRequest() {
        return supportsConfigurationDoneRequest;
    }

    public void setSupportsConfigurationDoneRequest(Boolean supportsConfigurationDoneRequest) {
        this.supportsConfigurationDoneRequest = supportsConfigurationDoneRequest;
    }

    public Boolean getSupportsFunctionBreakpoints() {
        return supportsFunctionBreakpoints;
    }

    public void setSupportsFunctionBreakpoints(Boolean supportsFunctionBreakpoints) {
        this.supportsFunctionBreakpoints = supportsFunctionBreakpoints;
    }

    public Boolean getSupportsConditionalBreakpoints() {
        return supportsConditionalBreakpoints;
    }

    public void setSupportsConditionalBreakpoints(Boolean supportsConditionalBreakpoints) {
        this.supportsConditionalBreakpoints = supportsConditionalBreakpoints;
    }

    public Boolean getSupportsHitConditionalBreakpoints() {
        return supportsHitConditionalBreakpoints;
    }

    public void setSupportsHitConditionalBreakpoints(Boolean supportsHitConditionalBreakpoints) {
        this.supportsHitConditionalBreakpoints = supportsHitConditionalBreakpoints;
    }

    public Boolean getSupportsEvaluateForHovers() {
        return supportsEvaluateForHovers;
    }

    public void setSupportsEvaluateForHovers(Boolean supportsEvaluateForHovers) {
        this.supportsEvaluateForHovers = supportsEvaluateForHovers;
    }

    public ExceptionBreakpointsFilter[] getExceptionBreakpointFilters() {
        return exceptionBreakpointFilters;
    }

    public void setExceptionBreakpointFilters(ExceptionBreakpointsFilter[] exceptionBreakpointFilters) {
        this.exceptionBreakpointFilters = exceptionBreakpointFilters;
    }

    public Boolean getSupportsStepBack() {
        return supportsStepBack;
    }

    public void setSupportsStepBack(Boolean supportsStepBack) {
        this.supportsStepBack = supportsStepBack;
    }

    public Boolean getSupportsSetVariable() {
        return supportsSetVariable;
    }

    public void setSupportsSetVariable(Boolean supportsSetVariable) {
        this.supportsSetVariable = supportsSetVariable;
    }

    public Boolean getSupportsRestartFrame() {
        return supportsRestartFrame;
    }

    public void setSupportsRestartFrame(Boolean supportsRestartFrame) {
        this.supportsRestartFrame = supportsRestartFrame;
    }

    public Boolean getSupportsGotoTargetsRequest() {
        return supportsGotoTargetsRequest;
    }

    public void setSupportsGotoTargetsRequest(Boolean supportsGotoTargetsRequest) {
        this.supportsGotoTargetsRequest = supportsGotoTargetsRequest;
    }

    public Boolean getSupportsStepInTargetsRequest() {
        return supportsStepInTargetsRequest;
    }

    public void setSupportsStepInTargetsRequest(Boolean supportsStepInTargetsRequest) {
        this.supportsStepInTargetsRequest = supportsStepInTargetsRequest;
    }

    public Boolean getSupportsCompletionsRequest() {
        return supportsCompletionsRequest;
    }

    public void setSupportsCompletionsRequest(Boolean supportsCompletionsRequest) {
        this.supportsCompletionsRequest = supportsCompletionsRequest;
    }

    public String[] getCompletionTriggerCharacters() {
        return completionTriggerCharacters;
    }

    public void setCompletionTriggerCharacters(String[] completionTriggerCharacters) {
        this.completionTriggerCharacters = completionTriggerCharacters;
    }

    public Boolean getSupportsModulesRequest() {
        return supportsModulesRequest;
    }

    public void setSupportsModulesRequest(Boolean supportsModulesRequest) {
        this.supportsModulesRequest = supportsModulesRequest;
    }

    public ColumnDescriptor[] getAdditionalModuleColumns() {
        return additionalModuleColumns;
    }

    public void setAdditionalModuleColumns(ColumnDescriptor[] additionalModuleColumns) {
        this.additionalModuleColumns = additionalModuleColumns;
    }

    public String[] getSupportedChecksumAlgorithms() {
        return supportedChecksumAlgorithms;
    }

    public void setSupportedChecksumAlgorithms(String[] supportedChecksumAlgorithms) {
        this.supportedChecksumAlgorithms = supportedChecksumAlgorithms;
    }

    public Boolean getSupportsRestartRequest() {
        return supportsRestartRequest;
    }

    public void setSupportsRestartRequest(Boolean supportsRestartRequest) {
        this.supportsRestartRequest = supportsRestartRequest;
    }

    public Boolean getSupportsExceptionOptions() {
        return supportsExceptionOptions;
    }

    public void setSupportsExceptionOptions(Boolean supportsExceptionOptions) {
        this.supportsExceptionOptions = supportsExceptionOptions;
    }

    public Boolean getSupportsValueFormattingOptions() {
        return supportsValueFormattingOptions;
    }

    public void setSupportsValueFormattingOptions(Boolean supportsValueFormattingOptions) {
        this.supportsValueFormattingOptions = supportsValueFormattingOptions;
    }

    public Boolean getSupportsExceptionInfoRequest() {
        return supportsExceptionInfoRequest;
    }

    public void setSupportsExceptionInfoRequest(Boolean supportsExceptionInfoRequest) {
        this.supportsExceptionInfoRequest = supportsExceptionInfoRequest;
    }

    public Boolean getSupportTerminateDebuggee() {
        return supportTerminateDebuggee;
    }

    public void setSupportTerminateDebuggee(Boolean supportTerminateDebuggee) {
        this.supportTerminateDebuggee = supportTerminateDebuggee;
    }

    public Boolean getSupportSuspendDebuggee() {
        return supportSuspendDebuggee;
    }

    public void setSupportSuspendDebuggee(Boolean supportSuspendDebuggee) {
        this.supportSuspendDebuggee = supportSuspendDebuggee;
    }

    public Boolean getSupportsDelayedStackTraceLoading() {
        return supportsDelayedStackTraceLoading;
    }

    public void setSupportsDelayedStackTraceLoading(Boolean supportsDelayedStackTraceLoading) {
        this.supportsDelayedStackTraceLoading = supportsDelayedStackTraceLoading;
    }

    public Boolean getSupportsLoadedSourcesRequest() {
        return supportsLoadedSourcesRequest;
    }

    public void setSupportsLoadedSourcesRequest(Boolean supportsLoadedSourcesRequest) {
        this.supportsLoadedSourcesRequest = supportsLoadedSourcesRequest;
    }

    public Boolean getSupportsLogPoints() {
        return supportsLogPoints;
    }

    public void setSupportsLogPoints(Boolean supportsLogPoints) {
        this.supportsLogPoints = supportsLogPoints;
    }

    public Boolean getSupportsTerminateThreadsRequest() {
        return supportsTerminateThreadsRequest;
    }

    public void setSupportsTerminateThreadsRequest(Boolean supportsTerminateThreadsRequest) {
        this.supportsTerminateThreadsRequest = supportsTerminateThreadsRequest;
    }

    public Boolean getSupportsSetExpression() {
        return supportsSetExpression;
    }

    public void setSupportsSetExpression(Boolean supportsSetExpression) {
        this.supportsSetExpression = supportsSetExpression;
    }

    public Boolean getSupportsTerminateRequest() {
        return supportsTerminateRequest;
    }

    public void setSupportsTerminateRequest(Boolean supportsTerminateRequest) {
        this.supportsTerminateRequest = supportsTerminateRequest;
    }

    public Boolean getSupportsDataBreakpoints() {
        return supportsDataBreakpoints;
    }

    public void setSupportsDataBreakpoints(Boolean supportsDataBreakpoints) {
        this.supportsDataBreakpoints = supportsDataBreakpoints;
    }

    public Boolean getSupportsReadMemoryRequest() {
        return supportsReadMemoryRequest;
    }

    public void setSupportsReadMemoryRequest(Boolean supportsReadMemoryRequest) {
        this.supportsReadMemoryRequest = supportsReadMemoryRequest;
    }

    public Boolean getSupportsWriteMemoryRequest() {
        return supportsWriteMemoryRequest;
    }

    public void setSupportsWriteMemoryRequest(Boolean supportsWriteMemoryRequest) {
        this.supportsWriteMemoryRequest = supportsWriteMemoryRequest;
    }

    public Boolean getSupportsDisassembleRequest() {
        return supportsDisassembleRequest;
    }

    public void setSupportsDisassembleRequest(Boolean supportsDisassembleRequest) {
        this.supportsDisassembleRequest = supportsDisassembleRequest;
    }

    public Boolean getSupportsCancelRequest() {
        return supportsCancelRequest;
    }

    public void setSupportsCancelRequest(Boolean supportsCancelRequest) {
        this.supportsCancelRequest = supportsCancelRequest;
    }

    public Boolean getSupportsBreakpointLocationsRequest() {
        return supportsBreakpointLocationsRequest;
    }

    public void setSupportsBreakpointLocationsRequest(Boolean supportsBreakpointLocationsRequest) {
        this.supportsBreakpointLocationsRequest = supportsBreakpointLocationsRequest;
    }

    public Boolean getSupportsClipboardContext() {
        return supportsClipboardContext;
    }

    public void setSupportsClipboardContext(Boolean supportsClipboardContext) {
        this.supportsClipboardContext = supportsClipboardContext;
    }

    public Boolean getSupportsSteppingGranularity() {
        return supportsSteppingGranularity;
    }

    public void setSupportsSteppingGranularity(Boolean supportsSteppingGranularity) {
        this.supportsSteppingGranularity = supportsSteppingGranularity;
    }

    public Boolean getSupportsInstructionBreakpoints() {
        return supportsInstructionBreakpoints;
    }

    public void setSupportsInstructionBreakpoints(Boolean supportsInstructionBreakpoints) {
        this.supportsInstructionBreakpoints = supportsInstructionBreakpoints;
    }

    public Boolean getSupportsExceptionFilterOptions() {
        return supportsExceptionFilterOptions;
    }

    public void setSupportsExceptionFilterOptions(Boolean supportsExceptionFilterOptions) {
        this.supportsExceptionFilterOptions = supportsExceptionFilterOptions;
    }

    public Boolean getSupportsSingleThreadExecutionRequests() {
        return supportsSingleThreadExecutionRequests;
    }

    public void setSupportsSingleThreadExecutionRequests(Boolean supportsSingleThreadExecutionRequests) {
        this.supportsSingleThreadExecutionRequests = supportsSingleThreadExecutionRequests;
    }

    private Boolean supportsGotoTargetsRequest;
    private Boolean supportsStepInTargetsRequest;
    private Boolean supportsCompletionsRequest;
    private String[] completionTriggerCharacters;
    private Boolean supportsModulesRequest;
    private ColumnDescriptor[] additionalModuleColumns;
    private String[] supportedChecksumAlgorithms;
    private Boolean supportsRestartRequest;
    private Boolean supportsExceptionOptions;
    private Boolean supportsValueFormattingOptions;
    private Boolean supportsExceptionInfoRequest;
    private Boolean supportTerminateDebuggee;
    private Boolean supportSuspendDebuggee;
    private Boolean supportsDelayedStackTraceLoading;
    private Boolean supportsLoadedSourcesRequest;
    private Boolean supportsLogPoints;
    private Boolean supportsTerminateThreadsRequest;
    private Boolean supportsSetExpression;
    private Boolean supportsTerminateRequest;
    private Boolean supportsDataBreakpoints;
    private Boolean supportsReadMemoryRequest;
    private Boolean supportsWriteMemoryRequest;
    private Boolean supportsDisassembleRequest;
    private Boolean supportsCancelRequest;
    private Boolean supportsBreakpointLocationsRequest;
    private Boolean supportsClipboardContext;
    private Boolean supportsSteppingGranularity;
    private Boolean supportsInstructionBreakpoints;
    private Boolean supportsExceptionFilterOptions;
    private Boolean supportsSingleThreadExecutionRequests;

}
