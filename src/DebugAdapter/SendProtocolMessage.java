package src.DebugAdapter;


import java.io.FileWriter;
import java.io.IOException;

public class SendProtocolMessage {

    public SendProtocolMessage() throws IOException {
    }

    //Sends protocol message with stdout
    public void sendProtocolMessage(String json) throws IOException {
        String message = String.format("Content-Length: %d\r\n\r\n%s", json.length(), json);
        System.out.print(message);
        System.out.flush();
    }

}
