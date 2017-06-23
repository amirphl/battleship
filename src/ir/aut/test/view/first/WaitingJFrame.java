package ir.aut.test.view.first;

import ir.aut.test.head.ManagerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Yana on 22/06/2017.
 */
public class WaitingJFrame extends JFrame implements IWaitingJFrameCallBack {
    private ManagerInterface manager;
    private JTextArea textArea;
    private JButton cancel;

    public WaitingJFrame(ManagerInterface manager) {
        this.manager = manager;
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Please Wait ...");
        setSize(400, 150);
        setLocation(600, 400);
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
        revalidate();
        setVisible(true);
    }

    private class Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread() {
                public void run() {
                    setVisible(false);
                    dispose();
                }
            }.start();
        }
    }

    @Override
    public void close(int i) {
        setVisible(false);
        dispose();
        if (i == 1)
            manager.startGameC();
        else {
            JOptionPane.showMessageDialog(null, "You are rejected by Opponent(Server). try again.", "REJECTED", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
