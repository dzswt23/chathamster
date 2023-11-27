package de.swt23.chat.receiver;

import java.util.ArrayList;

public class Group extends Receiver {

    private final String groupName;
    private final ArrayList<Person> members;

    public Group(String groupName) {
        this.groupName = groupName;
        members = new ArrayList<Person>();
    }

    public String getGroupName() {
        return groupName;
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
