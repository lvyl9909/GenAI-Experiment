import java.util.Random;

public class Game {
    private Board board;
    private Block currentBlock;
    private boolean isGameOver;
    private int score;
    private Random random;

    public Game() {
        board = new Board();
        score = 0;
        isGameOver = false;
        random = new Random();
        spawnBlock();
    }

    public void update() {
        if (!isGameOver) {
            moveBlockDown();
            checkGameOver();
        }
    }

    private void checkGameOver() {
        // Check if the blocks have stacked up to the top
        if (/* condition for game over */ false) {
            isGameOver = true;
        }
    }

    public void restart() {
        board = new Board();
        score = 0;
        isGameOver = false;
        spawnBlock();
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Board getBoard() {
        return board;
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public void moveBlockDown() {
        if (board.canMove(currentBlock.getX(), currentBlock.getY() + 1, currentBlock.getShape())) {
            currentBlock.moveDown();
        } else {
            board.lockBlock(currentBlock);
            spawnBlock();
        }
    }

    public void moveBlockLeft() {
        if (board.canMove(currentBlock.getX() - 1, currentBlock.getY(), currentBlock.getShape())) {
            currentBlock.moveLeft();
        }
    }

    public void moveBlockRight() {
        if (board.canMove(currentBlock.getX() + 1, currentBlock.getY(), currentBlock.getShape())) {
            currentBlock.moveRight();
        }
    }

    public void rotateBlock() {
        currentBlock.rotate();
        if (!board.canMove(currentBlock.getX(), currentBlock.getY(), currentBlock.getShape())) {
            currentBlock.rotate(); // Rotate back if it's an invalid move
        }
    }

    private void spawnBlock() {
        int blockType = random.nextInt(4); // Randomly select between 4 types
        switch (blockType) {
            case 0:
                currentBlock = new SquareBlock();
                break;
            case 1:
                currentBlock = new LineBlock();
                break;
            case 2:
                currentBlock = new TBlock();
                break;
            case 3:
                currentBlock = new LBlock();
                break;
        }
    }
}
