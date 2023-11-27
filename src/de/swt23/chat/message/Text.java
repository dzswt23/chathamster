package de.swt23.chat.message;

import de.swt23.chat.receiver.Receiver;

public class Text extends Message {

    //Attributes
    private String text;

    //Constructor
    public Text(Receiver receiver, String text) {
        super(receiver);
        this.text = text;

    }

    //Methods
    public String getText() {
        return text;
    }

}
