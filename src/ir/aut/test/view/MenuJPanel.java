package ir.aut.test.view;

import javax.swing.*;
import java.awt.*;

import static ir.aut.test.view.Constants.TEXT_FONT;

/**
 * Created by Yana on 05/06/2017.
 */
public class MenuJPanel extends JPanel {
    public MenuJPanel() {
        setBackground(Color.WHITE);
        setBounds(0, 0, 150, 30);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.BLUE));
        setSize(new Dimension(150, 30));
        setLayout(new FlowLayout());
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
