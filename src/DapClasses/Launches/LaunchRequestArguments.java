package src.DapClasses.Launches;
public class LaunchRequestArguments {
    private boolean noDebug;
    private Object __restart;
    private String name;
    private String program;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public boolean isNoDebug() {
        return noDebug;
    }

    public void setNoDebug(boolean noDebug) {
        this.noDebug = noDebug;
    }

    public Object get__restart() {
        return __restart;
    }

    public void set__restart(Object __restart) {
        this.__restart = __restart;
    }
}
