package src.DapClasses;

public class SetVariableResponse extends Response {
    private String value;
    private String type;
    private int variablesReference;
    private int namedVariables;
    private int indexedVariables;
    private Object body;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public int getVariablesReference() {
        return variablesReference;
    }

    public void setVariablesReference(int variablesReference) {
        this.variablesReference = variablesReference;
    }

    public int getNamedVariables() {
        return namedVariables;
    }

    public void setNamedVariables(int namedVariables) {
        this.namedVariables = namedVariables;
    }

    public int getIndexedVariables() {
        return indexedVariables;
    }

    public void setIndexedVariables(int indexedVariables) {
        this.indexedVariables = indexedVariables;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = body;
    }
}
