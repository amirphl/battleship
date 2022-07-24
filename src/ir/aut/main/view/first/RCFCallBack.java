package ir.aut.main.view.first;

/**
 * Created by Yana on 21/06/2017.
 */
public interface RCFCallBack {
    void addSubJPanel(String opponentName, String opponentIP);

    void acceptRequest(int i);

    void deleteSubJPanel(int i);

    void deleteByIP(String ip);
}
