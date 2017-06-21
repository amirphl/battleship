package ir.aut.test.view;

import ir.aut.test.tools.ManagerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Yana on 20/06/2017.
 */
public class ConnectionModeFrame extends JFrame {

    private ManagerInterface manager;
    private String playerName = "Anonymous";
    private JTextField name;
    private JTextField portH;
    private JTextField portG;
    private JTextField ip;
    private JRadioButton host;
    private JRadioButton guest;
    private ButtonGroup buttonGroup;
    private String opponentPort;
    private String playerPort;
    private String opponentIp;
    private JButton start;
    private JButton exit;
    private Font font;

    public ConnectionModeFrame(ManagerInterface manager) {
        this.manager = manager;
        setLayout(new FlowLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Select Connection Mode");
        setSize(500, 300);
        setLocation(200, 200);
        font = new Font("SanSerif", Font.PLAIN, 18);
        TextHandler textHandler = new TextHandler();
        name = new JTextField("Type your name here", 30);
        name.setFont(font);
        name.addActionListener(textHandler);
        add(name);
        RadioButtonHandler radioButtonHandler = new RadioButtonHandler();
        host = new JRadioButton("Host", true);
        guest = new JRadioButton("Guest", false);
        host.setFont(font);
        guest.setFont(font);
        host.addItemListener(radioButtonHandler);
        guest.addItemListener(radioButtonHandler);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(host);
        buttonGroup.add(guest);
        portH = new JTextField("Type port then press Enter", 30);
        portG = new JTextField("Type port then press Enter", 30);
        portG.setEnabled(false);
        ip = new JTextField("Type ip then press Enter", 30);
        ip.setEnabled(false);
        portH.setFont(font);
        portG.setFont(font);
        ip.setFont(font);
        portH.addActionListener(textHandler);
        portG.addActionListener(textHandler);
        ip.addActionListener(textHandler);
        add(host);
        add(portH);
        add(guest);
        add(portG);
        add(ip);
        ButtonHandler buttonHandler = new ButtonHandler();
        start = new JButton("Start");
        exit = new JButton("Exit");
        start.setFont(font);
        exit.setFont(font);
        start.addActionListener(buttonHandler);
        exit.addActionListener(buttonHandler);
        add(start);
        add(exit);
        setVisible(true);
    }

    private class TextHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread() {
                public void run() {
                    if (e.getSource() == name)
                        playerName = e.getActionCommand();
                    else if (e.getSource() == portH) {
                        playerPort = e.getActionCommand();
                    } else if (e.getSource() == portG) {
                        opponentPort = e.getActionCommand();
                    } else {
                        opponentIp = e.getActionCommand();
                    }
                }
            }.start();
        }
    }

    private class RadioButtonHandler implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            new Thread() {
                public void run() {
                    if (e.getSource() == host) {
                        portG.setEnabled(false);
                        ip.setEnabled(false);
                        portH.setEnabled(true);
                    } else {
                        portG.setEnabled(true);
                        ip.setEnabled(true);
                        portH.setEnabled(false);
                    }
                }
            }.start();
        }
    }

    public class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread() {
                public void run() {
                    if (e.getSource() == start) {
                        int p;
                        if (host.isSelected()) {
                            try {
                                System.out.println(playerPort);
                                p = Integer.valueOf(playerPort);
                            } catch (NumberFormatException e1) {
                                JOptionPane.showMessageDialog(null, "Incorrect Format Fot Port.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            manager.waitForClient(p, playerName);
                            setVisible(false);
                            dispose();
                        } else {
                            try {
                                p = Integer.valueOf(playerPort);
                            } catch (NumberFormatException e1) {
                                JOptionPane.showMessageDialog(null, "Incorrect Format Fot Port.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            manager.connectToServer(opponentIp, p, playerName);
                            setVisible(false);
                            dispose();
                        }
                    } else {
                        setVisible(false);
                        dispose();
                    }
                }
            }.start();
        }
    }
}
