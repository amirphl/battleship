package ir.aut.main.view.second;

import ir.aut.main.head.Connector;
import ir.aut.main.model.MyFileWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ir.aut.main.view.Constants.*;

/**
 * Created by Yana on 05/06/2017.
 */
public class ChatBox extends JPanel implements ChatBoxCallBack {

    //    private MessageManager messageManager;
    private Connector connector;
    private String opponentName;
    private String myName;
    private JScrollPane scrollPane;
    private ChatJPanel chatJPanel;

    private JTextArea textArea;
    private JTextField textField;
    private Font font = new Font("SanSerif", Font.PLAIN, 18);

    private MyFileWriter myFileWriter;

    public ChatBox(Connector connector, String opponentName, String myName) {
        this.connector = connector;
        this.opponentName = opponentName;
        this.myName = myName;
//        messageManager.setChatBoxCallBack(this);
        connector.setChatBoxCallBack(this);
        setLayout(null);
//        setSize(new Dimension(WIDTH_OF_FRAME - WIDTH_OF_ORDERINGJPANEL - 1, HEIGHT_OF_FRAME - 40));
        setBounds(WIDTH_OF_ORDERINGJPANEL + 1, 0, WIDTH_OF_FRAME - WIDTH_OF_ORDERINGJPANEL - 20, HEIGHT_OF_FRAME - 40);
//        paintOpponentName();

        textField = new JTextField("Type here ...");
        textField.setBounds(5, HEIGHT_OF_FRAME - 120, 260, 30);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textField.setOpaque(true);
        textField.setFont(font);
        textField.addActionListener(new Handler());
        add(textField);

        JButton send = new JButton("Send");
        send.setBounds(150, HEIGHT_OF_FRAME - 80, 100, 30);
        send.addActionListener(new Handler());
        send.setSelected(false);
        add(send);

        chatJPanel = new ChatJPanel(opponentName);
        scrollPane = new JScrollPane(chatJPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(5, 40, WIDTH_OF_FRAME - WIDTH_OF_ORDERINGJPANEL - 25, HEIGHT_OF_FRAME - 160);
        add(scrollPane);
        revalidate();
        repaint();
    }

    public void paintOpponentName(String opponentName) {
        this.opponentName = opponentName;
        textArea = new JTextArea("Chat to " + opponentName);
        textArea.setBounds(1, 1, 200, 25);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textArea.setOpaque(true);
        textArea.setFont(font);
        textArea.setEnabled(false);
        textArea.setDisabledTextColor(Color.BLACK);
        textArea.setBackground(Color.YELLOW);
        add(textArea);
        myFileWriter = new MyFileWriter(myName, opponentName);
        repaint();
    }

    @Override
    public void addText(String text, int player) {
        chatJPanel.addText(text, player);
        new Thread() {
            public void run() {
                myFileWriter.write(text, player);
            }
        }.start();
    }

    private class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread() {
                public void run() {
                    addText(e.getActionCommand(), 1);
                    connector.setConversationMessage(e.getActionCommand());
                    connector.sendMessage("SendConversationMessage");
//                    messageManager.sendConversation(e.getActionCommand());
                    textField.setText(" ");
                    revalidate();
                }
            }.start();
        }
    }
}
