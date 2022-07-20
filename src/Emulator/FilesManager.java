package src.Emulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class FilesManager {

    private String createName(String name) {
        if (name.contains("/")) {
            int index = 0;
            for (int i = 0; i < name.length(); i++) {
                if (name.charAt(i) == '/') {
                    index = i;
                }
            }
            name = name.substring(0, index + 1);
            name = name + "assembly.asm";
            return name;
        }
        return "assembly.asm";
    }

    public String createNewFile(String[] arr) throws IOException {
        String name = createName(arr[0]);
        FileWriter file = new FileWriter(name);
        ArrayList<String> list2 = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            FileReader curr = new FileReader(arr[i]);
            ArrayList<String> list = readFile(curr);
            if (list.contains("FUNCTIONMAIN;")) {
                list2 = list;
            } else {
                for (int j = 0; j < list.size(); j++) {
                    file.write(list.get(j) + "\n");
                }
            }
            curr.close();
        }
        for (int i = 0; i < list2.size(); i++) {
            file.write(list2.get(i) + "\n");
        }
        file.close();
        return name;
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
