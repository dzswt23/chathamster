package de.swt23.chat.manager;

import de.swt23.chat.session.Session;
import de.thm.oop.chat.base.server.BasicTHMChatServer;

import java.io.IOException;

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

    public boolean addGroup(String name) {
        // add group
        return true;
    }

    public boolean removeGroup(String name) {
        // remove group
        return true;
    }

    // get group by name

    public boolean addPersonToGroup(String username, String groupName) {
        // search user
        // add to group
        return true;
    }

    public boolean removePersonFromGroup(String username, String groupName) {
        // search user
        // remove from group
        return true;
    }

    public String[] getUsersInGroup(String username) {
        return null;
    }

    public String[] getGroupNames() {
        return null;
    }
}
