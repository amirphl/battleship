package ir.aut.test.view.first;

import ir.aut.test.logic.MessageManager;
import ir.aut.test.head.ManagerInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Yana on 21/06/2017.
 */
public class ReceivedConnectionsFrame extends JFrame implements RCInterface {

    private ManagerInterface manager;
    private MessageManager messageManager;
    private JTextArea textArea;
    private int counter = 0;
    private ArrayList<OpponentInformationJPanel> arrayList;

    public ReceivedConnectionsFrame(ManagerInterface manager, MessageManager messageManager) {
        this.manager = manager;
        this.messageManager = messageManager;
        messageManager.setReceivedConnectionsFrame(this);
        arrayList = new ArrayList<>();
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Waiting for connections ...");
        setSize(500, 400);
        setLocation(200, 200);
        textArea = new JTextArea("Received Connections:");
        textArea.setFont(new Font("SanSerif", Font.PLAIN, 18));
        textArea.setBackground(Color.GRAY);
        textArea.setBounds(0, 0, 185, 25);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        add(textArea);
        setVisible(true);
    }

    @Override
    public synchronized void addJPanel(String opponentName, String opponentIP) {
        OpponentInformationJPanel jPanel = new OpponentInformationJPanel(opponentName, opponentIP, counter, this);
        counter++;
        arrayList.add(jPanel);
        add(jPanel);
        repaint();
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
        remove(arrayList.get(i));
        for (int j = i + 1; j < arrayList.size(); j++) {
            arrayList.get(j).setCode(arrayList.get(j).getCode() - 1);
        }
        arrayList.remove(i);
        //***??????  notify removed player****//
    }
}