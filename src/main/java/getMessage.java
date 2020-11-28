import javax.swing.*;
import java.awt.*;
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
                int type = theMessage.getTheType();
                String message = theMessage.getTheMessage();
                System.out.println(theMessage.getTheType());
                if (type == 0) {
                    theText.append(message);
                    theText.setCaretPosition(theText.getText().length());
                } else if (type == 2) {
                    Queue<String> list = theMessage.getTheClientMap();
                    theList.setListData(list.toArray());
                    theText.append(message);
                    theText.setCaretPosition(theText.getText().length());
                } else if (type == 1) {
                    Toolkit.getDefaultToolkit().beep();
                    Runnable a = () -> {
                    Object[] options = {"恩恩！", "好的"};
                    JOptionPane.showOptionDialog(null, message, "SOMEONE TO TELL YOU",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    };
                    new Thread(a).start();
                    theText.append(message);
                    theText.setCaretPosition(theText.getText().length());
                }
            }

        } catch (Exception e) {
            theText.append("\t\t已经和服务器断开链接\n");
        }
    }

}
