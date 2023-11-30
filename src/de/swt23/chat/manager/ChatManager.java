package de.swt23.chat.manager;

import de.swt23.chat.message.Image;
import de.swt23.chat.message.Message;
import de.swt23.chat.message.Text;
import de.swt23.chat.receiver.Group;
import de.swt23.chat.receiver.Person;
import de.swt23.chat.receiver.Receiver;
import de.swt23.chat.session.Session;
import de.thm.oop.chat.base.server.BasicTHMChatServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * manager class to manage connection to server and functionality
 */
public class ChatManager {
    private final BasicTHMChatServer chatServer;
    private final ArrayList<Group> groups;
    private final ArrayList<Person> people;
    private Session currentSession;

    public ChatManager() {
        chatServer = new BasicTHMChatServer();
        groups = new ArrayList<>();
        people = new ArrayList<>();
    }

    /**
     * check login data of the user
     *
     * @param username username of the user
     * @param password password of the user
     * @return true if the login was successful
     */
    public boolean login(String username, String password) {
        try {
            // throws exception if username & password are not correct
            chatServer.getUsers(username, password);
            currentSession = new Session(username, password);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * get a list of all the users
     *
     * @return arraylist of registered people
     */
    public ArrayList<Person> getPeople() {
        if (currentSession == null) {
            return null;
        }
        // check for cached user list
        if (!people.isEmpty()) {
            return people;
        }
        try {
            // get user list from server
            String[] usernames = chatServer.getUsers(currentSession.getUsername(), currentSession.getPassword());
            for (String username : usernames) {
                people.add(new Person(username));
            }
            return people;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * create a new group
     *
     * @param name name of the new group
     * @return true if the group was created successfully
     */
    public boolean addGroup(String name) {
        if (getGroup(name) != null) {
            return false;
        }
        // check if there is a person that has this name
        if (getPerson(name) != null) {
            return false;
        }
        groups.add(new Group(name));
        return true;
    }

    /**
     * remove a group
     *
     * @param name name of the group that shall be removed
     * @return true if the group was removed successfully
     */
    public boolean removeGroup(String name) {
        Group group = getGroup(name);
        if (group == null) {
            return false;
        }
        return groups.remove(group);
    }

    /**
     * get a group by its name
     *
     * @param name the name of the searched group
     * @return the group object that matched the name, null if no object was found
     */
    public Group getGroup(String name) {
        for (Group group : groups) {
            if (group.getGroupName().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

    /**
     * add a person to a group
     *
     * @param username  username of the person that shall be added
     * @param groupName name of the group that the person shall be added to
     * @return true if the person was added successfully
     */
    public boolean addPersonToGroup(String username, String groupName) {
        Person person = getPerson(username);
        if (person == null) {
            return false;
        }
        Group group = getGroup(groupName);
        if (group == null) {
            return false;
        }
        if (group.getMembers().contains(person)) {
            return false;
        }
        group.getMembers().add(person);
        return true;
    }

    /**
     * remove a person from a group
     *
     * @param username  username of the person that shall be removed
     * @param groupName name of the group that the person shall be removed from
     * @return true if the person was removed successfully
     */
    public boolean removePersonFromGroup(String username, String groupName) {
        Person person = getPerson(username);
        if (person == null) {
            return false;
        }
        Group group = getGroup(groupName);
        if (group == null) {
            return false;
        }
        if (!group.getMembers().contains(person)) {
            return false;
        }
        group.getMembers().remove(person);
        return true;
    }

    /**
     * get a person by its username
     *
     * @param username username of the person
     * @return object of person or null if not found
     */
    public Person getPerson(String username) {
        for (Person person : getPeople()) {
            if (person.getUsername().equalsIgnoreCase(username)) {
                return person;
            }
        }
        return null;
    }

    /**
     * send a message (text or image) to a receiver (person or group)
     *
     * @param message  the message to be sent
     * @return true if the message was sent successfully
     */
    public boolean sendMessage(Message message) {
        // check if the user is logged in
        if (currentSession == null) {
            return false;
        }
        if (message.getReceiver() instanceof Person) {
            if (currentSession.getUsername().equalsIgnoreCase(((Person) message.getReceiver()).getUsername())) {
                return false;
            }
        }
        if (message instanceof Image) {
            return sendImageMessage((Image) message, message.getReceiver());
        } else {
            return sendTextMessage((Text) message, message.getReceiver());
        }
    }

    /**
     * send an image message to a receiver (group or person)
     *
     * @param image    the image that shall be sent
     * @param receiver the receiver (can be a group or a person)
     * @return true if the message was sent successfully
     */
    public boolean sendImageMessage(Image image, Receiver receiver) {
        try {
            if (receiver instanceof Group) {
                for (Person person : ((Group) receiver).getMembers()) {
                    if (currentSession.getUsername().equalsIgnoreCase(person.getUsername())) {
                        continue;
                    }
                    chatServer.sendImageMessage(currentSession.getUsername(), currentSession.getPassword(), person.getUsername(), image.getMimeType(), image.getImageData());
                }
            } else {
                chatServer.sendImageMessage(currentSession.getUsername(), currentSession.getPassword(), ((Person) receiver).getUsername(), image.getMimeType(), image.getImageData());
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * send an image message to a receiver (group or person)
     *
     * @param text     the text that shall be sent
     * @param receiver the receiver (can be a group or a person)
     * @return true if the message was sent successfully
     */
    public boolean sendTextMessage(Text text, Receiver receiver) {
        try {
            if (receiver instanceof Group) {
                for (Person person : ((Group) receiver).getMembers()) {
                    if (currentSession.getUsername().equalsIgnoreCase(person.getUsername())) {
                        continue;
                    }
                    chatServer.sendTextMessage(currentSession.getUsername(), currentSession.getPassword(), person.getUsername(), text.getText());
                }
            } else {
                chatServer.sendTextMessage(currentSession.getUsername(), currentSession.getPassword(), ((Person) receiver).getUsername(), text.getText());
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * get all existing group names
     *
     * @return a string array containing all the group names
     */
    public ArrayList<String> getGroupNames() {
        ArrayList<String> groupNames = new ArrayList<>();
        for (Group group : groups) {
            groupNames.add(group.getGroupName());
        }
        return groupNames;
    }

    /**
     * get a list of the most recent 100 messages
     *
     * @return arraylist of the recent messages or null if the request failed
     */
    public ArrayList<String> getMessages() {
        if (currentSession == null) {
            return null;
        }
        try {
            return new ArrayList<>(Arrays.asList(chatServer.getMostRecentMessages(currentSession.getUsername(), currentSession.getPassword())));
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }
}
