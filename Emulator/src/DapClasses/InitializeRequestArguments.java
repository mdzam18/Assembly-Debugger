package DapClasses;
public class InitializeRequestArguments {
    private String clientID;
    private String clientName;
    private String adapterID;
    private String locale;
    private Boolean linesStartAt1;
    private Boolean columnsStartAt1;
    private String   pathFormat;
    private Boolean supportsVariableType;
    private Boolean supportsVariablePaging;
    private Boolean supportsRunInTerminalRequest;
    private Boolean supportsMemoryReferences;
    private Boolean supportsProgressReporting;
    private Boolean supportsInvalidatedEvent;
    private Boolean supportsMemoryEvent;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAdapterID() {
        return adapterID;
    }

    public void setAdapterID(String adapterID) {
        this.adapterID = adapterID;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Boolean getLinesStartAt1() {
        return linesStartAt1;
    }

    public void setLinesStartAt1(Boolean linesStartAt1) {
        this.linesStartAt1 = linesStartAt1;
    }

    public Boolean getColumnsStartAt1() {
        return columnsStartAt1;
    }

    public void setColumnsStartAt1(Boolean columnsStartAt1) {
        this.columnsStartAt1 = columnsStartAt1;
    }

    public String getPathFormat() {
        return pathFormat;
    }

    public void setPathFormat(String pathFormat) {
        this.pathFormat = pathFormat;
    }

    public Boolean getSupportsVariableType() {
        return supportsVariableType;
    }

    public void setSupportsVariableType(Boolean supportsVariableType) {
        this.supportsVariableType = supportsVariableType;
    }

    public Boolean getSupportsVariablePaging() {
        return supportsVariablePaging;
    }

    public void setSupportsVariablePaging(Boolean supportsVariablePaging) {
        this.supportsVariablePaging = supportsVariablePaging;
    }

    public Boolean getSupportsRunInTerminalRequest() {
        return supportsRunInTerminalRequest;
    }

    public void setSupportsRunInTerminalRequest(Boolean supportsRunInTerminalRequest) {
        this.supportsRunInTerminalRequest = supportsRunInTerminalRequest;
    }

    public Boolean getSupportsMemoryReferences() {
        return supportsMemoryReferences;
    }

    public void setSupportsMemoryReferences(Boolean supportsMemoryReferences) {
        this.supportsMemoryReferences = supportsMemoryReferences;
    }

    public Boolean getSupportsProgressReporting() {
        return supportsProgressReporting;
    }

    public void setSupportsProgressReporting(Boolean supportsProgressReporting) {
        this.supportsProgressReporting = supportsProgressReporting;
    }

    public Boolean getSupportsInvalidatedEvent() {
        return supportsInvalidatedEvent;
    }

    public void setSupportsInvalidatedEvent(Boolean supportsInvalidatedEvent) {
        this.supportsInvalidatedEvent = supportsInvalidatedEvent;
    }

    public Boolean getSupportsMemoryEvent() {
        return supportsMemoryEvent;
    }

    public void setSupportsMemoryEvent(Boolean supportsMemoryEvent) {
        this.supportsMemoryEvent = supportsMemoryEvent;
    }
}
