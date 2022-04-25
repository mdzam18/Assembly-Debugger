
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParseJson {
    private JSONObject json;

    public ParseJson(String str) throws ParseException {
        JSONParser parser = new JSONParser();
        json = (JSONObject) parser.parse(str);
        for(int i = 0; i < json.keySet().size(); i++){
            System.out.println("key: " + json.keySet().toArray()[i]);
            System.out.println("value: " + json.get(json.keySet().toArray()[i]));
        }
    }

    public String toString(JSONObject json){
        return json.toJSONString();
    }

    public static void main(String args[]) throws ParseException {
        String str = "{\"name\":\"John\", \"age\":30, \"car\":null}";
        ParseJson p = new ParseJson(str);
    }


}
