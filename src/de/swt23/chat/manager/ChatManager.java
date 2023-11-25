package de.swt23.chat.manager;

import de.swt23.chat.session.Session;
import de.thm.oop.chat.base.server.BasicTHMChatServer;

import java.io.IOException;
import java.io.InputStream;

/**
 * manager class to manage connection to server and functionality
 * @author Julian Oswald
 */
public class ChatManager {
    private BasicTHMChatServer chatServer;
    private Session currentSession;
    // Arraylist for person
    // Arraylist for groups

    public ChatManager() {
        chatServer = new BasicTHMChatServer();
    }

    /**
     * check login data of the user
     * @param username username of the user
     * @param password password of the user
     * @return true if the login was successful
     */
    public boolean login(String username, String password) {
        try {
            // throws error if username & password are not correct
            chatServer.getUsers(username, password);
            currentSession = new Session(username, password);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * get a list of all the users
     * @return string array of registered usernames
     */
    public String[] getUserList() {
        if (currentSession == null) {
            return null;
        }
        try {
            return chatServer.getUsers(currentSession.getUsername(), currentSession.getPassword());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * create a new group
     * @param name name of the new group
     * @return true if the group was created successfully
     */
    public boolean addGroup(String name) {
        // add group
        return true;
    }

    /**
     * remove a group
     * @param name name of the group that shall be removed
     * @return true if the group was removed successfully
     */
    public boolean removeGroup(String name) {
        // remove group
        return true;
    }

    /**
     * get a group by its name
     * @param name the name of the searched group
     * @return the group object that matched the name, null if no object was found
     */
    /*public Group getGroup(String name) {
        for (Group group : groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }*/

    /**
     * add a person to a group
     * @param username username of the person that shall be added
     * @param groupName name of the group that the person shall be added to
     * @return true if the person was added successfully
     */
    public boolean addPersonToGroup(String username, String groupName) {
        // search user
        // add to group
        return true;
    }

    /**
     * remove a person from a group
     * @param username username of the person that shall be removed
     * @param groupName name of the group that the person shall be removed from
     * @return true if the person was removed successfully
     */
    public boolean removePersonFromGroup(String username, String groupName) {
        // search user
        // remove from group
        return true;
    }

    /**
     * send a message (text or image) to a receiver (person or group)
     * @param message the message to be sent
     * @param receiver the recipient of the message
     * @return true if the message was sent successfully
     */
    /*public boolean sendMessage(Message message, Receiver receiver) {
        if (message instance of Image) {
            return sendImageMessage((Image) message, receiver);
        } else {
            return sendTextMessage((Text) message, receiver);
        }
    }*/

    /**
     * send an image message to a receiver (group or person)
     * @param image the image that shall be sent
     * @param receiver the receiver (can be a group or a person)
     * @return true if the message was sent successfully
     */
    /*public boolean sendImageMessage(Image image, Receiver receiver) {
        String mimeType = image.getMimeType();
        InputStream imageData = image.getImageData();
        if (receiver instanceof Group) {
            for (Person person : (Group) receiver.getMembers()) {
                chatServer.sendImageMessage(session.getUsername(), session.getPassword(), person.getName(), image.getMimeType(), image.getImageData());
            }
        } else {
            chatServer.sendTextMessage(session.getUsername(), session.getPassword(), (Person) receiver.getName(), text.getText());
        }
        return true;
    }*/

    /**
     * send an image message to a receiver (group or person)
     * @param text the text that shall be sent
     * @param receiver the receiver (can be a group or a person)
     * @return true if the message was sent successfully
     */
    /*public boolean sendTextMessage(Text text, Receiver receiver) {
        if (receiver instanceof Group) {
            for (Person person : (Group) receiver.getMembers()) {
                chatServer.sendTextMessage(session.getUsername(), session.getPassword(), person.getName(), text.getText());
            }
        } else {
            chatServer.sendTextMessage(session.getUsername(), session.getPassword(), (Person) receiver.getName(), text.getText());
        }
        return true;
    }*/

    /**
     * get all existing group names
     * @return a string array containing all the group names
     */
    /*public String[] getGroupNames() {
        String[] groupNames = new String[groups.length];
        for (int i = 0; i < groups.length; i++) {
            groupNames[i] = groups[i].getName();
        }
        return groupNames;
    }*/
}
