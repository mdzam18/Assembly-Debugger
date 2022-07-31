package src.Emulator.Memory;

public class MemoryManager {

    private int[] memory;

    public MemoryManager() {
        memory = new int[1]; //saved pc
    }

    public int[] getMemory() {
        int[] arr = new int[memory.length];
        for (int i = 0; i < memory.length; i++) {
            arr[i] = memory[i];
        }
        return arr;
    }

    public int getMemoryArraySize(){
        return memory.length;
    }

    public int getValue(int location){
        return memory[location];
    }

    public void addValue(int location, int value){
        memory[location] = value;
    }

    public void allocateMemory(String line) throws Exception {
        int index = line.indexOf("=");
        int size = computeMemorySize(line.substring(index + 1, line.indexOf(";")));
        if(size < 0){
            throw new Exception("invalid size: " + size);
        }
        resizeMemory(size);
    }

    public void resizeMemory(int size) {
        int[] curr = new int[size];
        System.arraycopy(memory, 0, curr, 0, Math.min(size, memory.length));
        memory = curr;
    }

    public void deleteSavedPC() {
        int[] curr = new int[memory.length - 1];
        System.arraycopy(memory, 1, curr, 0, memory.length - 1);
        memory = curr;
    }

    //computes size of the memory to allocate.
    private int computeMemorySize(String str) {
        if (str.contains("-")) {
            int index = str.indexOf("-");
            int size = Integer.parseInt(str.substring(index + 1)) / 4;
            return memory.length + size;
        } else {
            int index = str.indexOf("+");
            return memory.length - Integer.parseInt(str.substring(index + 1)) / 4;
        }
    }

}
