package ir.aut.test.view;

import static ir.aut.test.view.Constants.*;

/**
 * Created by Yana on 06/06/2017.
 */
public class SquaresEditor {

    private MySquare[][] mySquares;
    private int mX;
    private int mY;
    private int direction;
    private int n;
    private int raw = -1;
    private int column = -1;

    public SquaresEditor(MySquare[][] mySquares) {
        this.mySquares = mySquares;
        mX = 0;
        mY = 0;
        direction = 0;
        n = 0;
    }

    public void run() {
        findRawAndColumn();
        if (raw == -1 || column == -1)
            return;
        if (direction == HARIZONTAL)
            fillInHarizontal();
        else if (direction == VERTICAL)
            fillInVertical();
        raw = -1;
        column = -1;
    }

    private void findRawAndColumn() {
        for (int i = 0; i < LEN; i++) {
            if (S_X + (SIDE_LENGTH * i) < mX && mX < S_X + (SIDE_LENGTH * (i + 1))) {
                column = i;
            }
            if (S_Y + gap + (SIDE_LENGTH * i) < mY && mY < S_Y + gap + (SIDE_LENGTH * (i + 1))) {
                raw = i;
            }
        }
    }

    private void fillInHarizontal() {
        for (int i = 0; i < n; i++) {
            if (column >= LEN)
                return;
            if (isFill(raw, column)) {
                return;
            }
            column += 1;
        }
        fillMyRectangleInHarizontalMode();
    }

    private void fillMyRectangleInHarizontalMode() {
        column -= n;
        for (int i = 0; i < n; i++) {
            mySquares[raw][column++].fill(true);
        }
    }

    private boolean isFill(int i, int j) {
        for (int k = i - 1; k < i + 2; k++) {
            for (int z = j - 1; z < j + 2; z++) {
                try {
                    if (mySquares[k][z].isFill())
                        return true;
                } catch (NullPointerException e) {

                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
        return false;
    }

    private void fillInVertical() {
        for (int j = 0; j < n; j++) {
            if (raw >= LEN)
                return;
            if (isFill(raw, column)) {
                return;
            }
            raw += 1;
        }
        fillMyRectangleInVerticalMode();
    }

    private void fillMyRectangleInVerticalMode() {
        raw -= n;
        for (int i = 0; i < n; i++) {
            mySquares[raw++][column].fill(true);
        }
    }

    public void setMySquares(MySquare[][] mySquares) {
        this.mySquares = mySquares;
    }

    public void setmX(int mX) {
        this.mX = mX;
    }

    public void setmY(int mY) {
        this.mY = mY;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setN(int n) {
        this.n = n;
    }

    public MySquare[][] getMySquares() {
        return mySquares;
    }

    public int getmX() {
        return mX;
    }

    public int getmY() {
        return mY;
    }

    public int getDirection() {
        return direction;
    }

    public int getN() {
        return n;
    }
}
