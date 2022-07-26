package ir.aut.main.view.second;

/**
 * Created by Yana on 05/06/2017.
 */
public interface FrameCallBack {
    void removeJLabel();

    void boundJLabel(int direction, int n);

    void reset();

    void startGame();

    void check();

    void setMyTurn(boolean bool);

    void destroyMyShips(int i, int j);

    void destroyOpponentShips(int i, int j, int condition);

//    String getMyName();

    void setOpponentName(String username);

    String getOpponentName();

    void loose();

    void win(int i);
}
