package batattack;


public class Weapon extends Character {

    public Weapon(int x, int y) {

        setupShot(x, y);
    }

    private void setupShot(int x, int y) {

        setImage("src/pictures/syringe.png");

        setX(x);
        setY(y);
    }
}