package ir.aut.test.view;

import java.awt.*;

/**
 * Created by Yana on 05/06/2017.
 */
public class MySquare {
    private int id;
    private int mX;
    private int mY;
    private Color bgColor;
    private Color tColor;
    private String text;

    public MySquare() {
    }

    public MySquare(int id, int mX, int mY) {
        this.id = id;
        this.mX = mX;
        this.mY = mY;
        bgColor = Color.WHITE;
        tColor = Color.BLACK;
        text = "";
    }

    public MySquare(int id, int mX, int mY, Color bgColor) {
        this(id, mX, mY);
        this.bgColor = bgColor;
    }

    public MySquare(int id, int mX, int mY, Color bgColor, Color tColor, String text) {
        this(id, mX, mY, bgColor);
        this.text = text;
        this.tColor = tColor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setmX(int mX) {
        this.mX = mX;
    }

    public void setmY(int mY) {
        this.mY = mY;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public void settColor(Color tColor) {
        this.tColor = tColor;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
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
}
