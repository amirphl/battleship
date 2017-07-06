package ir.aut.test.view.squar;

import java.awt.*;

import static ir.aut.test.view.Constants.FILL_COLOR;
import static ir.aut.test.view.Constants.IMPACT_COLOR;

/**
 * Created by Yana on 05/06/2017.
 */
public class Square {
    private int id;
    private int mX;
    private int mY;
    private Color bgColor = Color.WHITE;
    private Color tColor = Color.BLACK;
    private String text = " ";
    private boolean isFill = false;
    private boolean isDestroyed = false;

    public Square(int id, int mX, int mY) {
        this.id = id;
        this.mX = mX;
        this.mY = mY;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public void fill(boolean bool) {
//        isFill = bool;
//        if (bool) {
//            bgColor = FILL_COLOR;
//        } else {
//            bgColor = Color.WHITE;
//        }
//    }

    public void fill() {
        isFill = true;
        bgColor = FILL_COLOR;
    }

    public void clear() {
        isFill = false;
        bgColor = Color.WHITE;
    }

//    public void destroy(boolean bool) {
//        isDestroyed = bool;
//        if (bool) {
//            bgColor = IMPART_COLOR;
//            return;
//        } else {
//            bgColor = Color.WHITE;
//        }
//    }

    public void destroy() {
        isDestroyed = true;
        bgColor = IMPACT_COLOR;
        setText("@");
    }

    public int getmX() {
        return mX;
    }

    public int getmY() {
        return mY;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Color gettColor() {
        return tColor;
    }

    public String getText() {
        return text;
    }

    public boolean isFill() {
        return isFill;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
