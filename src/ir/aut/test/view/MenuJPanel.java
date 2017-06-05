package ir.aut.test.view;

import javax.swing.*;
import java.awt.*;

import static ir.aut.test.view.Constants.*;

/**
 * Created by Yana on 05/06/2017.
 */
public class MenuJPanel extends JPanel {

    public MenuJPanel() {
//        setLayout(new FlowLayout());
        setSize(new Dimension(WIDTH_OF_MENUJPANEL, HEIGHT_OF_MENUJPANEL));
        setBounds(S_X, S_Y - 80, WIDTH_OF_MENUJPANEL, HEIGHT_OF_MENUJPANEL);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.BLUE));

        JMenuBar menuBar = new JMenuBar();
        add(menuBar);
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
        fileMenu.setFont(TEXT_FONT);
        helpMenu.setFont(TEXT_FONT);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
//        JMenuItem newAction = new JMenuItem("New");
//        newAction.setBackground(Color.GRAY);
//        newAction.setFont(font);
//        fileMenu.add(newAction);
    }
}
