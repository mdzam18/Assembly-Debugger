package src.DapClasses.Variables;

import src.DapClasses.BaseClasses.Response;

public class VariablesResponse extends Response {
    private Variable[] variables;

    public Variable[] getVariables() {
        return variables;
    }

    public void setVariables(Variable[] variables) {
        this.variables = variables;
    }
}
