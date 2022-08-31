package src.DapClasses.Output;

import src.DapClasses.Source;

public class OutputEvent {
    private String category;
    private String output;
    private String group;
    private int variablesReference;
    private Source source;
    private int line;
    private int column;
    private Object data;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getVariablesReference() {
        return variablesReference;
    }

    public void setVariablesReference(int variablesReference) {
        this.variablesReference = variablesReference;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
