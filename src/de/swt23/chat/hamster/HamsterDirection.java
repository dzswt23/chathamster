package de.swt23.chat.hamster;

public enum HamsterDirection {

    NORTH(0),

    WEST(3),

    SOUTH(2),

    EAST(1);

    private final int value;
    private HamsterDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
