public abstract class Block {
    protected int[][] shape;
    protected int x, y; // Current position

    public Block(int[][] shape) {
        this.shape = shape;
        this.x = 5;
        this.y = 0;
    }

    public abstract void rotate();

    public int[][] getShape() {
        return shape;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
