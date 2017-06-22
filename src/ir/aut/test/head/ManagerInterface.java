package ir.aut.test.head;

/**
 * Created by Yana on 21/06/2017.
 */
public interface ManagerInterface {
    void connectToServer(String ip, int port, String playerName);

    void waitForClient(int port, String playerName);

    void startGameS();

    void startGameC();
}
