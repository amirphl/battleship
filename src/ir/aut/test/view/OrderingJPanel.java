package ir.aut.test.view;

import javax.swing.*;
import java.awt.*;

import static ir.aut.test.view.Constants.*;

/**
 * This class provides a JPanel that player can order ships and set them in to the map.
 * this class works with an array of MySquare[][] and paint squares on map according the features in
 * each member of array .
 * Created by Yana on 05/06/2017.
 */
public class OrderingJPanel extends JPanel {
    private MySquare[][] mySquares;
    private String sentence = " ";
    private MenuJPanel menuJPanel;

    public OrderingJPanel(MySquare[][] mySquares, String sentence) {
        this(mySquares);
        this.sentence = sentence;
    }

    public OrderingJPanel(MySquare[][] mySquares) {
        this.mySquares = mySquares;
        setLayout(null);
        menuJPanel = new MenuJPanel();
        add(menuJPanel);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setSize(new Dimension(WIDTH_OF_ORDERINGJPANEL, HEIGHT_OF_ORDERINGJPANEL));
        setBounds(0, 0, WIDTH_OF_ORDERINGJPANEL, HEIGHT_OF_ORDERINGJPANEL);
        setOpaque(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(TEXT_FONT);
        g.drawString(sentence, S_X, S_Y - 15);
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                g.setColor(mySquares[i][j].getBgColor());
                g.fillRect(mySquares[i][j].getmX(), mySquares[i][j].getmY(), SIDE_LENGTH, SIDE_LENGTH);
                g.setColor(mySquares[i][j].gettColor());
                g.drawString(mySquares[i][j].getText(), mySquares[i][j].getmX() + SIDE_LENGTH / 2, mySquares[i][j].getmY() + SIDE_LENGTH / 2);
            }
        }

        g.setColor(Color.BLACK);
        int x = mySquares[0][0].getmX();
        int y = mySquares[0][0].getmY();
        for (int i = 0; i < LEN + 1; i++) {
            g.drawLine(x + i * SIDE_LENGTH, y, x + i * SIDE_LENGTH, y + LEN * SIDE_LENGTH);
            g.drawLine(x, y + i * SIDE_LENGTH, x + LEN * SIDE_LENGTH, y + i * SIDE_LENGTH);
        }
    }

    public void setMySquares(MySquare[][] mySquares) {
        this.mySquares = mySquares;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public MySquare[][] getMySquares() {
        return mySquares;
    }

    public String getSentence() {
        return sentence;
    }
}
