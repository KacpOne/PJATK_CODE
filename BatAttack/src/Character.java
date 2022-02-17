package batattack;

import javax.swing.*;
import java.awt.Image;

public class Character implements GameplayDetails {

    private boolean visible;
    private Image image;
    private boolean dead;

    int x;
    int y;
    int speed;

    public Character() {

        visible = true;
    }

    public void die() {

        visible = false;
    }

    public boolean isVisible() {

        return visible;
    }

    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    public int getY() {

        return y;
    }

    public int getX() {

        return x;
    }

    public void setDead(boolean dead) {

        this.dead = dead;
    }

    public boolean isDead() {

        return this.dead;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(String image) {
        ImageIcon imageIcon = new ImageIcon(image);
        this.image = imageIcon.getImage();
    }
}