package src.DebugAdapter;


public class SendProtocolMessage {

    public void sendProtocolMessage(String json) {
        String message = String.format("Content-Length: %d\r\n\r\n%s", json.length(), json);
        System.out.print(message);
        System.out.flush();
    }

}
