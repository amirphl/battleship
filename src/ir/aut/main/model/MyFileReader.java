package ir.aut.main.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Yana on 07/07/2017.
 */
public class MyFileReader extends JFrame {

    private File file;
    private JSONParser parser;
    private JTextArea textArea;

    public MyFileReader(File file) {
        this.file = file;
        setLayout(new FlowLayout());
        setSize(new Dimension(300, 600));
        setLocation(1000, 600);
        read();
    }


    private void read() {
        parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray cList = (JSONArray) jsonObject.get("1");
            JSONArray pList = (JSONArray) jsonObject.get("2");

//                System.out.println(cList);
//                System.out.println(pList);

            String oName = (String) cList.get(1);

            setTitle(oName);

            for (int k = 2; k < cList.size(); k++) {
                if (pList.get(k - 2).equals("p1")) {
                    textArea = new JTextArea("Me:" + "\n" + cList.get(k));
                } else {
                    textArea = new JTextArea(oName + ":" + "\n" + cList.get(k));
                }
                textArea.setEnabled(false);
                textArea.setFont(new Font("Sanserif", Font.PLAIN, 18));
                textArea.setBackground(Color.GREEN);
                textArea.setDisabledTextColor(Color.BLACK);
                add(textArea);
            }
            setVisible(true);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }
}
