package src.DapClasses.Exceptions;
public class ExceptionBreakpointsFilter {
    private String filter;
    private String label;
    private String description;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDefault0() {
        return default0;
    }

    public void setDefault0(Boolean default0) {
        this.default0 = default0;
    }

    public Boolean getSupportsCondition() {
        return supportsCondition;
    }

    public void setSupportsCondition(Boolean supportsCondition) {
        this.supportsCondition = supportsCondition;
    }

    public String getConditionDescription() {
        return conditionDescription;
    }

    public void setConditionDescription(String conditionDescription) {
        this.conditionDescription = conditionDescription;
    }

    private Boolean default0;
    private Boolean supportsCondition;
    private String conditionDescription;

}
