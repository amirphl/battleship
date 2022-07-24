package ir.aut.main.view.first;

import ir.aut.main.head.Connector;
import ir.aut.main.head.ManagerInterface;
import ir.aut.main.logic.MessageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Yana on 22/06/2017.
 */
public class WaitingJFrame extends JFrame implements WaitingJFrameCallBack {

    private ManagerInterface manager;
    private Connector connector;
    private JTextArea textArea;
    private JButton cancel;
    private int window = 0;

    public WaitingJFrame(ManagerInterface manager, Connector connector) {
        this.manager = manager;
        this.connector = connector;

        connector.setWaitingJFrameCallBack(this);

        setLayout(null);
        setResizable(false);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Please Wait ...");
        setSize(400, 150);
        setLocation(600, 400);
        addWindowListener(new WindowHandler());
        textArea = new JTextArea("Waiting for the host to join ...");
        textArea.setFont(new Font("SanSerif", Font.PLAIN, 18));
        textArea.setBackground(Color.YELLOW);
        textArea.setDisabledTextColor(Color.BLACK);
        textArea.setEnabled(false);
        textArea.setBounds(50, 10, 250, 25);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        add(textArea);
        cancel = new JButton("Cancel");
        cancel.setBounds(250, 50, 100, 30);
        cancel.setFont(new Font("SanSerif", Font.PLAIN, 16));
        cancel.addActionListener(new Handler());
        add(cancel);
//        revalidate();
        setVisible(true);
    }

    private class Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread() {
                public void run() {
                    leave();
                }
            }.start();
        }
    }

    private class WindowHandler implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            if (window == 0) {
                leave();
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

    @Override
    public void close(int i) {
        window = 1;
        if (i == 1)
            manager.startGameC();
        else {
            JOptionPane.showMessageDialog(null, "You are rejected by Opponent(Server). try again.", "REJECTED", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        setVisible(false);
        dispose();
    }

    private void leave() {
        try {
            connector.setIp(InetAddress.getLocalHost().getHostAddress());
            connector.sendMessage("LeaveBeforeAccept");
//            messageManager.sendRequestLeave(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        setVisible(false);
        dispose();
        System.exit(0);
    }
}
