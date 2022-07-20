package src.Emulator;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
<<<<<<< HEAD
          //String fileName = "C:\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\src\\Tests\\calltests.txt";
          String fileName = "D:\\FINAL\\Assembly-Debugger\\src\\Tests\\calltests.txt";
//        String[] arr = new String[2];
//        arr[0] = fileName;
//        fileName = "\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\Emulator\\src\\tests\\sum.txt";
//        arr[1] = fileName;
    //    fileName = createNewFile(arr);
        // String fileName = args[0];
=======
        //String fileName = "C:\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\src\\Tests\\calltests.txt";
        String fileName = "src/Tests/calltests.txt";
        String fileName2 = "src/Tests/sum.txt";
        String arr[] = new String[2];
        arr[0] = fileName;
        arr[1] = fileName2;
        FilesManager manager = new FilesManager();
        fileName = manager.createNewFile(arr);
>>>>>>> 48b66c88dacaefea3822811e91e38a995155d0dc
        AssemblyEmulator emulator = new AssemblyEmulator(new FileReader(fileName));
        emulator.next();
        emulator.next();
        ArrayList<String> list = emulator.getCallStack();
        System.out.println("call stack: ");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("done");
        System.out.println("show stack: ");
        emulator.showStack("TEST_SUM1");
        System.out.println("done");
        //  emulator.showStack("test_sum2");
    }
}
