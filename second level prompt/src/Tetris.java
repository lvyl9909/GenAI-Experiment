import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Tetris extends JPanel implements ActionListener {
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    private static final int TILE_SIZE = 30;
    private Timer timer;
    private boolean isFallingFinished = false;
    private boolean isStarted = false;
    private boolean isPaused = false;
    private int score = 0;
    private int curX = 0;
    private int curY = 0;
    private Shape curPiece;
    private Shape.Tetrominoes[] board;

    public Tetris() {
        setFocusable(true);
        requestFocusInWindow(); // Request focus for key events
        curPiece = new Shape();
        timer = new Timer(400, this);
        timer.start();
        board = new Shape.Tetrominoes[BOARD_WIDTH * BOARD_HEIGHT];
        clearBoard();
        addKeyListener(new TAdapter());
    }


    public void start() {
        isStarted = true;
        isFallingFinished = false;
        score = 0;
        clearBoard();
        newPiece();
        timer.start();
    }

    private void pause() {
        if (!isStarted) return;
        isPaused = !isPaused;
        if (isPaused) {
            timer.stop();
        } else {
            timer.start();
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Timer ticked");  // Add this line
        if (isFallingFinished) {
            isFallingFinished = false;
            newPiece();
        } else {
            moveDown();
        }
    }

    private void moveDown() {
        System.out.println("Attempting to move down");
        if (!tryMove(curPiece, curX, curY + 1)) {
            System.out.println("Piece dropped at (" + curX + ", " + curY + ")");
            pieceDropped();
        }
    }



    private void pieceDropped() {
        for (int i = 0; i < 4; i++) {
            int x = curX + curPiece.x(i);
            int y = curY + curPiece.y(i);
            board[(y * BOARD_WIDTH) + x] = curPiece.getShape();
        }
        System.out.println("Piece added to board at (" + curX + ", " + curY + ")");
        removeFullLines();  // Check and clear full lines
        if (!isFallingFinished) {
            newPiece();  // Start new piece
        }
    }



    private void removeFullLines() {
        int numFullLines = 0;

        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean lineIsFull = true;
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (shapeAt(j, i) == Shape.Tetrominoes.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                numFullLines++;

                for (int k = i; k < BOARD_HEIGHT - 1; k++) {
                    for (int j = 0; j < BOARD_WIDTH; j++) {
                        board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1);
                    }
                }
            }
        }

        if (numFullLines > 0) {
            score += numFullLines * 100;
            isFallingFinished = true;
            curPiece.setShape(Shape.Tetrominoes.NoShape);
            repaint();
        }
    }

    private void newPiece() {
        curPiece.setRandomShape();
        curX = BOARD_WIDTH / 2 - 1; // Start in the middle of the board horizontally
        curY = -1; // Adjust curY to ensure it's within bounds (but starts at the top)

        // Try to move the new piece into its starting position. If it fails, the game is over.
        if (!tryMove(curPiece, curX, curY)) {
            curPiece.setShape(Shape.Tetrominoes.NoShape);
            timer.stop();
            isStarted = false;
            System.out.println("Game over");
        } else {
            System.out.println("New piece created: " + curPiece.getShape());
        }
    }




    private boolean tryMove(Shape newPiece, int newX, int newY) {
        for (int i = 0; i < 4; i++) {
            int x = newX + newPiece.x(i);
            int y = newY + newPiece.y(i);

            // Allow pieces to spawn just above the board, but they must move down into the visible area
            if (x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT) {
                System.out.println("Invalid move: Out of bounds at (" + x + ", " + y + ")");
                return false;
            }

            // If the piece is still above the visible board, don't check for collisions yet
            if (y < 0) {
                continue;
            }

            if (shapeAt(x, y) != Shape.Tetrominoes.NoShape) {
                System.out.println("Invalid move: Collision at (" + x + ", " + y + ")");
                return false;
            }
        }

        // Valid move, update piece position and repaint
        curPiece = newPiece;
        curX = newX;
        curY = newY;
        repaint();
        System.out.println("Moved piece to (" + curX + ", " + curY + ")");
        return true;
    }





    private Shape.Tetrominoes shapeAt(int x, int y) {
        return board[(y * BOARD_WIDTH) + x];
    }

    private void clearBoard() {
        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {
            board[i] = Shape.Tetrominoes.NoShape;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                Shape.Tetrominoes shape = shapeAt(j, i);
                if (shape != Shape.Tetrominoes.NoShape) {
                    drawSquare(g, j * TILE_SIZE, i * TILE_SIZE, shape);
                }
            }
        }

        if (curPiece.getShape() != Shape.Tetrominoes.NoShape) {
            for (int i = 0; i < 4; i++) {
                int x = curX + curPiece.x(i);
                int y = curY + curPiece.y(i);
                drawSquare(g, x * TILE_SIZE, y * TILE_SIZE, curPiece.getShape());
            }
        }
    }

    private void drawSquare(Graphics g, int x, int y, Shape.Tetrominoes shape) {
        Color colors[] = {new Color(0, 0, 0), new Color(255, 0, 0), new Color(0, 255, 0),
                new Color(0, 0, 255), new Color(255, 255, 0), new Color(255, 165, 0),
                new Color(128, 0, 128), new Color(0, 255, 255)};

        g.setColor(colors[shape.ordinal()]);
        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
        g.setColor(colors[0]);
        g.drawRect(x, y, TILE_SIZE, TILE_SIZE);
    }

    class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (!isStarted || curPiece.getShape() == Shape.Tetrominoes.NoShape) {
                return;
            }

            int keycode = e.getKeyCode();
            System.out.println("Key Pressed: " + keycode);  // Add this to verify key press

            if (keycode == 'p' || keycode == 'P') {
                pause();
                return;
            }

            if (isPaused)
                return;

            switch (keycode) {
                case KeyEvent.VK_LEFT:
                    System.out.println("Attempting to move left"); // Debug log
                    tryMove(curPiece, curX - 1, curY);
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.println("Attempting to move right"); // Debug log
                    tryMove(curPiece, curX + 1, curY);
                    break;
                case KeyEvent.VK_DOWN:
                    System.out.println("Attempting to move down"); // Debug log
                    moveDown();
                    break;
                case KeyEvent.VK_UP:
                    System.out.println("Attempting to rotate"); // Debug log
                    tryMove(curPiece.rotateRight(), curX, curY);
                    break;
            }
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        Tetris game = new Tetris();
        frame.add(game);
        frame.setSize(BOARD_WIDTH * TILE_SIZE + 20, BOARD_HEIGHT * TILE_SIZE + 40);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }
}
