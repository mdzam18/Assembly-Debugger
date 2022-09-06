package src.Emulator.Main;

import src.DebugAdapter.Receiver;
import src.Emulator.AssemblyEmulator.AssemblyEmulator;

import java.util.ArrayList;
import java.util.Map;


public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            Receiver receiver = new Receiver();
            receiver.receive();
        } else {
            AssemblyEmulator emulator = new AssemblyEmulator(args);
            emulator.runWholeCode();
            Map<String, Integer> registers = emulator.getRegisters();
            for (String register : registers.keySet()) {
                if (!register.equals("RV")) {
                    System.out.println(register + ": " + registers.get(register));
                }
            }
            if (emulator.containsRv()) {
                System.out.println();
                System.out.println("RV: " + emulator.getRv());
            }
            System.out.println("done");
        }
    }
}
