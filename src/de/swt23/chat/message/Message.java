package de.swt23.chat.message;

import de.swt23.chat.receiver.Receiver;

public abstract class Message {

    //Attribute
    private Receiver receiver;

    //Constructor
    public Message(Receiver receiver){
        this.receiver = receiver;
    }

    //Methods
    public Receiver getReceiver(){
        return receiver;
    }

}
