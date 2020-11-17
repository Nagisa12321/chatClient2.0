import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    private Message theMessage;
    private final String hostname;
    private final String port;
    private Socket theSocket = null;
    private String myID;
    private theObjectStream theStream;


    public Client(String hostname, String port) {
        this.hostname = hostname;
        this.port = port;
    }

    public theObjectStream getTheStream(){
        return theStream;
    }

    public Socket getSocket() {
        return theSocket;
    }

    public void openSocket() throws Exception {
        theSocket = new Socket(hostname, Integer.parseInt(port));
    }

    public void closeSocket() throws Exception {
        theSocket.close();
    }

    public void Send(String Message) throws IOException {
        theMessage = new Message();
        theMessage.groupSend(Message, myID);
        ObjectOutputStream out = theStream.getObjectOutputStream();
        out.writeObject(theMessage);
        out.flush();
    }

    public void SendId(String myID) throws IOException {
        this.myID = myID;
		ObjectOutputStream out = new ObjectOutputStream(theSocket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(theSocket.getInputStream());
		theStream = new theObjectStream(out,in);
		out.writeUTF(myID);
		out.flush();
    }

    public void P2PSend(String Message, String theID) throws IOException{
        theMessage = new Message();
        theMessage.p2pSend(myID, theID, Message);
		ObjectOutputStream out = theStream.getObjectOutputStream();
		out.writeObject(theMessage);
		out.flush();
    }
}
