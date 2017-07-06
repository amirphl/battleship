package ir.aut.test.view.squar;

import static ir.aut.test.view.Constants.*;

/**
 * Created by Yana on 06/06/2017.
 */
public class SquaresEditor {

    private Square[][] squares;
    private int mX;
    private int mY;
    private int direction;
    private int jLabelLength;
    private int raw = -1;
    private int column = -1;

    public SquaresEditor(Square[][] squares) {
        this.squares = squares;
        mX = 0;
        mY = 0;
        direction = 0;
        jLabelLength = 0;
    }

    public boolean run() {
        findRawAndColumn();
        if (raw == -1 || column == -1)
            return false;
        if (direction == HARIZONTAL) {
            if (!fillInHarizontal())
                return false;
        } else if (direction == VERTICAL) {
            if (!fillInVertical())
                return false;
        }
        raw = -1;
        column = -1;
        return true;
    }

    private void findRawAndColumn() {
        for (int i = 0; i < LEN; i++) {
            if (S_X + (SIDE_LENGTH * i) < mX && mX < S_X + (SIDE_LENGTH * (i + 1))) {
                column = i;
            }
            if (S_Y + GAP + (SIDE_LENGTH * i) < mY && mY < S_Y + GAP + (SIDE_LENGTH * (i + 1))) {
                raw = i;
            }
        }
    }

    private boolean fillInHarizontal() {
        for (int i = 0; i < jLabelLength; i++) {
            if (column >= LEN || isFill(raw, column))
                return false;
            column += 1;
        }
        fillMyRectangleInHarizontalMode();
        return true;
    }

    private void fillMyRectangleInHarizontalMode() {
        column -= jLabelLength;
        for (int i = 0; i < jLabelLength; i++) {
            squares[raw][column++].fill();
        }
    }

    private boolean isFill(int i, int j) {
        for (int k = i - 1; k < i + 2; k++) {
            for (int z = j - 1; z < j + 2; z++) {
                try {
                    if (squares[k][z].isFill())
                        return true;
                } catch (NullPointerException e) {

                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
        return false;
    }

    private boolean fillInVertical() {
        for (int j = 0; j < jLabelLength; j++) {
            if (raw >= LEN || isFill(raw, column))
                return false;
            raw += 1;
        }
        fillMyRectangleInVerticalMode();
        return true;
    }

    private void fillMyRectangleInVerticalMode() {
        raw -= jLabelLength;
        for (int i = 0; i < jLabelLength; i++) {
            squares[raw++][column].fill();
        }
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

    public void setJLabelLength(int jLabelLength) {
        this.jLabelLength = jLabelLength;
    }
}
