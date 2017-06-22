package ir.aut.test.view.second;

/**
 * Created by Yana on 05/06/2017.
 */
public interface UI1 {

    void removeJLabel();

    void boundJLabel(int direction, int n);

    void reset();

    void startGame();

    void setMyTurn(boolean bool);

    void destroyMyShips(int i, int j);

    void destroyOpponentShips(int i, int j, int condition);

    String getMyName();

    void setOpponentName(String username);

    String getOpponentName();
}
