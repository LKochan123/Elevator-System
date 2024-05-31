package org.example;

public enum Direction {
    UPWARD("UP"),
    DOWNWARD("DOWN"),
    NONE("NONE");

    private final String name;

    Direction(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
