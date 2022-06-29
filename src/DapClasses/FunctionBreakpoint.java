package src.DapClasses;

public class FunctionBreakpoint {
    private String name;
    private String condition;
    private String hitCondition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getHitCondition() {
        return hitCondition;
    }

    public void setHitCondition(String hitCondition) {
        this.hitCondition = hitCondition;
    }
}
