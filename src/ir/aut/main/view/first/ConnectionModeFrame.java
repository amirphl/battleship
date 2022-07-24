package ir.aut.main.view.first;

import ir.aut.main.head.ManagerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private String opponentPort = "5000";
    private String playerPort = "5000";
    private String opponentIp = "127.0.0.1";
    private JButton start;
    private JButton exit;
    private Font font;
    private int p;

    public ConnectionModeFrame(ManagerInterface manager) {
        this.manager = manager;
        setLayout(new FlowLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Select Connection Mode");
        setSize(500, 300);
        setLocation(400, 400);
        font = new Font("SanSerif", Font.PLAIN, 18);
        JTextArea textArea = new JTextArea("Name:");
        textArea.setFont(font);
        textArea.setBackground(Color.YELLOW);
        textArea.setDisabledTextColor(Color.BLACK);
        textArea.setEnabled(false);
        add(textArea);
        TextHandler textHandler = new TextHandler();
        FocusHandler focusHandler = new FocusHandler();
        name = new JTextField("", 30);
        name.setFont(font);
        name.addActionListener(textHandler);
        name.addFocusListener(focusHandler);
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
        portH = new JTextField("port:", 30);
        portG = new JTextField("port:", 30);
        portG.setEnabled(false);
        ip = new JTextField("IP:", 30);
        ip.setEnabled(false);
        portH.setFont(font);
        portG.setFont(font);
        ip.setFont(font);
        portH.addActionListener(textHandler);
        portG.addActionListener(textHandler);
        ip.addActionListener(textHandler);
        portH.addFocusListener(focusHandler);
        portG.addFocusListener(focusHandler);
        ip.addFocusListener(focusHandler);
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

    private void fillP(int n) throws NumberFormatException {
        switch (n) {
            case 0:
                p = Integer.valueOf(playerPort);
                break;
            case 1:
                p = Integer.valueOf(opponentPort);
                break;
        }
        if (p < 0 || p > 65535)
            throw new NumberFormatException();
    }

    private class TextHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread() {
                public void run() {
                    if (e.getSource() == name) {
                        playerName = e.getActionCommand();
                    } else if (e.getSource() == portH) {
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
                    revalidate();
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
                        if (host.isSelected()) {
                            try {
                                fillP(0);
                            } catch (NumberFormatException e1) {
                                JOptionPane.showMessageDialog(null, "Incorrect Format Fot Port.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            manager.waitForClient(p, playerName);
                        } else {
                            try {
                                fillP(1);
                            } catch (NumberFormatException e1) {
                                JOptionPane.showMessageDialog(null, "Incorrect Format Fot Port.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            manager.connectToServer(opponentIp, p, playerName);
                        }
                        setVisible(false);
                        dispose();
                    } else {
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    }
                }
            }.start();
        }
    }

    public class FocusHandler implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
        }

        @Override
        public void focusLost(FocusEvent e) {
            new Thread() {
                public void run() {
                    if (e.getSource() == name) {
                        playerName = name.getText();
                    } else if (e.getSource() == portH) {
                        playerPort = portH.getText();
                    } else if (e.getSource() == portG) {
                        opponentPort = portG.getText();
                    } else {
                        opponentIp = ip.getText();
                    }
                }
            }.start();
        }
    }
}
