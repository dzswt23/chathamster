package de.swt23.chat.message;

import de.swt23.chat.receiver.Receiver;

public class Text extends Message {

    //Attributes
    private String text;

    //Constructor
    public Text(Receiver receiver, MessageDirection direction, String timeStamp,String text) {
        super(receiver,direction, timeStamp);
        this.text = text;

    }

    //Methods
    public String getText() {
        return text;
    }

    @Override
    public String toString (){
        return super.toString()+ text;
    }

}
