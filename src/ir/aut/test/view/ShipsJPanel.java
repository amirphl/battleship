package ir.aut.test.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static ir.aut.test.view.Constants.*;

/**
 * Created by Yana on 05/06/2017.
 */
public class ShipsJPanel extends JPanel {
    private JButton resetButton;
    private JButton readyButton;
    private JButton leaveButton;
    private JButton rotateButton;
    private boolean isEditing;
    private int[] s1 = {0, 0, 0, 0};
    private int[] s2 = {0, 0, 0};
    private int[] s3 = {0, 0};
    private int[] s4 = {0};
    private final int hei = 20;
    private Handler handler;
    private JButton smallSquareButton;
    private JButton mediumSquareButton;
    private JButton bigSquareButton;
    private JButton largeSquareButton;

    public ShipsJPanel(boolean isEditing) {
        this.isEditing = isEditing;
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        setSize(new Dimension(WIDTH_OF_GAMEJPANEL, HEIGHT_OF_FRAME - 40));
        setBounds(0, HEIGHT_OF_GAMEJPANEL + 1, WIDTH_OF_GAMEJPANEL, HEIGHT_OF_FRAME - HEIGHT_OF_GAMEJPANEL - 40 - 1);
        setOpaque(true);
        handler = new Handler();
        createButtons();
    }

    private void createButtons() {
        if (isEditing == true) {
            resetButton = new JButton("Reset");
            readyButton = new JButton("Ready");
            rotateButton = new JButton("Rotate");
            try {
                resetButton.setIcon(new ImageIcon(ImageIO.read(new File("reset.jpg"))));
                readyButton.setIcon(new ImageIcon(ImageIO.read(new File("ready.jpg"))));
                rotateButton.setIcon(new ImageIcon(ImageIO.read(new File("rotate.jpg"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            resetButton.setFont(TEXT_FONT);
            readyButton.setFont(TEXT_FONT);
            rotateButton.setFont(TEXT_FONT);
            resetButton.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            readyButton.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            rotateButton.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            resetButton.setBounds(500, 30, 100, 30);
            resetButton.setOpaque(true);
            readyButton.setBounds(620, 30, 110, 30);
            readyButton.setOpaque(true);
            rotateButton.setBounds(750, 30, 120, 30);
            rotateButton.setOpaque(true);
            resetButton.setToolTipText("For Reset , Press me");
            readyButton.setToolTipText("To start , Press me");
            rotateButton.setToolTipText("To Rotate rectangle , Press me");
            resetButton.addActionListener(handler);
            readyButton.addActionListener(handler);
            rotateButton.addActionListener(handler);
            add(resetButton);
            add(readyButton);
            add(rotateButton);
            drawSButtons();
        } else {
            leaveButton = new JButton("Leave");
            leaveButton.setFont(TEXT_FONT);
            leaveButton.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            leaveButton.setBounds(450, 30, 100, 30);
            leaveButton.setOpaque(true);
            leaveButton.setToolTipText("To Leave , Press me");
            leaveButton.addActionListener(handler);
            add(leaveButton);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 30;
        int y = 30;
        int side = 20;
        if (isEditing == true) {
            for (int i = 0; i < 4; i++) {
                if (s1[i] == 0) {
                    drawRectangle(x, y, side, g);
                    x += (side + 10);
                }
            }
            x = 30;
            y = 60;
            side = 40;
            for (int i = 0; i < 3; i++) {
                if (s2[i] == 0) {
                    drawRectangle(x, y, side, g);
                    x += (side + 10);
                }
            }
            x = 30;
            y = 90;
            side = 60;
            for (int i = 0; i < 2; i++) {
                if (s3[i] == 0) {
                    drawRectangle(x, y, side, g);
                    x += (side + 10);
                }
            }
            x = 30;
            y = 120;
            side = 80;
            for (int i = 0; i < 1; i++) {
                if (s4[i] == 0) {
                    drawRectangle(x, y, side, g);
                    x += (side + 10);
                }
            }
        } else {

        }
    }

    private void drawRectangle(int x, int y, int side, Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, side, hei);
        g.setColor(Color.GREEN);
        g.drawRect(x, y, side, hei);
    }

    private void drawSButtons() {
        smallSquareButton = new JButton("4" + "- Select");
        mediumSquareButton = new JButton("3" + "- Select");
        bigSquareButton = new JButton("2" + "- Select");
        largeSquareButton = new JButton("1" + "- Select");
        smallSquareButton.setBounds(200, 30, 100, 25);
        mediumSquareButton.setBounds(200, 60, 100, 25);
        bigSquareButton.setBounds(200, 90, 100, 25);
        largeSquareButton.setBounds(200, 120, 100, 25);
        smallSquareButton.setOpaque(true);
        mediumSquareButton.setOpaque(true);
        bigSquareButton.setOpaque(true);
        largeSquareButton.setOpaque(true);
        smallSquareButton.addActionListener(handler);
        mediumSquareButton.addActionListener(handler);
        bigSquareButton.addActionListener(handler);
        largeSquareButton.addActionListener(handler);
        add(smallSquareButton);
        add(mediumSquareButton);
        add(bigSquareButton);
        add(largeSquareButton);
    }

    private class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == resetButton) {

            } else if (e.getSource() == readyButton) {

            } else if (e.getSource() == rotateButton) {

            } else if (e.getSource() == leaveButton) {

            } else if (e.getSource() == smallSquareButton) {

            } else if (e.getSource() == mediumSquareButton) {

            } else if (e.getSource() == bigSquareButton) {

            } else if (e.getSource() == largeSquareButton) {

            }
        }
    }

    public void setEditing(boolean bool) {
        isEditing = bool;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setS1(int[] s1) {
        this.s1 = s1;
    }

    public int[] getS1() {
        return s1;
    }

    public void setS2(int[] s2) {
        this.s2 = s2;
    }

    public int[] getS2() {
        return s2;
    }

    public void setS3(int[] s3) {
        this.s3 = s3;
    }

    public int[] getS3() {
        return s3;
    }

    public void setS4(int[] s4) {
        this.s4 = s4;
    }

    public int[] getS4() {
        return s4;
    }
}
