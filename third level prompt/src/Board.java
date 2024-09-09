public class Board {
    private final int WIDTH = 10;
    private final int HEIGHT = 20;
    private int[][] grid;

    public Board() {
        grid = new int[HEIGHT][WIDTH];
    }

    public boolean canMove(int newX, int newY, int[][] shape) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int boardX = newX + j;
                    int boardY = newY + i;

                    if (boardX < 0 || boardX >= WIDTH || boardY >= HEIGHT || (boardY >= 0 && grid[boardY][boardX] != 0)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void lockBlock(Block block) {
        int[][] shape = block.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    grid[block.getY() + i][block.getX() + j] = shape[i][j];
                }
            }
        }
    }

    public int[][] getGrid() {
        return grid;
    }
}
