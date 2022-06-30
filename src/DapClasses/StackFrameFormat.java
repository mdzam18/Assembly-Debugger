package src.DapClasses;

public class StackFrameFormat extends ValueFormat {
    private boolean parameters;
    private boolean parameterTypes;
    private boolean parameterNames;

    public boolean isParameters() {
        return parameters;
    }

    public void setParameters(boolean parameters) {
        this.parameters = parameters;
    }

    public boolean isParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(boolean parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public boolean isParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(boolean parameterNames) {
        this.parameterNames = parameterNames;
    }

    public boolean isParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(boolean parameterValues) {
        this.parameterValues = parameterValues;
    }

    public boolean isLine() {
        return line;
    }

    public void setLine(boolean line) {
        this.line = line;
    }

    public boolean isModule() {
        return module;
    }

    public void setModule(boolean module) {
        this.module = module;
    }

    public boolean isIncludeAll() {
        return includeAll;
    }

    public void setIncludeAll(boolean includeAll) {
        this.includeAll = includeAll;
    }

    private boolean parameterValues;
    private boolean line;
    private boolean module;
    private boolean includeAll;
}
