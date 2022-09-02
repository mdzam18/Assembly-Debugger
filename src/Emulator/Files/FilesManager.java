package src.Emulator.Files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;

public class FilesManager {

    private boolean containsMain;

    //reads all files and creates list of commands to execute
    public ArrayList<Lines> createList(String[] arr) throws Exception {
        containsMain = false;
        ArrayList<Lines> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            FileReader curr = new FileReader(arr[i]);
            ArrayList<Lines> list2 = readFile(curr, arr[i]);
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

    private ArrayList<Lines> readFile(FileReader file, String fileName) throws Exception {
        BufferedReader rd = new BufferedReader(file);
        ArrayList<Lines> list = new ArrayList<>();
        int numberOfLine = 0;
        while (true) {
            String line = rd.readLine();
            if (line == null) {
                break;
            }
            if (!line.equals("")) {
                line = line.replaceAll(" ", ""); //Delete white spaces.
                line = line.toUpperCase(Locale.ROOT);
                if (!line.endsWith(";")) {
                    throw new Exception("line: " + (list.size() + 1) + " missing semicolon");
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
                    Lines currentLine = new Lines(curr, numberOfLine, fileName);
                    list.add(currentLine);
                    start = end + 1;
                    index = line.indexOf(";", start);
                    end = index;
                }
                numberOfLine++;
            }
        }
        rd.close();
        return list;
    }
}
