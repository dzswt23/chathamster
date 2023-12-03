package de.swt23.chat.message;

import de.swt23.chat.receiver.Entity;

/**
 * the text class inherits from message and provides information about a text message
 */
public class Text extends Message {
    private final String text;

    // Constructor for listing of the messages
    public Text(Entity entity, MessageDirection direction, String timeStamp, String text) {
        super(entity, direction, timeStamp);
        this.text = text;
    }

    // Constructor for outgoing messages
    public Text(Entity entity, MessageDirection direction, String text) {
        super(entity, direction);
        this.text = text;
    }

    //Methods
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return super.toString() + text;
    }

}
