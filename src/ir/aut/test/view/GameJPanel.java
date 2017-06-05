package ir.aut.test.view;

import javax.swing.*;
import java.awt.*;

import static ir.aut.test.view.Constants.*;

/**
 * Created by Yana on 05/06/2017.
 */
public class GameJPanel extends JPanel {
    private MySquare[][] mySquares;
    private String sentence;

    public GameJPanel(MySquare[][] mySquares, String sentence) {
        this(mySquares);
        this.sentence = sentence;
    }

    public GameJPanel(MySquare[][] mySquares) {
        this.mySquares = mySquares;
        this.sentence = " ";
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBounds(100, 100, 900, 600);
        setOpaque(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(TEXT_FONT);
        g.drawString(sentence, S_X, S_Y / 2);
        for (int i = 0; i < LEN; i++) {
            for (int j = 0; j < LEN; j++) {
                g.setColor(mySquares[i][j].getBgColor());
                g.fillRect(mySquares[i][j].getmX(), mySquares[i][j].getmY(), SIDE_LENGTH, SIDE_LENGTH);
                g.setColor(mySquares[i][j].gettColor());
                g.drawString(mySquares[i][j].getText(), mySquares[i][j].getmX() + SIDE_LENGTH / 2, mySquares[i][j].getmY() + SIDE_LENGTH / 2);
            }
        }

        g.setColor(LINE_COLOR);
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
