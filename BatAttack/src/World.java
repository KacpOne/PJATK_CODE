package batattack;

import java.awt.event.KeyEvent;

public class World extends Character {

    private int lives = 2;

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives -= lives;
    }

    public World() {

        setupWorld();
    }

    private void setupWorld() {

        setImage("src/pictures/earth.png");
        int SX = 960;
        setX(SX);

        int SY = 860;
        setY(SY);
    }

    public void move() {

        x += speed;

        if (x <= 2) {

            x = 2;
        }

        if (x >= gameMapW - 2 * worldW) {

            x = gameMapW - 2 * worldW;
        }
    }

    public void keyPressed(KeyEvent keyEvent) {

        int key = keyEvent.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
                speed = -8;
                break;
            case KeyEvent.VK_RIGHT:
                speed = 8;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                speed = 0;
                break;
        }
    }
}