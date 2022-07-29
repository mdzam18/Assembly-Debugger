package src.Emulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class FilesManager {

    //reads all files and creates list of commands to execute
    public ArrayList<String> createList(String[] arr) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            FileReader curr = new FileReader(arr[i]);
            ArrayList<String> list2 = readFile(curr);
            for (int j = 0; j < list2.size(); j++) {
                list.add(list2.get(j));
            }
            curr.close();
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
                    System.out.println(line);
                    throw new Exception(line + " should end with ;");
                }
                int index = line.indexOf(";");
                int start = 0;
                int end = index;
                //can have multiple commands on one line;
                while (index != -1) {
                    String curr = line.substring(start, end + 1);
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
