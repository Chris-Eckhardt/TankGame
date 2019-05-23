package com.chriseckhardt.tankgame.state;

import com.chriseckhardt.tankgame.Game;
import com.chriseckhardt.tankgame.gameobjects.*;
import com.chriseckhardt.tankgame.gameobjects.blocks.Block;
import com.chriseckhardt.tankgame.gameobjects.powerups.PowerUp;
import com.chriseckhardt.tankgame.gameobjects.weapons.Weapon;

import java.awt.*;
import java.util.ArrayList;

import static com.chriseckhardt.tankgame.Game.WORLD_HEIGHT;
import static com.chriseckhardt.tankgame.Game.WORLD_WIDTH;

public class GameHandler {

    ///////// ALL FOR-LOOPS MUST BE INDEXED , NOT FOR-EACH ///////

    private Game game;

    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<Tank> tanks = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    public GameHandler(Game game) {
        this.game = game;
    }

    public void update() {

        if(playerLoss() != null) {
            playerLoss().loseLife();
        }

        collision();

        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).update();
        }

        for (int i = 0; i < weapons.size(); i++) {
            weapons.get(i).update();
        }

        for (int i = 0; i < powerUps.size(); i++) {
            powerUps.get(i).update();
        }

        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).update();
        }
    }

    public void drawImage(Graphics g) {

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);

        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            block.drawImage(g);
        }

        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.drawImage(g);
        }

        for (int i = 0; i < weapons.size(); i++) {
            Weapon weapon = weapons.get(i);
            weapon.drawImage(g);
        }

        for (int i = 0; i < tanks.size(); i++) {
            Tank tank = tanks.get(i);
            tank.drawImage(g);
        }

    }

    private void collision() {

        // #1 // TANK COLLISION
        for(int i = 0; i <tanks.size(); i++) {
            Tank tank = tanks.get(i);

            TYPE enemyProjectileType;
            TYPE enemyMineType;
            if(tank.getType() == TYPE.TANK_1) {
                enemyProjectileType = TYPE.PROJECTILE_2;
                enemyMineType = TYPE.MINE_2;
            } else {
                enemyProjectileType = TYPE.PROJECTILE_1;
                enemyMineType = TYPE.MINE_1;
            }

            // BLOCKS
            for (int j = 0; j < blocks.size(); j++) {
                GameObject block = blocks.get(j);
                if (tank.getBounds().intersects(block.getBounds())) {
                    if (tank.getY() <= block.getY() - (32/2f)) {
                        //bottom of block
                        tank.wallCollisionVert();
                    }
                    if (tank.getY() >= block.getY() + (32/2f)) {
                        //top of the block
                        tank.wallCollisionVert();
                    }
                    if (tank.getX() < block.getX()) {
                        //left of block
                        tank.wallCollisionHoriz();
                    }
                    if (tank.getX() > block.getX()) {
                    //right of block
                        tank.wallCollisionHoriz();
                    }
                }
            }

            //WEAPONS
            for (int j = 0; j < weapons.size(); j++) {
                Weapon weapon = weapons.get(j);
                if (tank.getBounds().intersects(weapon.getBounds())) {

                    if (weapon.getType() == enemyProjectileType) {
                        tank.damage(weapon.getDamage());
                        weapon.explode();
                    }

                    if (weapon.getType() == enemyMineType) {
                        if (tank.getBounds().intersects(weapon.getBounds())) {
                            weapon.explode();
                            tank.damage(weapon.getDamage());
                        }
                    }
                }
            }

            // POWERUPS
            for(int j = 0; j < powerUps.size(); j++) {
                PowerUp powerUp = powerUps.get(j);
                if(tank.getBounds().intersects(powerUp.getBounds())) {
                    tank.powerUp(powerUp);
                    powerUps.remove(powerUp);
                }
            }

            // OTHER TANKS
            for (int j = 0; j < tanks.size(); j++) {
                Tank tank2 = tanks.get(j);
                if (tank.getBounds().intersects(tank2.getBounds()) && tank.getType() != tank2.getType()) {
                    if (tank.getY() <= tank2.getY() - (32/2f)) {
                        //bottom of block
                        tank.wallCollisionVert();
                    }
                    if (tank.getY() >= tank2.getY() + (32/2f)) {
                        //top of the block
                        tank.wallCollisionVert();
                    }
                    if (tank.getX() < tank2.getX()) {
                        //left of block
                        tank.wallCollisionHoriz();
                    }
                    if (tank.getX() > tank2.getX()) {
                        //right of block
                        tank.wallCollisionHoriz();
                    }
                }
            }
        }

        // #2 // WEAPON COLLISION
        for(int i = 0; i <weapons.size(); i++) {
            Weapon weapon = weapons.get(i);

            // BLOCKS
            for (int j = 0; j <blocks.size(); j++) {
                Block block = blocks.get(j);
                if (weapon.getType() == TYPE.PROJECTILE_1 || weapon.getType() == TYPE.PROJECTILE_2) {
                    if (weapon.getBounds().intersects(block.getBounds())) {
                        if (block.getType() == TYPE.INDESTRUCTIBLE_BLOCK) {
                            weapon.explode();
                        }
                        if (block.getType() == TYPE.DESTRUCTIBLE_BLOCK) {
                            block.damage(weapon.getDamage());
                            weapon.explode();
                        }
                    }
                }
            }

            // OTHER WEAPONS
            for (int j = 0; j < weapons.size(); j++) {
                Weapon weapon2 = weapons.get(j);
                if (weapon2.getType() != weapon.getType()) {
                    if (weapon.getBounds().intersects(weapon2.getBounds())) {
                        weapon2.explode();
                        weapon.explode();
                    }
                }
            }
        }
    }

    // ADD / REMOVE OBJECTS

    public void addTank(Tank tank) {
        tanks.add(tank);
    }

    public void removeTank(Tank tank) {
        tanks.remove(tank);
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public void removeWeapon(Weapon weapon) {
        weapons.remove(weapon);
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void removeBlock(Block block) {
        blocks.remove(block);
    }

    public void addPowerUp(PowerUp powerUp) {
        powerUps.add(powerUp);
    }

    public void removePowerUp(PowerUp powerUp) {
        powerUps.remove(powerUp);
    }

    private Tank playerLoss() {
        Tank loser = null;
        for (int i = 0; i < tanks.size(); i++) {
            Tank tank = tanks.get(i);
            if (tank.isDead()) {
                loser = tank;
            }
        }
        return loser;
    }

    public void gameOver(Tank loser) {
        if(loser.getType() == TYPE.TANK_1) {
            // tank 2 is winner
            game.setWinner(2);
            game.setGameState(STATE.GAME_OVER);
        } else {
            //tank 1 is winner
            game.setWinner(1);
            game.setGameState(STATE.GAME_OVER);
        }
    }

}
