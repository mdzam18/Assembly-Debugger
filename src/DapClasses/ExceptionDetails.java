package src.DapClasses;

public class ExceptionDetails {
    private String message;
    private String typeName;
    private String fullTypeName;
    private String evaluateName;
    private String stackTrace;
    private ExceptionDetails[] innerException;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFullTypeName() {
        return fullTypeName;
    }

    public void setFullTypeName(String fullTypeName) {
        this.fullTypeName = fullTypeName;
    }

    public String getEvaluateName() {
        return evaluateName;
    }

    public void setEvaluateName(String evaluateName) {
        this.evaluateName = evaluateName;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public ExceptionDetails[] getInnerException() {
        return innerException;
    }

    public void setInnerException(ExceptionDetails[] innerException) {
        this.innerException = innerException;
    }
}
