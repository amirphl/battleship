package ir.aut.test.view.second;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * Created by Yana on 22/06/2017.
 */
public class ChatJPanel extends JPanel {

    private String myName;
    private String opponentName;
    private JTextArea textArea;

    public ChatJPanel(String myName, String opponentName) {
        this.myName = myName;
        this.opponentName = opponentName;
        setLayout(new GridLayout(300, 1, 10, 10));
    }

    public void addText(String text, int player) {
        long time = System.currentTimeMillis();
        String t = new Date(time).toString().replace(":", "-");
        switch (player) {
            case 1:
                textArea = new JTextArea("You :" + "\n" + text + "\n" + t);
                break;
            case 2:
                textArea = new JTextArea(opponentName + " :" + "\n" + text + "\n" + t);
                break;
        }
        textArea.setDisabledTextColor(Color.BLACK);
        textArea.setFont(new Font("SanSerif", Font.ITALIC, 16));
        textArea.setBackground(Color.YELLOW);
        textArea.setEnabled(false);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(textArea);
        revalidate();
    }
}
