package src.DapClasses;

public class VariablesArguments {
    private int variablesReference;
    private String filter;
    private int start;
    private int count;
    private ValueFormat format;

    public int getVariablesReference() {
        return variablesReference;
    }

    public void setVariablesReference(int variablesReference) {
        this.variablesReference = variablesReference;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ValueFormat getFormat() {
        return format;
    }

    public void setFormat(ValueFormat format) {
        this.format = format;
    }
}
