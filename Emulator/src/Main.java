import java.io.FileReader;

public class Main {

    private String createNewFile(String[] arr){
        for(int i = 0 ; i < arr.length; i++){

        }
        return "";
    }

    public static void main(String[] args) throws Exception {
          String fileName = "\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\Emulator\\src\\tests\\calltests.txt";
//        String[] arr = new String[2];
//        arr[0] = fileName;
//        fileName = "\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\Emulator\\src\\tests\\sum.txt";
//        arr[1] = fileName;
    //    fileName = createNewFile(arr);
        // String fileName = args[0];
        AssemblyEmulator emulator = new AssemblyEmulator(new FileReader(fileName));
        emulator.debug();
        emulator.getCallStack();
        emulator.showStack("fact");
    }
}
