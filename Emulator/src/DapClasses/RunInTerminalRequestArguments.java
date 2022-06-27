package DapClasses;

public class RunInTerminalRequestArguments {
    private String kind;
    private String title;
    private String cwd;
    private String[] args;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCwd() {
        return cwd;
    }

    public void setCwd(String cwd) {
        this.cwd = cwd;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public Object getEnv() {
        return env;
    }

    public void setEnv(Object env) {
        this.env = env;
    }

    private Object env;

}
