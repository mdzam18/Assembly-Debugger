package src.DapClasses;

public class ExceptionInfoResponse {
    private String exceptionId;
    private String description;
    private String breakMode;
    private ExceptionDetails details;

    public String getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(String exceptionId) {
        this.exceptionId = exceptionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBreakMode() {
        return breakMode;
    }

    public void setBreakMode(String breakMode) {
        this.breakMode = breakMode;
    }

    public ExceptionDetails getDetails() {
        return details;
    }

    public void setDetails(ExceptionDetails details) {
        this.details = details;
    }
}
