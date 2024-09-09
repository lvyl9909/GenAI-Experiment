import javax.swing.*;
import java.awt.*;

public class Tetris extends JFrame {

    private JLabel statusBar;

    public Tetris() {
        initUI();
    }

    private void initUI() {
        statusBar = new JLabel(" 0");
        add(statusBar, BorderLayout.SOUTH);
        var board = new Board(this);
        add(board);
        board.start();

        setTitle("Tetris");
        setSize(200, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JLabel getStatusBar() {
        return statusBar;
    }

    public static void main(String[] args) {
        var game = new Tetris();
        game.setVisible(true);
    }
}
