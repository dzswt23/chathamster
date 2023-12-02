package de.swt23.chat.message;

import de.swt23.chat.receiver.Entity;

/**
 * the text class inherits from message and provides information about a text message
 */
public class Text extends Message {

    //Attributes
    private final String text;

    //Constructor
    public Text(Entity entity, MessageDirection direction, String timeStamp, String text) {
        super(entity, direction, timeStamp);
        this.text = text;
    }

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
