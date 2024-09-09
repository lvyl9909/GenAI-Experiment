import java.awt.Point;
import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;

public class Tetromino {
    public enum Shape { I, J, L, O, S, T, Z }

    public Shape shape;
    public Point[] coordinates;
    public int x, y;

    public Tetromino() {
        Random random = new Random();
        shape = Shape.values()[random.nextInt(Shape.values().length)];
        setShape(shape);
        x = 4;  // Start in the middle of the board
        y = 0;
    }

    private void setShape(Shape shape) {
        switch (shape) {
            case I:
                coordinates = new Point[]{new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)};
                break;
            case J:
                coordinates = new Point[]{new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)};
                break;
            case L:
                coordinates = new Point[]{new Point(2, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)};
                break;
            case O:
                coordinates = new Point[]{new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(1, 1)};
                break;
            case S:
                coordinates = new Point[]{new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)};
                break;
            case T:
                coordinates = new Point[]{new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)};
                break;
            case Z:
                coordinates = new Point[]{new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)};
                break;
        }
    }

    public void draw(Graphics g, int tileSize) {
        g.setColor(Color.RED);
        for (Point p : coordinates) {
            g.fillRect((x + p.x) * tileSize, (y + p.y) * tileSize, tileSize, tileSize);
        }
    }

    public void rotate() {
        if (shape == Shape.O) return;  // O-shape doesn't rotate

        for (Point p : coordinates) {
            int temp = p.x;
            p.x = p.y;
            p.y = -temp;
        }
    }

    public void rotateBack() {
        if (shape == Shape.O) return;  // O-shape doesn't rotate

        for (Point p : coordinates) {
            int temp = p.x;
            p.x = -p.y;
            p.y = temp;
        }
    }
}
