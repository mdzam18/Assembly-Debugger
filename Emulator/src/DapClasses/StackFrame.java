package DapClasses;

public class StackFrame {
    private int id;
    private String name;
    private Source source;
    private int line;
    private int column;
    private int endLine;
    private int endColumn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isCanRestart() {
        return canRestart;
    }

    public void setCanRestart(boolean canRestart) {
        this.canRestart = canRestart;
    }

    public String getInstructionPointerReference() {
        return instructionPointerReference;
    }

    public void setInstructionPointerReference(String instructionPointerReference) {
        this.instructionPointerReference = instructionPointerReference;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getPresentationHint() {
        return presentationHint;
    }

    public void setPresentationHint(String presentationHint) {
        this.presentationHint = presentationHint;
    }

    private boolean canRestart;
    private String instructionPointerReference;
    private String moduleId;
    private String presentationHint;
}
