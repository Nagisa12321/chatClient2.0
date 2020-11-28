import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.util.Queue;

public class getMessage implements Runnable {
    @SuppressWarnings("rawtypes")
    private final JList theList;
    private final JTextArea theText;
    private final JTextArea theText1;
    private final ObjectInputStream in;
    private final String MyID;

    @SuppressWarnings("rawtypes")
    public getMessage(JTextArea theText,JTextArea theText1, theObjectStream theObjectStream, JList theList, String MyID) {
        this.theList = theList;
        this.theText = theText;
        in = theObjectStream.getObjectInputStream();
        this.MyID = MyID;
        this.theText1 = theText1;
    }

    @SuppressWarnings({"InfiniteLoopStatement", "unchecked"})
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
                    if (!theMessage.getTheFromUser().equals(MyID)) {
                        Toolkit.getDefaultToolkit().beep();
                        Runnable a = () -> {
                            Object[] options = {"恩恩！", "好的"};
                            JOptionPane.showOptionDialog(null, message, "SOMEONE TO TELL YOU",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        };
                        new Thread(a).start();
                    }
                    theText1.append(message);
                    theText1.setCaretPosition(theText1.getText().length());
                }
            }

        } catch (Exception e) {
            theText.append("\t\t已经和服务器断开链接\n");
        }
    }

}
