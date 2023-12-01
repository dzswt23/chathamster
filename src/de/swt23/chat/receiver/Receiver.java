package de.swt23.chat.receiver;

public abstract class Receiver {
    private final String name;

    public Receiver(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
