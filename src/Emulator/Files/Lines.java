package src.Emulator.Files;

public class Lines {

    //line entered by customer
    private String line;

    //on which line number is this process in debugger
    private int actualLineNumber;

    private String fileName;

    public Lines(String line, int actualLineNumber, String fileName) {
        this.line = line;
        this.actualLineNumber = actualLineNumber;
        this.fileName = fileName;
    }

    public int getActualLineNumber(){
        return actualLineNumber;
    }

    public String getLine(){
        return line;
    }

    public String getFileName(){
        return fileName;
    }

}
