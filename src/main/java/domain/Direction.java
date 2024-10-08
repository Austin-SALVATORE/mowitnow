package domain;

public enum Direction {
    N, E, S, W;

    public Direction rotateRight() {
        return values()[(ordinal() + 1) % values().length];
    }

    public Direction rotateLeft() {
        return values()[(ordinal() + 3) % values().length];
    }
}