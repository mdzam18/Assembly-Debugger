//package src.DapClasses;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//public class RequestsReader {
//    private Scanner scanner;
//    public RequestsReader(){
//        scanner = new Scanner(System.in);
//        scanner.useDelimiter("");
//    }
//    private  String readHeader(Scanner scanner) {
//        String current = "";
//        while (true) {
//            String s = "";
//            while (true) {
//                String s2 = scanner.next();
//                if (s2.startsWith("\r")) {
//                    break;
//                }
//                s = s + s2;
//            }
//            current = current + s;
//            String next = scanner.next();
//            if (next.startsWith("\n")) {
//                break;
//            } else {
//                current = current + "\r";
//            }
//        }
//        return current;
//    }
//
//    public  String readRequest() {
//        Map<String, String> headers = new HashMap<>();
//        while (true) {
//            String header = readHeader(scanner);
//            String[] items = header.split(": ", 2); //?
//            if (items.length != 2) {
//                break;
//            }
//            headers.put(items[0], items[1]);
//        }
//        int length = Integer.parseInt(headers.get("Content-Length"));
//        StringBuilder rem = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            rem.append(scanner.next());
//        }
//        return rem.toString();
//    }
//}
