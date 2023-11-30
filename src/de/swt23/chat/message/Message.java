package de.swt23.chat.message;

import de.swt23.chat.receiver.Receiver;


public abstract class Message {
    private MessageDirection direction;
    //Attribute
    private Receiver receiver;

    private String timeStamp;

    //Constructor
    public Message(Receiver receiver, MessageDirection direction, String timeStamp) {
        this.receiver = receiver;
        this.direction = direction;
        this.timeStamp = timeStamp;
    }

    //Methods
    public Receiver getReceiver() {
        return receiver;
    }

    public MessageDirection getDirection(){
        return direction;
    }

    public String getTimeStamp(){
        return timeStamp;
    }

    public String toString(){
        String output= timeStamp+ " | ";
        if(direction == MessageDirection.IN){
            output = output + receiver+" -> mir: ";
        }else{
            output = output + "ich -> "+receiver+": ";
        }
        return output;
    }



}
