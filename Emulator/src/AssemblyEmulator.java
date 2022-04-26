import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AssemblyEmulator {

    private Map<String, Double> registers;

    public AssemblyEmulator(String text){
        text = text.replaceAll(" ", ""); //Delete white spaces.
        text = text.toUpperCase(Locale.ROOT);
        registers = new HashMap<>();
        fillRegistersMaps(text);
        printMap();
    }

    private void printMap(){
        for(String key : registers.keySet()){
            System.out.println(key + " " + registers.get(key));
        }
    }

    private String getInstructionType(String line){
        return "";
    }

    private void fillRegistersMaps(String line){
        int index = line.indexOf("=");
        String leftSide = getLeftSide(line.substring(0, index));
        String rightSide = getRightSide(line.substring(index + 1, line.indexOf(";")));
        registers.put(leftSide, Double.parseDouble(rightSide));
    }

    private String getLeftSide(String str){
        if(str.startsWith("R")){
            return str;
        }
        return "";
    }

    private String getRightSide(String str){
        //loads constants.
        if(Character.isDigit(str.charAt(0))){
            return str;
        } else if(str.charAt(0) == '-' && Character.isDigit(str.charAt(1))){ //is negative number.
            return str;
        }
        return "";
    }

    public static void main(String[] args){
        AssemblyEmulator emulator = new AssemblyEmulator("R1 =- 5  ;");
    }
}
