package de.swt23.chat;

public enum Direction {

    NORTH(0),

    WEST(3),

    SOUTH(2),

    EAST(1);

    private final int value;
    private Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
