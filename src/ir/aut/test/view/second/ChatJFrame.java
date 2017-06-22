package ir.aut.test.view.second;

import ir.aut.test.logic.MessageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ir.aut.test.view.Constants.HEIGHT_OF_FRAME;
import static ir.aut.test.view.Constants.WIDTH_OF_FRAME;
import static ir.aut.test.view.Constants.WIDTH_OF_ORDERINGJPANEL;

/**
 * Created by Yana on 05/06/2017.
 */
public class ChatJFrame extends JPanel implements UI3 {

    private MessageManager messageManager;
    private String myName;
    private String opponentName;
    private JScrollPane scrollPane;
    private ChatJPanel chatJPanel;

    private JTextArea textArea;
    private JTextField textField;

    public ChatJFrame(MessageManager messageManager, String myName, String opponentName) {
        this.messageManager = messageManager;
        this.myName = myName;
        this.opponentName = opponentName;
        messageManager.setChatJFrame(this);
        setLayout(null);
        setSize(new Dimension(WIDTH_OF_FRAME - WIDTH_OF_ORDERINGJPANEL - 1, HEIGHT_OF_FRAME - 40));
        setBounds(WIDTH_OF_ORDERINGJPANEL + 1, 0, WIDTH_OF_FRAME - WIDTH_OF_ORDERINGJPANEL - 20, HEIGHT_OF_FRAME - 40);

        Font font = new Font("SanSerif", Font.PLAIN, 18);
        textArea = new JTextArea("Chat to " + opponentName);
        textArea.setBounds(1, 1, 200, 25);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textArea.setOpaque(true);
        textArea.setFont(font);
        textArea.setEnabled(false);
        textArea.setDisabledTextColor(Color.BLACK);
        textArea.setBackground(Color.YELLOW);
        add(textArea);

        textField = new JTextField("Type here ...");
        textField.setBounds(1, HEIGHT_OF_FRAME - 80, 220, 30);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textField.setOpaque(true);
        textField.setFont(font);
        textField.addActionListener(new Handler());
        add(textField);

        JButton send = new JButton("Send");
        send.setBounds(225, HEIGHT_OF_FRAME - 80, 60, 30);
        send.addActionListener(new Handler());
        add(send);

        chatJPanel = new ChatJPanel(myName, opponentName);
        scrollPane = new JScrollPane(chatJPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(5, 40, WIDTH_OF_FRAME - WIDTH_OF_ORDERINGJPANEL - 25, HEIGHT_OF_FRAME - 120);
        add(scrollPane);
        revalidate();
        repaint();
    }

    @Override
    public void addText(String text, int player) {
        chatJPanel.addText(text, player);
    }

    private class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread() {
                public void run() {
                    addText(e.getActionCommand(), 1);
                    messageManager.sendConversation(e.getActionCommand());
                    textField.setText(" ");
                    revalidate();
                }
            }.start();
        }
    }
}
