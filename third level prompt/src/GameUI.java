import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameUI extends JPanel implements ActionListener {
    private Game game;
    private Timer timer;

    public GameUI() {
        game = new Game();
        timer = new Timer(500, this); // Game updates every 500ms
        timer.start();

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!game.isGameOver()) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            game.moveBlockLeft();
                            break;
                        case KeyEvent.VK_RIGHT:
                            game.moveBlockRight();
                            break;
                        case KeyEvent.VK_DOWN:
                            game.moveBlockDown();
                            break;
                        case KeyEvent.VK_UP:
                            game.rotateBlock();
                            break;
                    }
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (game.isGameOver()) {
            drawGameOverScreen(g);
        } else {
            drawBoard(g);
        }
    }

    private void drawBoard(Graphics g) {
        int[][] grid = game.getBoard().getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(j * 30, i * 30, 30, 30);
                }
            }
        }

        // Draw current block
        Block currentBlock = game.getCurrentBlock();
        int[][] shape = currentBlock.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect((currentBlock.getX() + j) * 30, (currentBlock.getY() + i) * 30, 30, 30);
                }
            }
        }
    }

    private void drawGameOverScreen(Graphics g) {
        g.setColor(Color.RED);
        g.drawString("Game Over!", 150, 200);
        g.drawString("Press R to Restart", 130, 240);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.update();
        repaint();
    }

    public void restartGame() {
        game.restart();
    }

    public boolean isGameOver() {
        return game.isGameOver();
    }
}
