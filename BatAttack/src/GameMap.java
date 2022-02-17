package batattack;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.*;

public class GameMap extends JPanel implements GameplayDetails {

    private ArrayList<Virus> viruses;
    private ArrayList<Bat> bats;
    private World world;
    private Weapon weapon;
    private Type type = Type.Starter;

    private int rotation = 1;
    private int direction = 1;
    private int deadBats = 0;
    private double bestTime;

    private String explosionString = "src/pictures/explosion.png";
    private ImageIcon explosionII = new ImageIcon(explosionString);
    private Image explosionImage = explosionII.getImage();

    private boolean inGame = true;
    private boolean victory = false;

    private String msg = "Defeat - Humanity has been exterminated by the new Bat overlords :(";
    private String file = "BestTime.txt";

    private Timer timer;
    private TimeCounter timeCounter = new TimeCounter();
    private FileManager fileManager = new FileManager();

    public GameMap() throws IOException {
        setupMAP();
    }

    private void setupMAP() throws IOException {

        addKeyListener(new KeyBoard());
        setFocusable(true);
        setBackground(Color.black);

        timer = new Timer(gameDelay, new Cycle());
        timer.start();

        bestTime = fileManager.scanFile("BestTime.txt");


        setupGAME();
    }


    private void setupGAME() {

        bats = new ArrayList<>();
        viruses = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {

                Bat bat = new Bat(bat_SX + 100 * j,
                        bat_YS + 70 * i);
                bats.add(bat);
            }
        }

        world = new World();
        weapon = new Weapon(world.getX(), world.getY());
    }

    private void paintBATS(Graphics g) {

        for (Bat bat : bats) {

            if (bat.isVisible()) {

                g.drawImage(bat.getImage(), bat.getX() + batW / 2, bat.getY(), this);
            }

            if (bat.isDead()) {
                removeBat(bat);
            }
        }
    }

    private void paintWORLD(Graphics g) {

        if (world.isVisible()) {

            g.drawImage(world.getImage(), world.getX(), world.getY(), this);
        }

        if (world.isDead()) {

            world.die();
            inGame = false;
        }
    }

    private void paintWEAPON(Graphics g) {

        if (weapon.isVisible()) {

            g.drawImage(weapon.getImage(), weapon.getX(), weapon.getY(), this);
        }
    }

    private void paintVIRUS(Graphics g) {

        for (Bat a : bats) {

            Virus b = a.getVirus();

            if (b.isAlive()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (type == Type.Game) {
            g.setColor(Color.white);
            g.fillRect(0, 0, gameMapW, gameMapH);

            Font font = new Font("Helvetica", Font.BOLD, 14);
            g.setColor(Color.black);
            g.setFont(font);
            try {
                paintALL(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type == Type.Starter) {

            ImageIcon batii = new ImageIcon("src/pictures/bat.png");
            Image bat = batii.getImage();
            ImageIcon virusii = new ImageIcon("src/pictures/virus.png");
            Image virus = virusii.getImage();

            Font font = new Font("Font", Font.BOLD, 100);
            g.setFont(font);
            g.setColor(Color.white);
            g.drawString("Bat Attack", gameMapW / 3 + 45, 200);

            Font font1 = new Font("Font", Font.ITALIC, 36);
            g.setFont(font1);
            g.drawString("Click Space to begin", gameMapW / 3 + 45, 800);

            AffineTransform batAT = AffineTransform.getTranslateInstance(200, gameMapH / 2);
            batAT.scale(3, 3);
            batAT.rotate(Math.toRadians(rotation), bat.getWidth(null) / 2, bat.getHeight(null) / 2);

            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.drawImage(bat, batAT, null);

            AffineTransform virusAT = AffineTransform.getTranslateInstance(gameMapW - 400, gameMapH / 2);
            virusAT.scale(3, 3);
            virusAT.rotate(Math.toRadians(rotation), virus.getWidth(null) / 2, virus.getHeight(null) / 2);

            graphics2D.drawImage(virus, virusAT, null);


            rotation++;

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            repaint();
        }
    }

    private void paintALL(Graphics g) throws IOException {

        String backIMG = "src/pictures/Wuhan.png";
        ImageIcon background = new ImageIcon(backIMG);
        Image backgroundIMG = background.getImage();

        g.drawImage(backgroundIMG, 0, 0, null);

        if (inGame) {
            Font font = new Font("Font", Font.ITALIC, 36);

            g.setColor(Color.white);
            g.setFont(font);
            g.drawString("Lifes left: " + world.getLives(), 100,
                    gameMapH - 50);

            g.setColor(Color.white);
            g.setFont(font);
            g.drawString("Time: " + timeCounter.getTime(), gameMapW - 300,
                    gameMapH - 50);

            g.drawLine(0, down,
                    gameMapW, down);
            try {
                paintBATS(g);
                paintWORLD(g);
                paintWEAPON(g);
                paintVIRUS(g);
            }catch (Exception ignored){}

            for (Bat bat : bats) {

                int batX = bat.getX();
                int batY = bat.getY();
                int weaponX = weapon.getX();
                int weaponY = weapon.getY();

                if (bat.isVisible() && weapon.isVisible()) {
                    if (weaponX >= (batX)
                            && weaponX <= (batX + batW)
                            && weaponY >= (batY)
                            && weaponY <= (batY + batH)) {
                        g.drawImage(explosionImage, batX + batW / 2, batY, null);
                    }
                }
            }
            for (Virus virus : viruses) {

                int virusX = virus.getX();
                int virusY = virus.getY();
                int worldX = world.getX();
                int worldY = world.getY();

                if (virus.isVisible()) {
                    if (virusX >= (worldX - 1)
                            && virusX <= (worldX + worldW)
                            && virusY >= (worldY)
                            && virusY <= (worldY + worldH)) {
                        g.drawImage(explosionImage, worldX, worldY, null);
                    }
                }
            }
        } else {

            if (timer.isRunning()) {
                timer.stop();
                timeCounter.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) throws IOException {

        g.setColor(Color.black);
        g.fillRect(0, 0, gameMapW, gameMapH);


        g.setColor(new Color(123, 9, 34));
        g.fillRect(50, gameMapH / 8, gameMapW - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, gameMapH / 8, gameMapW - 100, 50);


        Font font = new Font("Font", Font.ITALIC, 36);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg, gameMapW / 2 - 300, gameMapH / 8 + 36);

        if (victory) {

            g.setColor(new Color(123, 9, 34));
            g.fillRect(50, gameMapH / 2, gameMapW - 100, 50);
            g.setColor(Color.white);
            g.drawRect(50, gameMapH / 2, gameMapW - 100, 50);

            g.setColor(Color.white);
            g.setFont(font);
            g.drawString("Your Time: " + timeCounter.getElapsedTimeSecs() + " seconds", gameMapW / 2 - 300, gameMapH / 2 + 36);

            g.setColor(new Color(123, 9, 34));
            g.fillRect(50, gameMapH - 200, gameMapW - 100, 50);
            g.setColor(Color.white);
            g.drawRect(50, gameMapH - 200, gameMapW - 100, 50);

            g.setColor(Color.white);
            g.setFont(font);
            g.drawString("Your Best Time: " + fileManager.scanFile(file) + " seconds", gameMapW / 2 - 300,
                    gameMapH - 164);
        }
    }


    private void update() throws IOException {
        if (type == Type.Game) {
            if (timeCounter.getElapsedTimeSecs() == 0) {
                timeCounter.start();
            }
            if (deadBats == allBats) {
                victory = true;
                inGame = false;
                timeCounter.stop();

                if (bestTime > timeCounter.getElapsedTimeSecs()) {
                    fileManager.printFile(file, timeCounter.getElapsedTimeSecs());
                }
                fileManager.closeFile();
                msg = "Victory!!! - you saved the world from being ruled by Bats";
                timer.stop();
            }

            world.move();

            this.hits();

            int i = 0;
            while (i < bats.size()) {
                Bat bat = bats.get(i);

                if (bat.isVisible()) {

                    int y = bat.getY();

                    if (y>down - 100) {
                        inGame = false;
                        msg = "Defeat - Humanity has been exterminated by the new Bat overlords :( :(";
                    }

                    bat.move(direction);
                    i++;
                }
            }

            Random generator = new Random();

            for (Bat bat : bats) {

                int shoot = generator.nextInt(200);
                Virus virus = bat.getVirus();

                if (shoot == random && bat.isVisible() && !virus.isAlive()) {


                    virus.setAlive(true);
                    virus.setX(bat.getX());
                    virus.setY(bat.getY());
                    viruses.add(virus);
                }

                int virusX = virus.getX();
                int virusY = virus.getY();
                int worldX = world.getX();
                int worldY = world.getY();

                if (world.isVisible() && virus.isAlive()) {

                    if (virusX >= (worldX)
                            && virusX <= (worldX + worldW)
                            && virusY >= (worldY)
                            && virusY <= (worldY + worldH)) {

                        if (world.getLives() > 0) {
                            world.setLives(1);
                        } else {
                            world.setDead(true);
                        }
                        removeVirus(virus);
                    }
                }

                if (virus.isAlive()) {

                    virus.setY(virus.getY() + 1);

                    if (virus.getY() >= down - virusH) {

                        removeVirus(virus);
                    }
                }
            }
        }
    }
    private void hits(){
        if (weapon.isVisible()) {

            int weaponX = weapon.getX();
            int weaponY = weapon.getY();

            for (Bat bat : bats) {

                int batX = bat.getX();
                int batY = bat.getY();

                if (bat.isVisible() && weapon.isVisible()) {
                    if (weaponX >= (batX)
                            && weaponX <= (batX + batW)
                            && weaponY >= (batY)
                            && weaponY <= (batY + batH)) {
                        bat.setDead(true);
                        deadBats++;
                        weapon.die();
                    }
                }
            }


            int y = weapon.getY();
            y -= 5;

            if (y < 0) {
                weapon.die();
            } else {
                weapon.setY(y);
            }
        }

        for (Bat bat : bats) {

            int x = bat.getX();

            if (x >= gameMapW - right && direction != -1) {

                direction = -1;

                int i = 0;
                while (i < bats.size()) {

                    Bat a2 = bats.get(i);
                    a2.setY(a2.getY() + moveDown);
                    i++;
                }
            }

            if (x <= left && direction != 1) {

                direction = 1;

                int i = 0;
                while (i < bats.size()) {

                    Bat a = bats.get(i);
                    a.setY(a.getY() + moveDown);
                    i++;
                }
            }
        }
    }

    private void removeBat(Bat bat) {
        this.bats.remove(bat);
        removeVirus(bat.getVirus());
        bat.die();
    }

    private void removeVirus(Virus virus) {
        this.viruses.remove(virus);
        virus.setAlive(false);
    }

    private void doCycle() throws IOException {

        update();
        repaint();
    }

    private class Cycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                doCycle();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class KeyBoard extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            world.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {

                if (type == Type.Starter) {
                    type = Type.Game;
                }


                if (inGame) {
                    if (!weapon.isVisible()) {
                        weapon = new Weapon(world.getX(), world.getY());
                    }
                }
            }
            world.keyPressed(e);
        }
    }
}