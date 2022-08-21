package src.Emulator.Main;

import src.DapClasses.Receiver;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
//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
    public static void main(String[] args) throws Exception {
        String test = "Content-Length: 451\r\n" +
                "\r\n" +
                "{\"command\":\"initialize\",\"arguments\":{\"clientID\":\"vscode\",\"clientName\":\"Visual Studio Code\",\"adapterID\":\"mock\",\"pathFormat\":\"path\",\"linesStartAt1\":true,\"columnsStartAt1\":true,\"supportsVariableType\":true,\"supportsVariablePaging\":true,\"supportsRunInTerminalRequest\":true,\"locale\":\"en-us\",\"supportsProgressReporting\":true,\"supportsInvalidatedEvent\":true,\"supportsMemoryReferences\":true,\"supportsArgsCanBeInterpretedByShell\":true},\"type\":\"request\",\"seq\":1}Content-Length: 79";
        //System.out.println(test);
        Receiver receiver = new Receiver();
        receiver.receive();

        //String w = "{\"command\":\"initialize\",\"arguments\":{\"clientID\":\"vscode\",\"clientName\":\"Visual Studio Code\",\"adapterID\":\"mock\",\"pathFormat\":\"path\",\"linesStartAt1\":true,\"columnsStartAt1\":true,\"supportsVariableType\":true,\"supportsVariablePaging\":true,\"supportsRunInTerminalRequest\":true,\"locale\":\"en-us\",\"supportsProgressReporting\":true,\"supportsInvalidatedEvent\":true,\"supportsMemoryReferences\":true,\"supportsArgsCanBeInterpretedByShell\":true},\"type\":\"request\",\"seq\":1}";

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
