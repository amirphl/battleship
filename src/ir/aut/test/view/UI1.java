package ir.aut.test.view;

/**
 * Created by Yana on 05/06/2017.
 */
public interface UI1 {
    void boundJLabel(int direction, int n);

    void reset();

    void setIAmNewSquare(boolean bool);

    void startGame();

    void setMyTurn(boolean bool);

    void impartMySquares(int i, int j);

    void impartOpponentSquares(int i, int j, int condition);
}
