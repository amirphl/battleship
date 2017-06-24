package ir.aut.test.view.first;

/**
 * Created by Yana on 21/06/2017.
 */
public interface RCFCallBack {
    void addIPJPanel(String opponentName, String opponentIP);

    void acceptRequest(int i);

    void deleteJPanel(int i);

    void deleteByIP(String ip);
}
