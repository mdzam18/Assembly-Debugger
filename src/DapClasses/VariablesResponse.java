package src.DapClasses;

public class VariablesResponse extends Response{
    private Variable[] variables;

    public Variable[] getVariables() {
        return variables;
    }

    public void setVariables(Variable[] variables) {
        this.variables = variables;
    }
}
