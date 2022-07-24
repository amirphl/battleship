package ir.aut.main.view.second;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Yana on 06/07/2017.
 */
public class HistoryJPanel extends JPanel {

    private JScrollPane scrollPane;
    private CJPanel cJPanel;

    public HistoryJPanel() {
        cJPanel = new CJPanel();
        scrollPane = new JScrollPane(cJPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
        addConversation();
    }

    private void addConversation() {
        File f = new File("./ConversationsHistory/");
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
        for (File fi : files) {
            cJPanel.addButton(fi);
            repaint();
        }
    }
}
