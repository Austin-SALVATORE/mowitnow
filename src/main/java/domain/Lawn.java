package domain;

public class Lawn {
    private final int maxX, maxY;

    public Lawn(int maxX, int maxY) {
        if (maxX < 0 || maxY < 0) {
            throw new IllegalArgumentException("Lawn dimensions must be non-negative");
        }
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x <= maxX && y >= 0 && y <= maxY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}