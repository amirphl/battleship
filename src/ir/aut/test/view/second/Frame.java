package ir.aut.test.view.second;

import ir.aut.test.logic.MessageManager;
import ir.aut.test.view.squar.Square;
import ir.aut.test.view.squar.SquaresEditor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import static ir.aut.test.view.Constants.*;

/**
 * Created by Yana on 05/06/2017.
 */
public class Frame extends JLayeredPane implements MouseMotionListener, UI1, MouseListener {
    private JFrame frame;
    private MessageManager messageManager;
    private String station;
    private String myName = " ";
    private String opponentName = " ";
    private OrderingJPanel orderingJPanel;
    private ChatJFrame chatJFrame;
    private ShipsJPanel shipsJPanel;
    private JLabel jLabel;
    private Square[][] mySquares;
    private Square[][] opponentSquares;
    private boolean myTurn = true;
    private boolean abstractTurn = true;
    private boolean matchCondition = false;
    /**
     * jLabelDirection = 0 ----> HARIZONTAL .
     * jLabelDirection = 1 ----> VERTICAL .
     */
    private int jLabelDirection = 0;
    /**
     * n : Length of ship .
     */
    private int n = 0;
    private SquaresEditor squaresEditor;
    private int numberOfDestroyedUnits = 0;

    public Frame(MessageManager messageManager, String station, String myName) {
        this.messageManager = messageManager;
        this.station = station;
        this.myName = myName;
        frame = new JFrame("Battle Ship");
        frame.setLayout(null);
        frame.setSize(new Dimension(WIDTH_OF_FRAME, HEIGHT_OF_FRAME));
        frame.setLocation(100, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        setOpaque(true);
        frame.setContentPane(this);
        frame.addMouseMotionListener(this);
        frame.addMouseListener(this);

        new Thread() {
            public void run() {
                messageManager.setFrame(Frame.this);
                messageManager.sendMyName(myName);
            }
        }.start();

        createSquares();

        orderingJPanel = new OrderingJPanel(mySquares, "Please Arrange your Field.");
        shipsJPanel = new ShipsJPanel(this, messageManager);
        add(orderingJPanel, 0);
        add(shipsJPanel, 0);
        squaresEditor = new SquaresEditor(mySquares);
        frame.setVisible(true);
    }

    private void createSquares() {
        mySquares = new Square[LEN][LEN];
        opponentSquares = new Square[LEN][LEN];
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                mySquares[i][j] = new Square(10 * i + j, S_X + j * SIDE_LENGTH, S_Y + i * SIDE_LENGTH);
                opponentSquares[i][j] = new Square(10 * i + j, S_X + j * SIDE_LENGTH, S_Y + i * SIDE_LENGTH);
            }
        }
    }

    private void createJLabel() {
        jLabel = new JLabel();
        jLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        try {
            jLabel.setIcon(new ImageIcon(ImageIO.read(new File("jLabelIcon.jpg"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(jLabel, 2, 1);
    }

    @Override
    public void removeJLabel() {
        remove(jLabel);
    }

    @Override
    public void boundJLabel(int direction, int n) {
        createJLabel();
        jLabelDirection = direction;
        this.n = n;
        switch (direction) {
            case HARIZONTAL:
                jLabel.setBounds(0, 0, n * SIDE_LENGTH, SIDE_LENGTH);
                break;
            case VERTICAL:
                jLabel.setBounds(0, 0, SIDE_LENGTH, n * SIDE_LENGTH);
                break;
        }
    }

    @Override
    public void reset() {
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                mySquares[i][j].clear();
            }
        }
        repaint();
    }

    @Override
    public void startGame() {
        matchCondition = true;
        removeJLabel();
        new Terminator().start();
        if (station.equals(SERVER))
            setMyTurn(true);
        else
            setMyTurn(false);
    }

    @Override
    public void destroyMyShips(int i, int j) {
        try {
            if (mySquares[i][j].isFill()) {
                mySquares[i][j].destroy();
                messageManager.sendLocation(i, j, 1);
            } else if (mySquares[i][j].isDestroyed()) {

            } else {
                mySquares[i][j].setText("*");
                messageManager.sendLocation(i, j, 0);
                setMyTurn(true);
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        repaint();
    }

    @Override
    public void destroyOpponentShips(int i, int j, int condition) {
        if (condition == 1) {
            opponentSquares[i][j].destroy();
            numberOfDestroyedUnits++;
        } else {
            opponentSquares[i][j].setText("*");
        }
        remove(orderingJPanel);
        orderingJPanel = new OrderingJPanel(opponentSquares, "Your Turn");
        add(orderingJPanel, 0);
        frame.revalidate();
        if (condition == 0) {
            remove(orderingJPanel);
            orderingJPanel = new OrderingJPanel(opponentSquares, "Transforming ...");
            add(orderingJPanel, 0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setMyTurn(false);
        }
        abstractTurn = true;
    }

    @Override
    public void setMyTurn(boolean bool) {
        myTurn = bool;
        remove(orderingJPanel);
        if (myTurn) {
            orderingJPanel = new OrderingJPanel(opponentSquares, "Your Turn");
        } else {
            orderingJPanel = new OrderingJPanel(mySquares, opponentName + "'s Turn");
        }
        add(orderingJPanel, 0);
        frame.revalidate();
    }

    @Override
    public String getMyName() {
        return myName;
    }

    @Override
    public void setOpponentName(String username) {
        opponentName = username;
        chatJFrame = new ChatJFrame(messageManager, opponentName);
        add(chatJFrame, 0);
        revalidate();
    }

    @Override
    public String getOpponentName() {
        return opponentName;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        new Thread() {
            public void run() {
                if (jLabel != null) {
                    jLabel.setLocation(e.getX(), e.getY());
                }
            }
        }.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        new Thread() {
            public void run() {
                if (matchCondition) {
                    if (myTurn) {
                        sendClickedPoint(e);
                    } else {

                    }
                } else {
                    squaresEditor.setmX(e.getX());
                    squaresEditor.setmY(e.getY());
                    squaresEditor.setDirection(jLabelDirection);
                    squaresEditor.setN(n);
                    if (squaresEditor.run()) {
                        shipsJPanel.changeTextOfButton();
                        removeJLabel();
                        shipsJPanel.enableButtons();
                        repaint();
                    }
                }
            }
        }.start();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void sendClickedPoint(MouseEvent e) {
        if (abstractTurn)
            messageManager.sendLocation((e.getY() - S_Y - 30) / SIDE_LENGTH, (e.getX() - S_X) / SIDE_LENGTH, 3);
        abstractTurn = false;
    }

    private class Terminator extends Thread {
        public void run() {
            while (matchCondition) {
                if (numberOfDestroyedUnits == 20) {
                    win();
                    break;
                } else {
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void win() {
        matchCondition = false;
        JOptionPane.showMessageDialog(null, "Congratulation !!! You won the match ...", "Game ended.", JOptionPane.INFORMATION_MESSAGE);
        messageManager.sendTerminate();
    }

    @Override
    public void loose() {
        matchCondition = false;
        JOptionPane.showMessageDialog(null, "How bad !!! You lost ...", "Game ended.", JOptionPane.INFORMATION_MESSAGE);
    }
}
