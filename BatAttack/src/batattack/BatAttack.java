package batattack;

import javax.swing.JFrame;
import java.awt.*;
import java.io.IOException;

public class BatAttack extends JFrame implements GameplayDetails {

    public BatAttack() throws IOException {
        setupGame();
    }

    private void setupGame() throws IOException {

        GameMap gameMap = new GameMap();
        add(gameMap);

        setTitle("Bat Attack");
        setSize(gameMapW, gameMapH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            BatAttack batAttack = null;
            try {
                batAttack = new BatAttack();
            } catch (IOException e) {
                e.printStackTrace();
            }
            batAttack.setVisible(true);
        });
    }
}