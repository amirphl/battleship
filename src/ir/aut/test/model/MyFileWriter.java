package ir.aut.test.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yana on 07/07/2017.
 */
public class MyFileWriter {

    private String myName;
    private String opponentName;
    private JSONObject obj;
    private File path;
    private JSONArray cList;
    private JSONArray playerList;
    private DateFormat dateFormat;

    public MyFileWriter(String myName, String opponentName) {
        this.myName = myName;
        this.opponentName = opponentName;
        createPath();
    }

    private void createPath() {
        int i = new File("d:\\ConversationsHistory").listFiles().length;
        path = new File("d:\\ConversationsHistory\\" + "chat" + (++i) + "-to-" + opponentName + ".json");
        cList = new JSONArray();
        playerList = new JSONArray();
        cList.add(myName);
        cList.add(opponentName);
    }

    public void write(String m, int player) {
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        m = m + "\n" + dateFormat.format(new Date());
        obj = new JSONObject();
        cList.add(m);
        if (player == 1) {
            playerList.add("p1");
        } else {
            playerList.add("p2");
        }

        obj.put("1", cList);
        obj.put("2", playerList);

        dateFormat = new SimpleDateFormat("(yyyy-MM-dd)-HH:mm:ss");
        Date date = new Date();
        obj.put("3", dateFormat.format(date));

        try (FileWriter file = new FileWriter(path)) {
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
