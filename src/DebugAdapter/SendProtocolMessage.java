package src.DebugAdapter;


import java.io.FileWriter;
import java.io.IOException;

public class SendProtocolMessage {
    private FileWriter fWriter = new FileWriter(
            "/home/nroga/Final/Assembly-Debugger/src/Emulator/Main/sendTest");

    public SendProtocolMessage() throws IOException {
    }

    public void sendProtocolMessage(String json) throws IOException {
        fWriter.write("\n\n");
        fWriter.write(json);
        fWriter.write("\n\n");
        fWriter.flush();
        String message = String.format("Content-Length: %d\r\n\r\n%s", json.length(), json);
        System.out.print(message);
        System.out.flush();
    }

}
