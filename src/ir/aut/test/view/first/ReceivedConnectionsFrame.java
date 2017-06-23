package ir.aut.test.view.first;

import ir.aut.test.logic.MessageManager;
import ir.aut.test.head.ManagerInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Yana on 21/06/2017.
 */
public class ReceivedConnectionsFrame extends JFrame implements RCFCallBack {

    private ManagerInterface manager;
    private MessageManager messageManager;
    private JTextArea textArea;
    private int counter = 0;
    private ArrayList<IPJPanel> arrayList;
    private JScrollPane scrollPane;
    private JPanel p;

    public ReceivedConnectionsFrame(ManagerInterface manager, MessageManager messageManager) {
        this.manager = manager;
        this.messageManager = messageManager;
        messageManager.setRcfCallBack(this);
        arrayList = new ArrayList<>();
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Waiting for connections ...");
        setSize(450, 600);
        setLocation(600, 400);
        textArea = new JTextArea("Received Connections:");
        textArea.setFont(new Font("SanSerif", Font.PLAIN, 18));
        textArea.setBackground(Color.YELLOW);
        textArea.setDisabledTextColor(Color.BLACK);
        textArea.setBounds(0, 0, 185, 25);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        textArea.setEnabled(false);
        add(textArea);

        p = new JPanel(new GridLayout(300, 1, 10, 10));
        scrollPane = new JScrollPane(p);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(5, 40, 440, 500);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(scrollPane);
        setVisible(true);
    }

    @Override
    public synchronized void addIPJPanel(String opponentName, String opponentIP) {
        IPJPanel jPanel = new IPJPanel(opponentName, opponentIP, counter, this);
        counter++;
        arrayList.add(jPanel);
        p.add(jPanel);
        p.repaint();
        revalidate();
    }

    @Override
    public void acceptRequest(int i) {
        messageManager.acceptRequest(i);
        setVisible(false);
        dispose();
        manager.startGameS();
    }

    @Override
    public void deleteJPanel(int i) {
        p.remove(arrayList.get(i));
        messageManager.rejectRequest(i);
        p.revalidate();
    }
}
