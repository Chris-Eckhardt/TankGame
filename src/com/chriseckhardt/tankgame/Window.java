package com.chriseckhardt.tankgame;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public static final long serialVersionUID = 1L;

    private static final String TITLE = "TANK GAME";

    public Window() {
        setTitle(TITLE);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIgnoreRepaint(true);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addNotify() {
        super.addNotify();

        setLayout(new BorderLayout());
        add(new Game());
    }

}



