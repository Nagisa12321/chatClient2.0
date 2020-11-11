import javax.swing.*;
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
    private JButton btnP2pSend;
    private Client theClient;


    public chatGUI() {
        btnDisconnect.setEnabled(false);
        btnConnect.setEnabled(true);
        btnSend.setEnabled(false);
        btnP2pSend.setEnabled(false);
        text_port.setText("23333");
        text_ID.setText("Trump");
        text_IP.setText("cc_pc");
        text.setLineWrap(true);
        setContentPane(contentPane);
        setModal(true);

        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Objects.equals(text_IP.getText(), "") && !Objects.equals(text_port.getText(), "") && !Objects.equals(text_ID.getText(), ""))
                    try {
                        theClient = new Client(text_IP.getText(), text_port.getText());
                        theClient.openSocket();
                        theClient.SendId(text_ID.getText());
                        text.append("\t\tConnect successed!!!!!!!!!!!!!!!\n");
                        Runnable theThread = new getMessage(text, theClient.getSocket(), theClient.getTheStream());
                        Thread theThread2 = new Thread(theThread);
                        theThread2.start();
                        btnConnect.setEnabled(false);
                        btnSend.setEnabled(true);
                        btnDisconnect.setEnabled(true);
                        btnP2pSend.setEnabled(true);
                    } catch (IOException e1) {
                        text.append("\t\tThere is an IOException~ please try again\n");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
            }
        });
        btnDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theClient.closeSocket();
                    btnConnect.setEnabled(true);
                    btnSend.setEnabled(false);
                    btnDisconnect.setEnabled(false);
                } catch (Exception ignored) {
                }
            }
        });
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!text_Message.getText().equals(""))
                    try {
                        theClient.Send(text_Message.getText());
                        text_Message.setText("");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
            }
        });
        text_Message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (!text_Message.getText().equals(""))
                        try {
                            theClient.Send(text_Message.getText());
                            text_Message.setText("");
                        } catch (Exception e1) {
                            text.append("GroupSend failed , please try again ^-^\n");
                        }
                }
            }
        });
        btnP2pSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!text_Message.getText().equals("") && !textToUser.getText().equals("")){
                    try {
                        theClient.P2PSend(text_Message.getText(),textToUser.getText());
                    } catch (IOException ioException) {
                        text.append("P2P send failed , please try again ^-^\n");
                    }
                }
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
