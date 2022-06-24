package DapClasses;

public class Scope {
    private String name;
    private String presentationHint;
    private int variablesReference;
    private int namedVariables;
    private int indexedVariables;
    private boolean expensive;
    private Source source;
    private int line;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresentationHint() {
        return presentationHint;
    }

    public void setPresentationHint(String presentationHint) {
        this.presentationHint = presentationHint;
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

    public boolean isExpensive() {
        return expensive;
    }

    public void setExpensive(boolean expensive) {
        this.expensive = expensive;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }

    private int column;
    private int endLine;
    private int endColumn;
}
