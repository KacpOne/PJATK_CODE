package batattack;

public class Virus extends Character {

    private boolean alive;

    public Virus(int x, int y) {

        setupVirus(x, y);
    }

    private void setupVirus(int x, int y) {

        setAlive(false);

        this.x = x;
        this.y = y;

        setImage("src/pictures/virus.png");
    }

    public void setAlive(boolean alive) {

        this.alive = alive;
    }

    public boolean isAlive() {

        return alive;
    }
}