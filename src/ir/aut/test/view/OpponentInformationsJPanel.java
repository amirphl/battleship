package ir.aut.test.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Yana on 21/06/2017.
 */
public class OpponentInformationsJPanel extends JPanel {

    private RCInterface receivedConnectionsFrame;
    private String name;
    private String ip;
    private int code;
    private JButton accept;
    private JButton reject;

    public OpponentInformationsJPanel(String name, String ip, int code, RCInterface receivedConnectionsFrame) {
        this.name = name;
        this.ip = ip;
        this.code = code;
        this.receivedConnectionsFrame = receivedConnectionsFrame;
        setLayout(null);
        setOpaque(true);
        setBounds(15, 50 + code * 110, 470, 100);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Handler handler = new Handler();
        accept = new JButton("Accept");
        reject = new JButton("Reject");
        accept.setBounds(200, 20, 110, 50);
        reject.setBounds(330, 20, 110, 50);
        accept.addActionListener(handler);
        reject.addActionListener(handler);
        add(accept);
        add(reject);
        revalidate();
    }

    public void paintComponent(Graphics g) {
        g.setFont(new Font("SanSerif", Font.PLAIN, 18));
        g.setColor(Color.BLACK);
        g.drawString(name, 10, 20);
        g.drawString(ip, 10, 60);
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
                        receivedConnectionsFrame.deleteJPanel(code);
                    }
                }
            }.start();
        }
    }

    public void setCode(int code) {
        this.code = code;
        setBounds(10, 50 + code * 110, 480, 100);
        repaint();
    }

    public int getCode() {
        return code;
    }
}
