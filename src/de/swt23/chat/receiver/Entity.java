package de.swt23.chat.receiver;

/**
 * an entity will be made a group or a person
 */
public abstract class Entity {
    private final String name;

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
