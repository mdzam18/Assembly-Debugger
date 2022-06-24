package DapClasses;

public class StackTraceArguments {
    private int threadId;
    private int startFrame;
    private int levels;

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getStartFrame() {
        return startFrame;
    }

    public void setStartFrame(int startFrame) {
        this.startFrame = startFrame;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public StackFrameFormat getFormat() {
        return format;
    }

    public void setFormat(StackFrameFormat format) {
        this.format = format;
    }

    private StackFrameFormat format;

}
