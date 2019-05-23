package com.chriseckhardt.tankgame.state;


import com.chriseckhardt.tankgame.Game;
import com.chriseckhardt.tankgame.input.MouseInput;

import java.awt.*;

import static com.chriseckhardt.tankgame.Game.FRAME_HEIGHT;
import static com.chriseckhardt.tankgame.Game.FRAME_WIDTH;

public class GameOver {

    private MouseInput mouse;
    private Game game;
    private String winnerString;
    private Rectangle okButton = new Rectangle(FRAME_WIDTH/2-60, FRAME_HEIGHT/2-25,120, 50);

    public GameOver(Game game, MouseInput mouse) {
        this.game = game;
        this.mouse = mouse;
    }

    public void update() {
        int mouseX = mouse.getX();
        int mouseY = mouse.getY();

        if(okButton.contains(mouseX, mouseY)) {
            game.setGameState(STATE.MENU);
        }
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Font font1 = new Font("arial", Font.BOLD, 75);
        Font font2 = new Font("arial", Font.BOLD, 30);

        g.setFont(font1);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        g.setColor(Color.white);
        g.drawString(winnerString + " WINS!", FRAME_WIDTH/2-260, 100);

        g.setFont(font2);
        g.drawString("OK", okButton.x + 10, okButton.y + 35);

        g2d.draw(okButton);
    }

    public void setWinner(int winner) {
        if(winner == 1) {
            this.winnerString = "PLAYER 1";
        }
        if(winner == 2) {
            this.winnerString = "PLAYER 2";
        }
    }
}
