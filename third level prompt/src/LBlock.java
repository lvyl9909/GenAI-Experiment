public class LBlock extends Block {
    public LBlock() {
        super(new int[][] {
                {1, 0},
                {1, 0},
                {1, 1}
        });
    }

    @Override
    public void rotate() {
        int[][] rotatedShape = new int[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                rotatedShape[j][shape.length - 1 - i] = shape[i][j];
            }
        }
        shape = rotatedShape;
    }
}
