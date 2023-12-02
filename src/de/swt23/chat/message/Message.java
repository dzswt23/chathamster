package de.swt23.chat.message;

import de.swt23.chat.receiver.Entity;

/**
 * a message will be made an image or a text
 */
public abstract class Message {
    private final MessageDirection direction;
    private final Entity entity;
    private String timeStamp;

    //Constructor
    public Message(Entity entity, MessageDirection direction, String timeStamp) {
        this.entity = entity;
        this.direction = direction;
        this.timeStamp = timeStamp;
    }

    public Message(Entity entity, MessageDirection direction) {
        this.entity = entity;
        this.direction = direction;

    }

    public Entity getEntity() {
        return entity;
    }

    public MessageDirection getDirection() {
        return direction;
    }

    public String toString() {
        String output = timeStamp + " | ";
        if (direction == MessageDirection.IN) {
            output = output + entity + " -> me: ";
        } else {
            output = output + "Me -> " + entity + ": ";
        }
        return output;
    }


}
