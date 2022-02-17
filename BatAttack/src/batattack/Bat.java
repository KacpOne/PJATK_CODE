package batattack;


public class Bat extends Character {

    private Virus virus;

    public Bat(int x, int y) {

        initAlien(x, y);
    }

    private void initAlien(int x, int y) {

        this.x = x;
        this.y = y;

        virus = new Virus(x, y);

        setImage("src/pictures/bat.png");
    }

    public void move(int direction) {

        this.x += direction;
    }

    public Virus getVirus() {

        return virus;
    }
}