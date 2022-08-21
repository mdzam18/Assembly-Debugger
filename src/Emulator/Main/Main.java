package src.Emulator.Main;

import src.DapClasses.Receiver;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;



public class Main {
    public static void main(String[] args) throws Exception {

        Receiver receiver = new Receiver();
        receiver.receive();


//        String fileName = "C:\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\src\\Tests\\testStack";
//        //String fileName2 = "src/Tests/calltests.txt";
//        String arr[] = new String[1];
//        arr[0] = fileName;
//        //arr[1] = fileName2;
//        AssemblyEmulator emulator = new AssemblyEmulator(arr);
//       // emulator.debug();
//        for(int i = 0; i < 8; i++){
//            emulator.next();
//        }
//        ArrayList<String> list = emulator.getCallStack();
//        System.out.println("call stack: ");
//        for (String s : list) {
//            System.out.println(s);
//        }
//        System.out.println("done");
//        System.out.println("show stack: ");
//        emulator.showStack("A");
//        System.out.println("done");
        //  emulator.showStack("test_sum2");


    }
}
