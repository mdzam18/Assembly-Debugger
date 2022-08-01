package src.Emulator.Main;

import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        String text = "usowushwbgsola ";
        for (String arg : args) {
            text += arg + " bla ";
        }

        // Try block to check if exception occurs
        try {

            // Create a FileWriter object
            // to write in the file
           // FileWriter fWriter = new FileWriter(
             //       "D:\\FINAL\\Assembly-Debugger\\src\\Emulator\\Main\\testInputFile");
            FileWriter fWriter = new FileWriter(
                    "/tmp/foobar");

            // Writing into file
            // Note: The content taken above inside the
            // string
            fWriter.write(text);

            // Printing the contents of a file
            System.out.println(text);

            // Closing the file writing connection
            fWriter.close();

            // Display message for successful execution of
            // program on the console
            System.out.println("File is created successfully with the content.");
        }

        // Catch block to handle if exception occurs
        catch (IOException e) {
            // Print the exception
            System.out.print(e.getMessage());
        }

        String fileName = "src/Tests/sum.txt";
        String fileName2 = "src/Tests/calltests.txt";
        String arr[] = new String[2];
        arr[0] = fileName;
        arr[1] = fileName2;
        AssemblyEmulator emulator = new AssemblyEmulator(arr);
        emulator.debug();
//        for(int i = 0; i < 17; i++){
//            emulator.next();
//        }
        ArrayList<String> list = emulator.getCallStack();
        System.out.println("call stack: ");
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("done");
        System.out.println("show stack: ");
        emulator.showStack("MAIN");
        System.out.println("done");
        //  emulator.showStack("test_sum2");
    }
}
