package ir.aut.test.view;

import ir.aut.test.logic.MessageManager;

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
    private OrderingJPanel orderingJPanel;
    private ChatJPanel chatJPanel;
    private ShipsJPanel shipsJPanel;
    private JLabel jLabel;
    private Square[][] mySquares;
    private Square[][] opponentSquares;
    private String sentence = "Please Arrange your Field.";
    private boolean myTurn = true;
    private boolean isGameStarted = false;
    /**
     * mode : true ----> in chapter of editing and setting ships in map.
     * mode : false ----> in chapter of playing .
     */
    private boolean mode = true;
    /**
     * jLabelDirection = 0 ----> HARIZONTAL .
     * jLabelDirection = 1 ----> VERTICAL .
     */
    private int jLabelDirection = 0;
    private boolean isJLabelUsed = false;
    /**
     * n : Length of ship .
     */
    private int n = 0;
    private SquaresEditor squaresEditor;
    private boolean iAmNewSquare = true;

    public Frame(MessageManager messageManager, String station, String myName) {
        this.messageManager = messageManager;
        this.station = station;
        this.myName = myName;
        frame = new JFrame("Battle Ship");
        frame.setLayout(null);
        frame.setSize(new Dimension(WIDTH_OF_FRAME, HEIGHT_OF_FRAME));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        setOpaque(true);
        frame.setContentPane(this);
        frame.addMouseMotionListener(this);
        frame.addMouseListener(this);

        createSquares();
        orderingJPanel = new OrderingJPanel(mySquares, sentence);
        chatJPanel = new ChatJPanel();
        shipsJPanel = new ShipsJPanel(this, messageManager, myName);
        createJLabel();
        add(orderingJPanel, 0);
        add(chatJPanel, 0);
        add(shipsJPanel, 0);
        squaresEditor = new SquaresEditor(mySquares);
        frame.revalidate();
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
        add(jLabel, new Integer(2), 1);
    }

    private void removeJLabel() {
        remove(jLabel);
    }

    @Override
    public void boundJLabel(int direction, int n) {
        jLabelDirection = direction;
        isJLabelUsed = false;
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
                mySquares[i][j].fill(false);
            }
        }
        repaint();
    }

    @Override
    public void setIAmNewSquare(boolean bool) {
        iAmNewSquare = bool;
    }

    @Override
    public void startGame() {
        isGameStarted = true;
        removeJLabel();
        messageManager.setFrame(this);
        if (station.equals(SERVER))
            setMyTurn(true);
        else
            setMyTurn(false);
    }

    @Override
    public void impartMySquares(int i, int j) {
        try {
            if (mySquares[i][j].isFill()) {
                mySquares[i][j].destroy(true);
                mySquares[i][j].setText("#");
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
    public void impartOpponentSquares(int i, int j, int condition) {
        if (condition == 1) {
            opponentSquares[i][j].destroy(true);
            opponentSquares[i][j].setText("#");
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
                if (isGameStarted) {
                    if (myTurn) {
                        messageManager.sendLocation((e.getY() - S_Y - 30) / SIDE_LENGTH, (e.getX() - S_X) / SIDE_LENGTH, 3);
                    } else {

                    }
                } else {
                    squaresEditor.setmX(e.getX());
                    squaresEditor.setmY(e.getY());
                    squaresEditor.setDirection(jLabelDirection);
                    squaresEditor.setN(n);
                    if (isJLabelUsed || !iAmNewSquare)
                        return;
                    if (squaresEditor.run()) {
                        isJLabelUsed = true;
                        iAmNewSquare = false;
                        shipsJPanel.changeTextOfButton();
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

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public boolean getMode() {
        return mode;
    }

    public String getSentence() {
        return sentence;
    }

    public boolean getIAmNewSquare() {
        return iAmNewSquare;
    }

    @Override
    public void setMyTurn(boolean bool) {
        myTurn = bool;
        remove(orderingJPanel);
        if (myTurn) {
            orderingJPanel = new OrderingJPanel(opponentSquares, "Your Turn.");
        } else {
            orderingJPanel = new OrderingJPanel(mySquares, "Opponent Turn.");
        }
        add(orderingJPanel, 0);
        frame.revalidate();
    }
}
