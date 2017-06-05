package ir.aut.test.view;

import javax.swing.*;
import java.awt.*;

import static ir.aut.test.view.Constants.HEIGHT_OF_FRAME;
import static ir.aut.test.view.Constants.WIDTH_OF_FRAME;
import static ir.aut.test.view.Constants.WIDTH_OF_GAMEJPANEL;

/**
 * Created by Yana on 05/06/2017.
 */
public class ChatJPanel extends JPanel {
    public ChatJPanel() {
        setBorder(BorderFactory.createLineBorder(Color.orange));
        setSize(new Dimension(WIDTH_OF_FRAME - WIDTH_OF_GAMEJPANEL - 1, HEIGHT_OF_FRAME - 40));
        setBounds(WIDTH_OF_GAMEJPANEL + 1, 0, WIDTH_OF_FRAME - WIDTH_OF_GAMEJPANEL - 20, HEIGHT_OF_FRAME - 40);
        setOpaque(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
//        g.fillRect( );
    }
}
