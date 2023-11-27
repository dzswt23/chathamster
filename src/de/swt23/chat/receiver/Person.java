package de.swt23.chat.receiver;

public class Person extends Receiver {

    private final String username;

    public Person(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }
}
