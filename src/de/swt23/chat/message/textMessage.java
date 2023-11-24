package de.swt23.chat.message;

public class textMessage extends Message{

    //Attributes
    private String text;

    //Constructor
    public textMessage(String text){
        super(receiver);
        this.text = text;

    }

    //Methods
    public String getText(){
        return text;
    }

}
