package ir.aut.test.view;

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
    private GameJPanel gameJPanel;
    private ChatJPanel chatJPanel;
    private ShipsJPanel shipsJPanel;
    private JLabel jLabel;
    private MySquare[][] mySquares;
    private String sentence;
    private boolean mode;
    private int jLabelDirection = 0;
    private boolean isJLabelUsed = false;
    private int n = 0;
    private SquaresEditor squaresEditor;

    public Frame(String sentence, boolean mode) {
        this.sentence = sentence;
        this.mode = mode;
        frame = new JFrame("Battle Ship");
        frame.setLayout(null);
        frame.setSize(new Dimension(WIDTH_OF_FRAME, HEIGHT_OF_FRAME));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        setOpaque(true);
        frame.setContentPane(this);
        frame.addMouseMotionListener(this);
        frame.addMouseListener(this);
        mySquares = new MySquare[LEN][LEN];
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                mySquares[i][j] = new MySquare(10 * i + j, S_X + j * SIDE_LENGTH, S_Y + i * SIDE_LENGTH);
            }
        }
        gameJPanel = new GameJPanel(mySquares, sentence);
        chatJPanel = new ChatJPanel();
        shipsJPanel = new ShipsJPanel(mode, this);
        add(gameJPanel, 0);
        add(chatJPanel, 0);
        add(shipsJPanel, 0);
        if (mode == true)
            createJLabel();
        squaresEditor = new SquaresEditor(mySquares);
        frame.revalidate();
        frame.setVisible(true);
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

    @Override
    public void createSquareJLabel(int direction, int n) {
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
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (jLabel != null) {
            jLabel.setLocation(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        squaresEditor.setmX(e.getX());
        squaresEditor.setmY(e.getY());
        squaresEditor.setDirection(jLabelDirection);
        squaresEditor.setN(n);
        if (!isJLabelUsed)
            squaresEditor.run();
        isJLabelUsed = true;
        repaint();
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
}
