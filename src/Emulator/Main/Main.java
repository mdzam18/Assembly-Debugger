package src.Emulator.Main;

import src.DapClasses.Receiver;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.util.ArrayList;


public class Main {
    public static String test2 = "Content-Length: 451\r\n" +
            "\r\n" +
            "{" +
            "\"command\":\"initialize\",\r\n" +
            "\"arguments\":{\r\n" +
            "\"clientID\":\"vscode\",\r\n" +
            "\"clientName\":\"Visual Studio Code\",\r\n" +
            "\"adapterID\":\"mock\",\r\n" +
            "\"pathFormat\":\"path\",\r\n" +
            "\"linesStartAt1\":true,\r\n" +
            "\"columnsStartAt1\":true,\r\n" +
            "\"supportsVariableType\":true,\r\n" +
            "\"supportsVariablePaging\":true,\r\n" +
            "\"supportsRunInTerminalRequest\":true,\r\n" +
            "\"locale\":\"en-us\",\r\n" +
            "\"supportsProgressReporting\":true,\r\n" +
            "\"supportsInvalidatedEvent\":true,\r\n" +
            "\"supportsMemoryReferences\":true,\r\n" +
            "\"supportsArgsCanBeInterpretedByShell\":true\r\n" +
            "},\r\n" +
            "\"type\":\"request\",\r\n" +
            "\"seq\":1\r\n" +
            "}" +
            "Content-Length: 79\r\n";
    public static void main(String[] args) throws Exception {
        //System.out.printf(test2);
//        Receiver receiver = new Receiver();
//        receiver.receive();


        String fileName = "C:\\Users\\mdzam\\Desktop\\assembly\\Assembly-Debugger\\src\\Tests\\addNumbers.asm";
        //String fileName2 = "src/Tests/calltests.asm";
        String arr[] = new String[1];
        arr[0] = fileName;
        //arr[1] = fileName2;
        AssemblyEmulator emulator = new AssemblyEmulator(arr);
       // emulator.debug();
        for(int i = 0; i < 4; i++){
            emulator.next();
        }
        ArrayList<String> list = emulator.getCallStack();
        System.out.println("call stack: ");
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("done");
        System.out.println("show stack: ");
        emulator.showStack("MAIN");
        System.out.println("done");
    }
}
