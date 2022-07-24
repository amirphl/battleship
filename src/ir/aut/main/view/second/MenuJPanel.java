package ir.aut.main.view.second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ir.aut.main.view.Constants.*;

/**
 * Created by Yana on 05/06/2017.
 */
public class MenuJPanel extends JPanel {

    public MenuJPanel() {
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
        JMenuItem menuItem = new JMenuItem("History");
        fileMenu.add(menuItem);
        menuItem.addActionListener(new Handler());
    }

    private class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Conversations History");
            frame.setLocation(800, 400);
            frame.setSize(new Dimension(300, 600));
            frame.add(new HistoryJPanel());
            frame.setVisible(true);
        }
    }
}
