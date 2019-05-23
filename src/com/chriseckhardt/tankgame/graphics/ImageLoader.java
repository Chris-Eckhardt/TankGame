package com.chriseckhardt.tankgame.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {

    private BufferedImage image;

    public ImageLoader() {}

    public BufferedImage loadImage(String path) {
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage[] loadTank1Images() {
        BufferedImage[] images = new BufferedImage[7];
        images[0] = this.loadImage("/tank/tank_blue.png");
        images[1] = this.loadImage("/projectile/bulletBlue1.png");
        images[2] = this.loadImage("/mine/mine1.png");
        images[3] = this.loadImage("/mine/mine2.png");
        images[4] = this.loadImage("/mine/mine3.png");
        images[5] = this.loadImage("/mine/mine4.png");
        images[6] = this.loadImage("/mine/mine5.png");
        return images;
    }

    public BufferedImage[] loadTank2Images() {
        BufferedImage[] images = new BufferedImage[7];
        images[0] = this.loadImage("/tank/tank_red.png");
        images[1] = this.loadImage("/projectile/bulletBlue1.png");
        images[2] = this.loadImage("/mine/mine1.png");
        images[3] = this.loadImage("/mine/mine2.png");
        images[4] = this.loadImage("/mine/mine3.png");
        images[5] = this.loadImage("/mine/mine4.png");
        images[6] = this.loadImage("/mine/mine5.png");
        return images;
    }

    public BufferedImage[] loadDestructibleBlockImages() {
        BufferedImage[] images  = new BufferedImage[2];
        images[0] = this.loadImage("/block/destructible_block_01.png");
        images[1] = this.loadImage("/block/destructible_block_02.png");
        return images;
    }
}
