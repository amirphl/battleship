package ir.aut.main.main.rogram;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class JsonSimpleWriteExample {

    public static void main(String[] args) {

        method2();
//        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//        Date date = new Date();
//        System.out.println(dateFormat.format(date));

//        method();
//        JSONObject obj = new JSONObject();
//        obj.put("name", "mkyong.com");
//        obj.put("age", new Integer(100));
//
//        JSONArray list = new JSONArray();
//        list.add("msg 1");
//        list.add("msg 2");
//        list.add("msg 3");
//
//        obj.put("messages", list);
//
//        try (FileWriter file = new FileWriter("..main.json")) {
//
//            file.write(obj.toJSONString());
//            file.flush();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        System.out.print(obj);
    }

    public static void method() {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("./ConversationsHistory/chat1-to-amir.json"));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);

            String name = (String) jsonObject.get("name");
            System.out.println(name);

            long age = (Long) jsonObject.get("age");
            System.out.println(age);

            // loop array
            JSONArray msg = (JSONArray) jsonObject.get("messages");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static class MyKeyListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == '\177') {
                // delete row method (when "delete" is typed)
                System.out.println("Key \"Delete\" Typed");
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println(e.getKeyChar());
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                // delete row method (when "delete" is pressed)
                System.out.println("Key \"Delete\" Pressed");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ALT) {
                // delete row method (when "delete" is released)
                System.out.println("Key \"Delete\" Released");
            }
        }
    }

    private  static  void method2(){
        JFrame f = new JFrame();
        f.setSize(new Dimension(410, 330));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(null);
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        panel.setBounds(50, 50, 300, 200);

        panel.addKeyListener(new MyKeyListener()); // add KeyListener
        panel.setFocusable(true); // set focusable to true
        panel.requestFocusInWindow(); // request focus

        f.getContentPane().add(panel);
        f.setVisible(true);
    }

}
