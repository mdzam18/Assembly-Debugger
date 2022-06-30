package src.DapClasses;

public class ExceptionOptions {
    private ExceptionPathSegment[] path;

    public ExceptionPathSegment[] getPath() {
        return path;
    }

    public void setPath(ExceptionPathSegment[] path) {
        this.path = path;
    }

    public String getBreakMode() {
        return breakMode;
    }

    public void setBreakMode(String breakMode) {
        this.breakMode = breakMode;
    }

    private String breakMode;
}
