import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TetrisGame extends JPanel implements ActionListener {

    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private final int TILE_SIZE = 30;
    private final Timer timer;
    private boolean[][] board;
    private boolean gameOver;
    private Tetromino currentTetromino;
    private int score;

    public TetrisGame() {
        setFocusable(true);
        setPreferredSize(new Dimension(BOARD_WIDTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE));
        board = new boolean[BOARD_HEIGHT][BOARD_WIDTH];
        timer = new Timer(400, this);
        gameOver = false;
        score = 0;
        currentTetromino = new Tetromino();  // Reference to Tetromino
        addKeyListener(new TAdapter());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over", BOARD_WIDTH * TILE_SIZE / 2 - 30, BOARD_HEIGHT * TILE_SIZE / 2);
            return;
        }

        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col]) {
                    g.setColor(Color.BLUE);
                    g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // Draw current tetromino
        currentTetromino.draw(g, TILE_SIZE);

        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 10);
    }

    private boolean canMove(Tetromino tetromino, int dx, int dy) {
        for (Point p : tetromino.coordinates) {
            int newX = tetromino.x + p.x + dx;
            int newY = tetromino.y + p.y + dy;
            if (newX < 0 || newX >= BOARD_WIDTH || newY >= BOARD_HEIGHT || board[newY][newX]) {
                return false;
            }
        }
        return true;
    }

    private void fixTetromino() {
        for (Point p : currentTetromino.coordinates) {
            board[currentTetromino.y + p.y][currentTetromino.x + p.x] = true;
        }
        removeFullRows();
        if (!checkGameOver()) {
            currentTetromino = new Tetromino();
        }
    }

    private void removeFullRows() {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            boolean fullRow = true;
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (!board[row][col]) {
                    fullRow = false;
                    break;
                }
            }
            if (fullRow) {
                score += 100;
                for (int r = row; r > 0; r--) {
                    System.arraycopy(board[r - 1], 0, board[r], 0, BOARD_WIDTH);
                }
                for (int col = 0; col < BOARD_WIDTH; col++) {
                    board[0][col] = false;
                }
            }
        }
    }

    private boolean checkGameOver() {
        for (int col = 0; col < BOARD_WIDTH; col++) {
            if (board[0][col]) {
                gameOver = true;
                timer.stop();
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canMove(currentTetromino, 0, 1)) {
            currentTetromino.y++;
        } else {
            fixTetromino();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                if (canMove(currentTetromino, -1, 0)) {
                    currentTetromino.x--;
                }
            } else if (key == KeyEvent.VK_RIGHT) {
                if (canMove(currentTetromino, 1, 0)) {
                    currentTetromino.x++;
                }
            } else if (key == KeyEvent.VK_UP) {
                currentTetromino.rotate();
                if (!canMove(currentTetromino, 0, 0)) {
                    currentTetromino.rotateBack();
                }
            } else if (key == KeyEvent.VK_DOWN) {
                if (canMove(currentTetromino, 0, 1)) {
                    currentTetromino.y++;
                }
            }
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        TetrisGame game = new TetrisGame();
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
