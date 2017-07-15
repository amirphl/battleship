package ir.aut.test.view.second;

import com.sun.corba.se.impl.protocol.giopmsgheaders.KeyAddr;
import ir.aut.test.head.Connector;
import ir.aut.test.logic.MessageManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import static ir.aut.test.view.Constants.*;

/**
 * Created by Yana on 05/06/2017.
 */
public class ShipsJPanel extends JPanel implements ShipsJPanelCallBack {
    private FrameCallBack iFrameCallBack;
    //    private MessageManager messageManager;
    private Connector connector;
    private JButton resetButton;
    private JButton readyButton;
    private JButton rotateButton;
    private JButton leaveButton;
    private boolean isEditing = true;
    private boolean isReady = false;
    private boolean opponentReadiness = false;
    private boolean isInterrupted = false;
    private String opponentName = " ";
    private boolean[] myS1 = {false, false, false, false};
    private boolean[] myS2 = {false, false, false};
    private boolean[] myS3 = {false, false};
    private boolean[] myS4 = {false};
    private boolean[] opponentS1 = {false, false, false, false};
    private boolean[] opponentS2 = {false, false, false};
    private boolean[] opponentS3 = {false, false};
    private boolean[] opponentS4 = {false};
    private final int hei = 20;
    private Handler handler;
    private JButton smallSquareButton;
    private JButton mediumSquareButton;
    private JButton bigSquareButton;
    private JButton largeSquareButton;
    private int direction = HARIZONTAL;
    private int position = 1;
    private int ssb = 4;
    private int msb = 3;
    private int bsb = 2;
    private int lsb = 1;
    private int numberOfUsedShips = 0;

    public ShipsJPanel(FrameCallBack iFrameCallBack, Connector connector) {
        this.iFrameCallBack = iFrameCallBack;
//        this.messageManager = messageManager;
        this.connector = connector;
//        messageManager.setShipsJPanelCallBack(this);
        connector.setShipsJPanelCallBack(this);
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setSize(new Dimension(WIDTH_OF_ORDERINGJPANEL, HEIGHT_OF_FRAME - HEIGHT_OF_ORDERINGJPANEL));
        setBounds(0, HEIGHT_OF_ORDERINGJPANEL + 1, WIDTH_OF_ORDERINGJPANEL, HEIGHT_OF_FRAME - HEIGHT_OF_ORDERINGJPANEL - 40 - 1);
        setOpaque(true);
        handler = new Handler();
        createButtons();
    }

    private void createButtons() {
        if (isEditing) {
            resetButton = new JButton("Reset");
            readyButton = new JButton("Ready");
            rotateButton = new JButton("Rotate");
            try {
                resetButton.setIcon(new ImageIcon(ImageIO.read(new File("reset.jpg"))));
                readyButton.setIcon(new ImageIcon(ImageIO.read(new File("ready.jpg"))));
                rotateButton.setIcon(new ImageIcon(ImageIO.read(new File("rotate.jpg"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            resetButton.setFont(TEXT_FONT);
            readyButton.setFont(TEXT_FONT);
            rotateButton.setFont(TEXT_FONT);
            resetButton.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            readyButton.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            rotateButton.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            resetButton.setBounds(500, 30, 100, 30);
            resetButton.setOpaque(true);
            readyButton.setBounds(620, 30, 115, 30);
            readyButton.setOpaque(true);
            rotateButton.setBounds(750, 30, 110, 30);
            rotateButton.setOpaque(true);

            rotateButton.setEnabled(false);
            rotateButton.setFocusable(true);
            rotateButton.addKeyListener(new MyKeyListener());
            rotateButton.setMnemonic(KeyEvent.VK_R);

            resetButton.setToolTipText("For Reset , Press me");
            readyButton.setToolTipText("To start , Press me");
            rotateButton.setToolTipText("To Rotate rectangle , Press me or click Alt+r");
            resetButton.addActionListener(handler);
            readyButton.addActionListener(handler);
            rotateButton.addActionListener(handler);
            add(resetButton);
            add(readyButton);
            add(rotateButton);
            drawSButtons();
        } else {
            leaveButton = new JButton("Leave");
            leaveButton.setFont(TEXT_FONT);
            leaveButton.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            leaveButton.setBounds(750, 30, 100, 30);
            leaveButton.setOpaque(true);
            leaveButton.setToolTipText("To Leave , Press me");
            leaveButton.addActionListener(handler);
            add(leaveButton);
        }
    }

    private void drawSButtons() {
        smallSquareButton = new JButton("4");
        mediumSquareButton = new JButton("3");
        bigSquareButton = new JButton("2");
        largeSquareButton = new JButton("1");
        smallSquareButton.setBounds(200, 30, 100, 25);
        mediumSquareButton.setBounds(200, 60, 100, 25);
        bigSquareButton.setBounds(200, 90, 100, 25);
        largeSquareButton.setBounds(200, 120, 100, 25);
        smallSquareButton.setOpaque(true);
        mediumSquareButton.setOpaque(true);
        bigSquareButton.setOpaque(true);
        largeSquareButton.setOpaque(true);
        smallSquareButton.setToolTipText("For picking a small ship , click me.");
        mediumSquareButton.setToolTipText("For picking a medium ship , click me.");
        bigSquareButton.setToolTipText("For picking a big ship , click me.");
        largeSquareButton.setToolTipText("For picking a large ship , click me.");
        smallSquareButton.addActionListener(handler);
        mediumSquareButton.addActionListener(handler);
        bigSquareButton.addActionListener(handler);
        largeSquareButton.addActionListener(handler);
        add(smallSquareButton);
        add(mediumSquareButton);
        add(bigSquareButton);
        add(largeSquareButton);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 30;
        int y = 30;
        int side = 20;
        g.setFont(TEXT_FONT);
        g.drawString("You", x, y / 2);
        g.drawString(opponentName, x + 250, y / 2);
        for (int i = 0; i < 4; i++) {
            drawMyRectangles(x, y, side, g, myS1[i]);
            drawOpponentRectangles(x + 250, y, side, g, opponentS1[i]);
            x += (side + 10);
        }
        x = 30;
        y = 60;
        side = 40;
        for (int i = 0; i < 3; i++) {
            drawMyRectangles(x, y, side, g, myS2[i]);
            drawOpponentRectangles(x + 250, y, side, g, opponentS2[i]);
            x += (side + 10);
        }
        x = 30;
        y = 90;
        side = 60;
        for (int i = 0; i < 2; i++) {
            drawMyRectangles(x, y, side, g, myS3[i]);
            drawOpponentRectangles(x + 250, y, side, g, opponentS3[i]);
            x += (side + 10);
        }
        x = 30;
        y = 120;
        side = 80;
        for (int i = 0; i < 1; i++) {
            drawMyRectangles(x, y, side, g, myS4[i]);
            drawOpponentRectangles(x + 250, y, side, g, opponentS4[i]);
            x += (side + 10);
        }
    }

    private void drawMyRectangles(int x, int y, int side, Graphics g, boolean isDestroyed) {
        if (isDestroyed)
            g.setColor(IMPACT_COLOR);
        else
            g.setColor(FILL_COLOR);
        g.fillRect(x, y, side, hei);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, side, hei);
    }

    private void drawOpponentRectangles(int x, int y, int side, Graphics g, boolean isDestroyed) {
        if (isEditing || isReady) {
            return;
        }
        drawMyRectangles(x, y, side, g, isDestroyed);
    }

    private class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread() {
                public void run() {
                    if (e.getSource() == readyButton) {
                        if (e.getActionCommand().equals("Ready")) {
                            if (numberOfUsedShips != 10) {
                                JOptionPane.showMessageDialog(null, "Put all ships in the map.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            readyButton.setText("Cancel");
                        } else if (e.getActionCommand().equals("Cancel"))
                            readyButton.setText("Ready");

                        if (isReady) {
                            isEditing = true;
                            isReady = false;
                            isInterrupted = true;
                            connector.setReadinessCondition(false);
                            connector.sendMessage("SendReadinessCondition");
//                            messageManager.sendReadinessCondition(false);
                        } else {
                            isEditing = false;
                            isReady = true;
                            isInterrupted = false;
                            connector.setReadinessCondition(true);
                            connector.sendMessage("SendReadinessCondition");
//                            messageManager.sendReadinessCondition(true);
                            new Thread() {
                                public void run() {
                                    while (!isInterrupted) {
                                        if (isOpponentReady()) {
                                            iFrameCallBack.startGame();
                                            startGame();
                                            isReady = false;
                                            break;
                                        } else
                                            try {
                                                Thread.sleep(150);
                                            } catch (InterruptedException e1) {
                                                e1.printStackTrace();
                                            }
                                    }
                                }
                            }.start();
                        }
                    } else if (e.getSource() == leaveButton) {
                        leaveGame();
                    }
                    if (isEditing) {
                        if (e.getSource() == resetButton) {
                            resetButtons();
                        } else if (e.getSource() == rotateButton) {
                            rotateButton();
                        } else {
                            disableButtons();
                        }
                        if (e.getSource() == smallSquareButton) {
                            iFrameCallBack.boundJLabel(direction, position = 1);
                        } else if (e.getSource() == mediumSquareButton) {
                            iFrameCallBack.boundJLabel(direction, position = 2);
                        } else if (e.getSource() == bigSquareButton) {
                            iFrameCallBack.boundJLabel(direction, position = 3);
                        } else if (e.getSource() == largeSquareButton) {
                            iFrameCallBack.boundJLabel(direction, position = 4);
                        }
                    }
                    repaint();
                }
            }.start();
        }
    }

    private class MyKeyListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            new Thread() {
                public void run() {
                    if (e.getKeyCode() == KeyEvent.VK_R) {
                        System.out.println("done");
                        if (isEditing)
                            rotateButton();
                        repaint();
                    }
                }
            }.start();
        }
    }

    @Override
    public void destroy(int sizeOfShip, int player) {
        switch (sizeOfShip) {
            case 1:
                for (int i = 3; i >= 0; i--) {
                    if (!myS1[i] && player == 1) {
                        myS1[i] = true;
                        break;
                    }
                    if (!opponentS1[i] && player == 2) {
                        opponentS1[i] = true;
                        break;
                    }
                }
                break;
            case 2:
                for (int i = 2; i >= 0; i--) {
                    if (!myS2[i] && player == 1) {
                        myS2[i] = true;
                        break;
                    }
                    if (!opponentS2[i] && player == 2) {
                        opponentS2[i] = true;
                        break;
                    }
                }
                break;
            case 3:
                for (int i = 1; i >= 0; i--) {
                    if (!myS3[i] && player == 1) {
                        myS3[i] = true;
                        break;
                    }
                    if (!opponentS3[i] && player == 2) {
                        opponentS3[i] = true;
                        break;
                    }
                }
                break;
            case 4:
                if (!myS4[0] && player == 1)
                    myS4[0] = true;
                if (!opponentS4[0] && player == 2)
                    opponentS4[0] = true;
                break;
        }
        repaint();
    }

    public void changeTextOfButton() {
        switch (position) {
            case 1:
                ssb--;
                smallSquareButton.setText(String.valueOf(ssb));
                break;
            case 2:
                msb--;
                mediumSquareButton.setText(String.valueOf(msb));
                break;
            case 3:
                bsb--;
                bigSquareButton.setText(String.valueOf(bsb));
                break;
            case 4:
                lsb--;
                largeSquareButton.setText(String.valueOf(lsb));
                break;
        }
        repaint();
    }

    private void removeButtuns() {
        remove(resetButton);
        remove(readyButton);
        remove(rotateButton);
        remove(smallSquareButton);
        remove(mediumSquareButton);
        remove(bigSquareButton);
        remove(largeSquareButton);
        revalidate();
    }

    public void disableButtons() {
        smallSquareButton.setEnabled(false);
        mediumSquareButton.setEnabled(false);
        bigSquareButton.setEnabled(false);
        largeSquareButton.setEnabled(false);
        rotateButton.setEnabled(true);
    }

    public void enableButtons() {
        numberOfUsedShips++;
        if (ssb != 0)
            smallSquareButton.setEnabled(true);
        if (msb != 0)
            mediumSquareButton.setEnabled(true);
        if (bsb != 0)
            bigSquareButton.setEnabled(true);
        if (lsb != 0)
            largeSquareButton.setEnabled(true);
        rotateButton.setEnabled(false);
    }

    private void resetButtons() {
        iFrameCallBack.reset();
        numberOfUsedShips = 0;
        ssb = 4;
        msb = 3;
        bsb = 2;
        lsb = 1;
        smallSquareButton.setText(String.valueOf(ssb));
        smallSquareButton.setEnabled(true);
        mediumSquareButton.setText(String.valueOf(msb));
        mediumSquareButton.setEnabled(true);
        bigSquareButton.setText(String.valueOf(bsb));
        bigSquareButton.setEnabled(true);
        largeSquareButton.setText(String.valueOf(lsb));
        largeSquareButton.setEnabled(true);
        rotateButton.setEnabled(false);
    }

    private void rotateButton() {
        switch (direction) {
            case HARIZONTAL:
                direction = VERTICAL;
                break;
            case VERTICAL:
                direction = HARIZONTAL;
                break;
        }
        iFrameCallBack.removeJLabel();
        iFrameCallBack.boundJLabel(direction, position);
    }

    private void startGame() {
        removeButtuns();
        createButtons();
        opponentName = iFrameCallBack.getOpponentName();
        repaint();
    }

    private void leaveGame() {
        String s = JOptionPane.showInputDialog("If you Sure , Type < LEAVE > here:");
        if (s.equals("LEAVE") && s != null) {
            connector.setTerminateCondition(0);
            connector.sendMessage("SendTerminateMessage");
//            messageManager.sendTerminate(0);
            iFrameCallBack.loose();
            System.exit(0);
        }
    }

    public void setOpponentReadiness(boolean bool) {
        opponentReadiness = bool;
    }

    public boolean isOpponentReady() {
        return opponentReadiness;
    }
}
