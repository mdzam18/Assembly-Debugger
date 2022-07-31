package src.Emulator.Files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;

public class FilesManager {

    private boolean containsMain;

    //reads all files and creates list of commands to execute
    public ArrayList<String> createList(String[] arr) throws Exception {
        containsMain = false;
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            FileReader curr = new FileReader(arr[i]);
            ArrayList<String> list2 = readFile(curr);
            for (int j = 0; j < list2.size(); j++) {
                list.add(list2.get(j));
            }
            curr.close();
        }
        if(!containsMain){
            throw new Exception("missing main");
        }
        return list;
    }

    private ArrayList<String> readFile(FileReader file) throws Exception {
        BufferedReader rd = new BufferedReader(file);
        ArrayList<String> list = new ArrayList<>();
        while (true) {
            String line = rd.readLine();
            if (line == null) {
                break;
            }
            if (!line.equals("")) {
                line = line.replaceAll(" ", ""); //Delete white spaces.
                line = line.toUpperCase(Locale.ROOT);
                if (!line.endsWith(";")) {
                    throw new Exception(line + " missing semicolon");
                }
                int index = line.indexOf(";");
                int start = 0;
                int end = index;
                //can have multiple commands on one line;
                while (index != -1) {
                    String curr = line.substring(start, end + 1);
                    if(curr.contains("MAIN")){
                        containsMain = true;
                    }
                    list.add(curr);
                    start = end + 1;
                    index = line.indexOf(";", start);
                    end = index;
                }
            }
        }
        rd.close();
        return list;
    }
}
