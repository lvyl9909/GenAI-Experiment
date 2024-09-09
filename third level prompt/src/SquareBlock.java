public class SquareBlock extends Block {
    public SquareBlock() {
        super(new int[][] {
                {1, 1},
                {1, 1}
        });
    }

    @Override
    public void rotate() {
        // Square block doesn't rotate
    }
}
