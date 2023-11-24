package de.swt23.chat.message;

public abstract class Message {

    //Attribute
    Receiver receiver;

    //Constructor
    public Message(Receiver receiver){
        this.receiver = receiver;
    }

    //Methods
    public Receiver getReceiver(){
        return receiver;
    }

}
