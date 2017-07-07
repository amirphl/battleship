package ir.aut.test.view.second;

import ir.aut.test.model.MyFileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Yana on 06/07/2017.
 */
public class CJPanel extends JPanel {

    private String opponentName;
    private ArrayList<File> fList;
    private int counter = 1;
    private JSONParser parser;

    public CJPanel() {
        setLayout(new GridLayout(300, 1, 10, 10));
        fList = new ArrayList<>();
    }

    public void addButton(File file) {
        parser = new JSONParser();
        try {
            fList.add(file);
            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray cList = (JSONArray) jsonObject.get("1");
            opponentName = (String) cList.get(1);

            String lastTime = (String) jsonObject.get("3");

            JButton jButton = new JButton((counter++) + "- " + opponentName + " " + lastTime);
            jButton.setFont(new Font("SanSerif", Font.ITALIC, 16));
            jButton.setBackground(Color.YELLOW);
            jButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jButton.addActionListener(new Handler());
            add(jButton);
            revalidate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = Integer.valueOf(e.getActionCommand().substring(0, 1));
            new MyFileReader(fList.get((i - 1)));
        }
    }
}
