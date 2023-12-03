package de.swt23.chat.receiver;

import java.util.ArrayList;

/**
 * a group holds multiple people as members
 */
public class Group extends Entity {

    private final ArrayList<Person> members;

    public Group(String name) {
        super(name);
        members = new ArrayList<>();
    }

    /**
     * add a new member to the group
     *
     * @param member the person that shall be added
     */
    public void addPerson(Person member) {
        members.add(member);
    }

    /**
     * remove a member of the group
     *
     * @param member the person that shall be removed
     * @return true if the person was a member of the group and successfully removed
     */
    public boolean removePerson(Person member) {
        return members.remove(member);
    }

    public ArrayList<Person> getMembers() {
        return members;
    }
}
