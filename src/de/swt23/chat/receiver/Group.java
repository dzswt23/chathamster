package de.swt23.chat.receiver;

import java.util.ArrayList;

public class Group extends Receiver {

    private final ArrayList<Person> members;

    public Group(String name) {
        super(name);
        members = new ArrayList<Person>();
    }

    public void addPerson(Person member) {
        members.add(member);
    }

    public boolean removePerson(Person member) {
        return members.remove(member);
    }

    public ArrayList<Person> getMembers() {
        return members;
    }
}
