package domain;

public class MowerData {
    private final int x, y;
    private final Direction orientation;
    private final String instructions;

    public MowerData(int x, int y, Direction orientation, String instructions) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.instructions = instructions;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getOrientation() {
        return orientation;
    }

    public String getInstructions() {
        return instructions;
    }
}