import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class getMessage implements Runnable {
    private JTextArea theText;
    private Socket serverSocket;
    private ObjectInputStream in;

    public getMessage(JTextArea theText, Socket serverSocket,theObjectStream theObjectStream) throws IOException {
        this.theText = theText;
        this.serverSocket = serverSocket;
        in = theObjectStream.getObjectInputStream();
    }

    public void run() {
        try {
            while (true) {
                Message theMessage = (Message) in.readObject();
                theText.append(theMessage.getTheMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("CLass not found");
        }
    }
}
