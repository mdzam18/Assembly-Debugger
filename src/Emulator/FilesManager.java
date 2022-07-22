package src.Emulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class FilesManager {

    public ArrayList<String> createList(String[] arr) throws IOException {
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

    private ArrayList<String> readFile(FileReader file) throws IOException {
        BufferedReader rd = new BufferedReader(file);
        ArrayList<String> list = new ArrayList<>();
        while (true) {
            String line = rd.readLine();
            if (line == null) {
                break;
            }
            line = line.replaceAll(" ", ""); //Delete white spaces.
            line = line.toUpperCase(Locale.ROOT);
            list.add(line);
        }
        rd.close();
        return list;
    }
}
