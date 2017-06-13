package ir.aut.test.view;

import ir.aut.test.logic.MessageManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static ir.aut.test.view.Constants.*;

/**
 * Created by Yana on 05/06/2017.
 */
public class ShipsJPanel extends JPanel implements UI2 {
    private UI1 ui1;
    private MessageManager messageManager;
    private JButton resetButton;
    private JButton readyButton;
    private JButton rotateButton;
    private JButton leaveButton;
    private boolean isEditing = true;
    private boolean isReady = false;
    private boolean isOpponentReady = false;
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
    private boolean isInterrupted = false;

    public ShipsJPanel(UI1 ui1, MessageManager messageManager) {
        this.ui1 = ui1;
        this.messageManager = messageManager;
        messageManager.setShipsJPanel(this);
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setSize(new Dimension(WIDTH_OF_ORDERINGJPANEL, HEIGHT_OF_FRAME - HEIGHT_OF_ORDERINGJPANEL));
        setBounds(0, HEIGHT_OF_ORDERINGJPANEL + 1, WIDTH_OF_ORDERINGJPANEL, HEIGHT_OF_FRAME - HEIGHT_OF_ORDERINGJPANEL - 40 - 1);
        setOpaque(true);
        handler = new Handler();
        createButtons();
    }

    private void createButtons() {
        if (isEditing == true) {
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
            resetButton.setToolTipText("For Reset , Press me");
            readyButton.setToolTipText("To start , Press me");
            rotateButton.setToolTipText("To Rotate rectangle , Press me");
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

    private void drawMyRectangles(int x, int y, int side, Graphics g, boolean isImparted) {
        if (isImparted)
            g.setColor(IMPART_COLOR);
        else
            g.setColor(FILL_COLOR);
        g.fillRect(x, y, side, hei);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, side, hei);
    }

    private void drawOpponentRectangles(int x, int y, int side, Graphics g, boolean isImparted) {
        if (isEditing || isReady) {
            return;
        }
        if (isImparted)
            g.setColor(IMPART_COLOR);
        else
            g.setColor(FILL_COLOR);
        g.fillRect(x, y, side, hei);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, side, hei);
    }

    private class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new Thread() {
                public void run() {
                    if (e.getSource() == readyButton) {
                        if (e.getActionCommand().equals("Ready"))
                            readyButton.setText("Cancel");
                        else if (e.getActionCommand().equals("Cancel"))
                            readyButton.setText("Ready");

                        if (isReady) {
                            isEditing = true;
                            isReady = false;
                            isInterrupted = true;
                            messageManager.sendReadinessCondition(false);
                        } else {
                            isEditing = false;
                            isReady = true;
                            ui1.setIAmNewSquare(false);
                            messageManager.sendReadinessCondition(true);
                            new Thread() {
                                public void run() {
                                    while (!isInterrupted()) {
                                        if (isOpponentReady) {
                                            ui1.startGame();
                                            startGame();
                                            interrupt();
                                        }
                                    }
                                }
                            }.start();
                        }
                    } else if (e.getSource() == leaveButton) {

                    }
                    if (isEditing) {
                        if (e.getSource() == resetButton) {
                            ui1.reset();
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
                        } else if (e.getSource() == rotateButton) {
                            switch (direction) {
                                case HARIZONTAL:
                                    direction = VERTICAL;
                                    break;
                                case VERTICAL:
                                    direction = HARIZONTAL;
                                    break;
                            }
                            ui1.boundJLabel(direction, position);
                        } else if (e.getSource() == smallSquareButton) {
                            if (ssb == 0) {
                                smallSquareButton.setEnabled(false);
                                return;
                            }
                            ui1.setIAmNewSquare(true);
                            ui1.boundJLabel(direction, 1);
                            position = 1;
                        } else if (e.getSource() == mediumSquareButton) {
                            if (msb == 0) {
                                mediumSquareButton.setEnabled(false);
                                return;
                            }
                            ui1.setIAmNewSquare(true);
                            ui1.boundJLabel(direction, 2);
                            position = 2;
                        } else if (e.getSource() == bigSquareButton) {
                            if (bsb == 0) {
                                bigSquareButton.setEnabled(false);
                                return;
                            }
                            ui1.setIAmNewSquare(true);
                            ui1.boundJLabel(direction, 3);
                            position = 3;
                        } else if (e.getSource() == largeSquareButton) {
                            if (lsb == 0) {
                                largeSquareButton.setEnabled(false);
                                return;
                            }
                            ui1.setIAmNewSquare(true);
                            ui1.boundJLabel(direction, 4);
                            position = 4;
                        }
                    }
                    repaint();
                }
            }.start();
        }
    }

    @Override
    public void impact(int sizeOfShip, int player) {
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

    public void setEditing(boolean bool) {
        isEditing = bool;
        repaint();
    }

    public void startGame() {

    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setReady(boolean bool) {
        isReady = bool;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setMyS1(boolean[] myS1) {
        this.myS1 = myS1;
    }

    public boolean[] getMyS1() {
        return myS1;
    }

    public void setMyS2(boolean[] myS2) {
        this.myS2 = myS2;
    }

    public boolean[] getMyS2() {
        return myS2;
    }

    public void setMyS3(boolean[] myS3) {
        this.myS3 = myS3;
    }

    public boolean[] getMyS3() {
        return myS3;
    }

    public void setMyS4(boolean[] myS4) {
        this.myS4 = myS4;
    }

    public boolean[] getMyS4() {
        return myS4;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    @Override
    public void setOpponentReady(boolean bool) {
        isOpponentReady = bool;
    }

    public boolean isOpponentReady() {
        return isOpponentReady;
    }
}
