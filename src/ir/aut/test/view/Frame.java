package ir.aut.test.view;

import javax.swing.*;
import java.awt.*;

import static ir.aut.test.view.Constants.*;

/**
 * Created by Yana on 05/06/2017.
 */
public class Frame extends JFrame {
    private GameJPanel gameJPanel;
    private ChatJPanel chatJPanel;
    private ShipsJPanel shipsJPanel;
    private MySquare[][] mySquares;
    private Container container;
    private MenuJPanel menuJPanel;

    public Frame() {
        super("Battle Ship");
        setLayout(null);
        setSize(new Dimension(WIDTH_OF_FRAME, HEIGHT_OF_FRAME));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        container = getContentPane();
        mySquares = new MySquare[10][10];
        for (int i = 0; i < mySquares[0].length; i++) {
            for (int j = 0; j < mySquares.length; j++) {
                mySquares[i][j] = new MySquare(10 * i + j, S_X + j * SIDE_LENGTH, S_Y + i * SIDE_LENGTH, Color.WHITE, Color.GREEN, "f");
            }
        }

        gameJPanel = new GameJPanel(mySquares, "Alis turn");
        chatJPanel = new ChatJPanel();
        shipsJPanel = new ShipsJPanel(true);
        setSize(1200, 900);
        getContentPane().add(gameJPanel/*, BorderLayout.CENTER*/);
        getContentPane().add(chatJPanel);
        getContentPane().add(shipsJPanel);
        setVisible(true);
        revalidate();
    }
}
