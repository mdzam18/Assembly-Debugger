package src.Emulator;

import java.io.FileReader;
import java.util.ArrayList;

public class Main {

    private String createNewFile(String[] arr){
        for(int i = 0 ; i < arr.length; i++){

        }
        return "";
    }

    public static void main(String[] args) throws Exception {
          //String fileName = "C:\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\src\\Tests\\calltests.txt";
          String fileName = "src/Tests/calltests.txt";
//        String[] arr = new String[2];
//        arr[0] = fileName;
//        fileName = "\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\Emulator\\src\\tests\\sum.txt";
//        arr[1] = fileName;
    //    fileName = createNewFile(arr);
        // String fileName = args[0];
        AssemblyEmulator emulator = new AssemblyEmulator(new FileReader(fileName));
       // emulator.debug();
        emulator.next();
        emulator.next();
        ArrayList<String> list = emulator.getCallStack();
        for(int i = 0 ; i < list.size(); i++){
            System.out.println(list.get(i));
        }
        emulator.showStack("TEST_SUM1");
      //  emulator.showStack("test_sum2");
    }
}
