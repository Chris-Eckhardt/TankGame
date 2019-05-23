package com.chriseckhardt.tankgame;

import com.chriseckhardt.tankgame.gameobjects.*;
import com.chriseckhardt.tankgame.gameobjects.blocks.DestructibleBlock;
import com.chriseckhardt.tankgame.gameobjects.blocks.IndestructibleBlock;
import com.chriseckhardt.tankgame.gameobjects.powerups.AmmoCrate;
import com.chriseckhardt.tankgame.gameobjects.powerups.HealthCrate;
import com.chriseckhardt.tankgame.gameobjects.powerups.MineCrate;
import com.chriseckhardt.tankgame.input.KeyInput;
import com.chriseckhardt.tankgame.input.MouseInput;
import com.chriseckhardt.tankgame.state.GameOver;
import com.chriseckhardt.tankgame.state.GameHandler;
import com.chriseckhardt.tankgame.state.Menu;
import com.chriseckhardt.tankgame.state.STATE;
import com.chriseckhardt.tankgame.util.Camera;
import com.chriseckhardt.tankgame.util.HUD;
import com.chriseckhardt.tankgame.graphics.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/*
 * TABLE OF CONTENTS :           NOTES :
 * 1: Constructor                   - Game()
 * 2: Build Map                     - creates the map from source that is external to the class
 * 3: Game Loop                     - this loop is not mine, but is a very common game loop
 * 4: Stop                          - stop game thread
 * 5: Update                        - updates all game objects and their attributes
 * 6: PaintComponent                - renders the updated objects to screen
 * 7: Tools                         - this will have various helper methods
 * 7: Main                          - entry point to program
 */

public class Game extends JPanel implements Runnable {

    public static final int WORLD_WIDTH = 1536;
    public static final int WORLD_HEIGHT = 1032;

    public static final int FRAME_WIDTH = 1024;
    public static final int FRAME_HEIGHT = 768;

    private static final int PLAYER_VIEW_WIDTH = FRAME_WIDTH / 2;
    private static final int PLAYER_VIEW_HEIGHT = FRAME_HEIGHT;

    private Thread thread;
    private GameHandler gameHandler;

    private Tank player1;
    private Tank player2;

    private Camera camera1;
    private Camera camera2;
    private HUD hud;
    private com.chriseckhardt.tankgame.state.Menu menu;
    private GameOver gameOver;
    private STATE state = STATE.MENU;
    private MouseInput mouse;

    private BufferedImage ammoCrate;
    private BufferedImage healthCrate;
    private BufferedImage world;
    private BufferedImage mineCrate;
    private BufferedImage indestructibleBlock;
    private BufferedImage[] tankImages1, tankImages2, destructibleBlockImages;

    private Boolean isRunning = false;

    //////////////////////////////////////
    //////////// CONSTRUCTOR /////////////
    //////////////////////////////////////

    Game() {
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setFocusable(true);
        requestFocus();

        mouse = new MouseInput();
        this.addMouseListener(mouse);
    }

    public void addNotify() {
        super.addNotify();

        if (thread == null) {
            thread = new Thread(this, "Loop thread");
            thread.start();
        }
    }

    //////////////////////////////////////
    //////////////// INIT ////////////////
    //////////////////////////////////////

    private void init() {
        isRunning = true;

        world = new BufferedImage(WORLD_WIDTH, WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        menu = new Menu(this, mouse);
        gameOver = new GameOver(this, mouse);
    }

    //////////////////////////////////////
    ////////////// BUILD MAP /////////////
    //////////////////////////////////////

    private void buildMap(BufferedImage image) {

        /////// RGB GUIDE ///////
        /*
        tank1                 (0,255, 0);
        tank2                 (0,0, 255);
        indestructible_block  (255, 0, 0);
        destructible_block    (0, 255, 255);
        ammo_crate            (255, 255, 0);
        health_crate          (255, 0, 255);
        mine_crate            (200,200,200)
        */

        int w = image.getWidth();
        int h = image.getHeight();

        for(int xx = 0; xx < w; xx++) {
            for(int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 0 && green == 255 && blue == 0) {
                    gameHandler.addTank(player1 =new Tank(xx*32, yy*32,
                            4, 4,0, TYPE.TANK_1, gameHandler, tankImages1));
                    camera1 = new Camera(xx*32, yy*32, PLAYER_VIEW_WIDTH, PLAYER_VIEW_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
                }

                if(red == 0 && green == 0 && blue == 255) {
                    gameHandler.addTank(player2 =new Tank(xx*32, yy*32,
                            4, 4,180, TYPE.TANK_2, gameHandler, tankImages2));
                    camera2 = new Camera(xx*32, yy*32, PLAYER_VIEW_WIDTH, PLAYER_VIEW_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
                }

                if(red == 255 && green == 0 && blue == 0) {
                    gameHandler.addBlock(new IndestructibleBlock(xx*32, yy*32, indestructibleBlock));
                }

                if(red == 255 && green == 0 && blue == 255) {
                    gameHandler.addBlock(new DestructibleBlock(xx*32, yy*32, gameHandler, destructibleBlockImages));
                }

                if(red == 255 && green == 255 && blue == 0) {
                    gameHandler.addPowerUp(new AmmoCrate(xx*32, yy*32, ammoCrate) );
                }

                if(red == 0 && green == 255 && blue == 255) {
                    gameHandler.addPowerUp(new HealthCrate(xx*32, yy*32, healthCrate));
                }

                if(red == 200 && green == 200 && blue == 200) {
                    gameHandler.addPowerUp(new MineCrate(xx*32, yy*32, mineCrate));
                }
            }
        }
    }

    //////////////////////////////////////
    //////////// GAME LOOP ///////////////
    //////////////////////////////////////

    public void run() {
        init();

        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while(isRunning) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if(delta >= 1) {
                update();
                updates++;
                delta--;
            }

            repaint();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(("updates: " + updates + " , FPS: "  + frames));
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    //////////////////////////////////////
    /////////////// STOP /////////////////
    //////////////////////////////////////

    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////
    /////////////// UPDATE ///////////////
    //////////////////////////////////////

    private void update() {
        if(state == STATE.MENU) {
            menu.update();
        }

        if(state == STATE.GAME) {
            hud.update();
            camera1.update(player1);
            camera2.update(player2);
            gameHandler.update();            // UPDATES ALL OBJECTS MANAGED BY HANDLER
        }

        if(state == STATE.GAME_OVER) {
            gameOver.update();
        }
    }

    //////////////////////////////////////
    ///////// PAINT COMPONENT ////////////
    //////////////////////////////////////

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        super.paintComponent(g2);


        if(state == STATE.MENU) {
            menu.drawImage(g);
        }

        if(state == STATE.GAME) {

            // DRAW GAME OBJECTS TO BUFFER
            this.gameHandler.drawImage(buffer);

            // DRAW SPLIT_SCREEN TO JPANEL
            BufferedImage left = world.getSubimage((int) camera1.getX(), (int) camera1.getY(), PLAYER_VIEW_WIDTH, PLAYER_VIEW_HEIGHT);
            BufferedImage right = world.getSubimage((int) camera2.getX(), (int) camera2.getY(), PLAYER_VIEW_WIDTH, PLAYER_VIEW_HEIGHT);

            g2.drawImage(left, 0, 0, PLAYER_VIEW_WIDTH, PLAYER_VIEW_HEIGHT, null);

            g2.drawImage(right, FRAME_WIDTH / 2, 0, PLAYER_VIEW_WIDTH, PLAYER_VIEW_HEIGHT, null);
            // DRAW HUD
            hud.drawImage(g2);

            // TRANSLATE CAMERAS
            g2.translate((int) -camera1.getX(), (int) -camera1.getY());
            g2.translate((int) -camera2.getX(), (int) -camera2.getY());
        }

        if(state == STATE.GAME_OVER) {
            gameOver.drawImage(g);
        }

        // DISPOSE GRAPHICS
        g2.dispose();


    }

    ////////////////////////////////////
    ///////////// TOOlS ////////////////
    ////////////////////////////////////

    public BufferedImage getWorld() {
        return world;
    } // this is for HUD minimap

    public void setGameState(STATE state) {
        this.state = state;
    }

    public void startRound(int map) {

        ImageLoader loader = new ImageLoader();
        tankImages1 = loader.loadTank1Images();
        tankImages2 = loader.loadTank2Images();
        destructibleBlockImages = loader.loadDestructibleBlockImages();
        indestructibleBlock = loader.loadImage("/block/indestructible_block.png");
        BufferedImage map1 = loader.loadImage("/map/MAP_1.png");
        BufferedImage map2 = loader.loadImage("/map/MAP_2.png");
        BufferedImage map3 = loader.loadImage("/map/MAP_3.png");
        BufferedImage map4 = loader.loadImage("/map/MAP_4.png");
        ammoCrate = loader.loadImage("/collectable/ammoCrate.png");
        healthCrate = loader.loadImage("/collectable/healthCrate.png");
        mineCrate = loader.loadImage("/collectable/mineCrate.png");

        gameHandler = new GameHandler(this);

        if( map == 2 ) {
            buildMap(map2);
        } else if( map == 3 ) {
            buildMap(map3);
        } else if( map == 4 ){
            buildMap(map4);
        } else {
            buildMap(map1);
        }

        // HUD must be created AFTER buildMap() is called
        hud = new HUD(player1, player2, this);

        // create keyListeners for players 1 & 2
        this.addKeyListener(new KeyInput(player1,
                KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE, KeyEvent.VK_E) );

        this.addKeyListener( new KeyInput(player2,
                KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER, KeyEvent.VK_SLASH));

        this.setGameState(STATE.GAME);
    }

    public void setWinner(int winner) {
        gameOver.setWinner(winner);
    }

    //////////////////////////////////////
    /////////////// MAIN /////////////////
    //////////////////////////////////////

    public static void main(String[] args) {
         new Window();
    }
}
