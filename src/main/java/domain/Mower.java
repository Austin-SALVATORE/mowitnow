package domain;

public class Mower {
    private int x, y; // Coordinates of the mower
    private Direction orientation; // Current direction
    private final Lawn lawn; // Reference to the lawn for boundary checks

    public Mower(int x, int y, Direction orientation, Lawn lawn) {
        if (!lawn.isWithinBounds(x, y)) {
            throw new IllegalArgumentException("Initial position is out of bounds");
        }
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.lawn = lawn;
    }

    public void turnRight() {
        orientation = orientation.rotateRight();
    }

    public void turnLeft() {
        orientation = orientation.rotateLeft();
    }

    public void forward() {
        int newX = x, newY = y;
        switch (orientation) {
            case N -> newY++;
            case E -> newX++;
            case S -> newY--;
            case W -> newX--;
        }
        if (lawn.isWithinBounds(newX, newY)) {
            x = newX;
            y = newY;
        }
    }

    public void executeInstructions(String instructions) {
        for (char instruction : instructions.toCharArray()) {
            switch (instruction) {
                case 'D' -> turnRight();
                case 'G' -> turnLeft();
                case 'A' -> forward();
                default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
            }
        }
    }

    public String getPosition() {
        return x + " " + y + " " + orientation;
    }
}
