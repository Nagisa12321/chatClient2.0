import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

public class chatGUI extends JDialog {
    private JPanel contentPane;
    private JTextField text_Message;
    private JTextArea text;
    private JTextField text_port;
    private JTextField text_IP;
    private JTextField text_ID;
    private JButton btnConnect;
    private JButton btnDisconnect;
    private JButton btnSend;
    private JTextField textToUser;
    private JList list1;
    private Client theClient;


    public chatGUI() {
        textToUser.setEditable(false);
        text.setEditable(false);
        btnDisconnect.setEnabled(false);
        btnConnect.setEnabled(true);
        btnSend.setEnabled(false);
        text_port.setText("23333");
        text_ID.setText("Trump");
        text_IP.setText("cc_pc");
        text.setLineWrap(true);
        setContentPane(contentPane);
        setModal(true);

        btnConnect.addActionListener(e -> {
            if (!Objects.equals(text_IP.getText(), "") && !Objects.equals(text_port.getText(), "") && !Objects.equals(text_ID.getText(), ""))
                try {
                    theClient = new Client(text_IP.getText(), text_port.getText());
                    theClient.openSocket();
                    theClient.SendId(text_ID.getText());
                    text.append("\t\tConnect succeeded!!!!!!!!!!!!!!!\n");
                    Runnable theThread = new getMessage(text, theClient.getTheStream(), list1);
                    Thread theThread2 = new Thread(theThread);
                    theThread2.start();
                    btnConnect.setEnabled(false);
                    btnSend.setEnabled(true);
                    btnDisconnect.setEnabled(true);
                } catch (IOException e1) {
                    text.append("\t\tThere is an IOException~ please try again\n");
                } catch (Exception e1) {
                    btnConnect.setEnabled(true);
                    btnSend.setEnabled(false);
                    btnDisconnect.setEnabled(false);
                }
        });
        btnDisconnect.addActionListener(e -> {
            try {
                theClient.closeSocket();
                btnConnect.setEnabled(true);
                btnSend.setEnabled(false);
                btnDisconnect.setEnabled(false);
            } catch (Exception ignored) {
            }
        });
        btnSend.addActionListener(e -> {
            if (!text_Message.getText().equals("")) {
                if (textToUser.getText().equals("") || textToUser.getText().equals("GroupSend"))
                    try {
                        theClient.Send(text_Message.getText());
                        text_Message.setText("");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                else {
                    try {
                        theClient.P2PSend(text_Message.getText(), textToUser.getText());
                    } catch (IOException ioException) {
                        text.append("P2P send failed , please try again ^-^\n");
                    }
                }
            }
        });
        text_Message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (!text_Message.getText().equals("")) {
                        if (textToUser.getText().equals("") || textToUser.getText().equals("GroupSend"))
                            try {
                                theClient.Send(text_Message.getText());
                                text_Message.setText("");
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        else {
                            try {
                                theClient.P2PSend(text_Message.getText(), textToUser.getText());
                            } catch (IOException ioException) {
                                text.append("P2P send failed , please try again ^-^\n");
                            }
                        }
                    }
                }
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                textToUser.setText((String) list1.getSelectedValue());
            }
        });
    }

    public static void main(String[] args) {
        chatGUI dialog = new chatGUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
