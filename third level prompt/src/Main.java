import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris Game");
        GameUI gameUI = new GameUI();

        frame.add(gameUI);
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameUI.isGameOver() && e.getKeyCode() == KeyEvent.VK_R) {
                    gameUI.restartGame();
                }
            }
        });
    }
}
