package DapClasses;

public class Variable {
    private String name;
    private String value;
    private String type;
    private VariablePresentationHint presentationHint;
    private String evaluateName;
    private int variablesReference;
    private int namedVariables;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VariablePresentationHint getPresentationHint() {
        return presentationHint;
    }

    public void setPresentationHint(VariablePresentationHint presentationHint) {
        this.presentationHint = presentationHint;
    }

    public String getEvaluateName() {
        return evaluateName;
    }

    public void setEvaluateName(String evaluateName) {
        this.evaluateName = evaluateName;
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

    public String getMemoryReference() {
        return memoryReference;
    }

    public void setMemoryReference(String memoryReference) {
        this.memoryReference = memoryReference;
    }

    private int indexedVariables;
    private String memoryReference;
}
