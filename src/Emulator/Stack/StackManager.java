package src.Emulator.Stack;

import src.Emulator.Memory.MemoryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StackManager {

    //instance variables
    private MemoryManager memoryManager;
    private ArrayList<String> callStack;
    private ArrayList<Integer> savedPc;

    public StackManager(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
        callStack = new ArrayList<>();
        callStack.add("MAIN");
        savedPc = new ArrayList<>();
        savedPc.add(0);
    }

    //Gets function name and returns stack of the function.
    public List<String> showStack(int indexInCallStack) throws Exception {
        List<String> list = new ArrayList<>();
        int[] memory = memoryManager.getMemory();
//        if (functionName.startsWith("MAIN")) {
//            for (int i = 0; i < memory.length; i++) {
//                if (savedPc.contains(i)) {
//                    list.add("SAVED PC");
//                    System.out.println("SAVED PC");
//                } else {
//                    list.add(String.valueOf(memory[i]));
//                    System.out.println(memory[i]);
//                }
//            }
//            return list;
//        }
        int lastIndex = memory.length;
        if (savedPc.size() > (indexInCallStack + 1)) {
            lastIndex = savedPc.get(indexInCallStack + 1);
        }
        System.out.println("SAVED PC");
        list.add("SAVED PC");
        for (int i = savedPc.get(indexInCallStack) + 1; i < lastIndex; i++) {
            System.out.println(memory[i]);
            list.add(String.valueOf(memory[i]));
        }
        return list;
    }

    //Returns callStack ArrayList.
    public ArrayList<String> getCallStack() {
        return callStack;
    }

    //Adds functionName to callStack list.
    public void addFunctionName(String functionName) {
        functionName = functionName.toUpperCase(Locale.ROOT);
        callStack.add(functionName);
    }

    //Removes functionName from CallStack list.
    public void removeFunctionName(int index) {
        callStack.remove(index);
    }

    //Add saved pc index.
    public void addSavedPc(int size) {
        savedPc.add(size);
    }

    //Removes saved pc index.
    public void removeSavedPc(int index) {
        savedPc.remove(index);
    }

//    //Deletes saved pc register from memory array.
//    public void deleteSavedPcFromMemory() {
//        int[] memory = memoryManager.getMemory();
//        int[] curr = new int[memory.length - 1];
//        System.arraycopy(memory, 1, curr, 0, memory.length - 1);
//        memory = curr;
//    }
//
//    public ArrayList<Integer> getSavedPc() {
//        return savedPc;
//    }

}
