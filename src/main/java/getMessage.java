import javax.swing.*;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Queue;

public class getMessage implements Runnable {
    private final JList theList;
    private final JTextArea theText;
    private final ObjectInputStream in;

    public getMessage(JTextArea theText, theObjectStream theObjectStream, JList theList) {
        this.theList = theList;
        this.theText = theText;
        in = theObjectStream.getObjectInputStream();
    }

    public void run() {
        try {
            while (true) {
                Message theMessage = (Message) in.readObject();
                if (theMessage.getTheType() == 2) {
                    Queue<String> list = theMessage.getTheClientMap();
                    System.out.println(Arrays.toString(list.toArray()));
                    System.out.println(theMessage);
                    theList.setListData(list.toArray());
                }
                theText.append(theMessage.getTheMessage());
                theText.setCaretPosition(theText.getText().length());
            }
        } catch (Exception e) {
            theText.append("\t\t已经和服务器断开链接\n");
        }
    }

}
