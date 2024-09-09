public class LineBlock extends Block {
    public LineBlock() {
        super(new int[][] {
                {1, 1, 1, 1}
        });
    }

    @Override
    public void rotate() {
        // Rotates the line block (vertical to horizontal and vice versa)
        if (shape.length == 1) {
            shape = new int[][] {
                    {1},
                    {1},
                    {1},
                    {1}
            };
        } else {
            shape = new int[][] {
                    {1, 1, 1, 1}
            };
        }
    }
}
