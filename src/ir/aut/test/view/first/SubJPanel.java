package ir.aut.test.view.first;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Yana on 21/06/2017.
 */
public class SubJPanel extends JPanel {

    private RCFCallBack receivedConnectionsFrame;
    private int code;
    private JTextArea nameArea;
    private JTextArea ipArea;
    private JButton accept;
    private JButton reject;

    public SubJPanel(String name, String ip, int code, RCFCallBack receivedConnectionsFrame) {
        this.code = code;
        this.receivedConnectionsFrame = receivedConnectionsFrame;
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Font font = new Font("SanSerif", Font.ITALIC, 16);
        nameArea = new JTextArea("name: " + name);
        nameArea.setEnabled(false);
        nameArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nameArea.setBackground(Color.YELLOW);
        nameArea.setDisabledTextColor(Color.BLACK);
        nameArea.setFont(font);
        add(nameArea);
        ipArea = new JTextArea("IP: " + ip);
        ipArea.setEnabled(false);
        ipArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ipArea.setBackground(Color.YELLOW);
        ipArea.setDisabledTextColor(Color.BLACK);
        ipArea.setFont(font);
        add(ipArea);
        Handler handler = new Handler();
        accept = new JButton("Accept");
        reject = new JButton("Reject");
        accept.addActionListener(handler);
        reject.addActionListener(handler);
        add(accept);
        add(reject);
        revalidate();
    }

    private class Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread() {
                public void run() {
                    if (e.getSource() == accept) {
                        receivedConnectionsFrame.acceptRequest(code);
                    } else {
                        setVisible(false);
                        revalidate();
                        receivedConnectionsFrame.deleteSubJPanel(code);
                    }
                }
            }.start();
        }
    }
}
