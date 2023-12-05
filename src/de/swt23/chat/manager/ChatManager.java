package de.swt23.chat.manager;

import de.swt23.chat.message.Image;
import de.swt23.chat.message.Message;
import de.swt23.chat.message.MessageDirection;
import de.swt23.chat.message.Text;
import de.swt23.chat.receiver.Entity;
import de.swt23.chat.receiver.Group;
import de.swt23.chat.receiver.Person;
import de.swt23.chat.session.Session;
import de.thm.oop.chat.base.server.BasicTHMChatServer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
        // check if there is a group or person who has that name
        if (getGroup(name) != null || getPerson(name) != null) {
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
        return group != null && groups.remove(group);
    }

    /**
     * get a group by its name
     *
     * @param name the name of the searched group
     * @return the group object that matched the name, null if no object was found
     */
    public Group getGroup(String name) {
        for (Group group : groups) {
            if (group.getName().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

    /**
     * add a person to a group
     *
     * @param person person that shall be added
     * @param group  group that the person shall be added to
     * @return true if the person was added successfully
     */
    public boolean addPersonToGroup(Person person, Group group) {
        if (!group.containsPerson(person)) {
            group.addPerson(person);
            return true;
        }
        return false;
    }

    /**
     * remove a person from a group
     *
     * @param person person that shall be removed
     * @param group  group that the person shall be removed from
     * @return true if the person was removed successfully
     */
    public boolean removePersonFromGroup(Person person, Group group) {
        return group.containsPerson(person) && group.removePerson(person);
    }

    /**
     * get a person by its username
     *
     * @param username username of the person
     * @return object of person or null if not found
     */
    public Person getPerson(String username) {
        for (Person person : getPeople()) {
            if (person.getName().equalsIgnoreCase(username)) {
                return person;
            }
        }
        return null;
    }

    /**
     * send a message (text or image) to a receiver (person or group)
     *
     * @param message the message to be sent
     * @return true if the message was sent successfully
     */
    public boolean sendMessage(Message message) {
        // check if the user is logged in
        if (currentSession == null) {
            return false;
        }
        // check if the user is trying to send a message to himself
        if (message.getEntity() instanceof Person) {
            if (currentSession.getUsername().equalsIgnoreCase(message.getEntity().getName())) {
                return false;
            }
        }
        if (message instanceof Image) {
            return sendImageMessage((Image) message);
        } else {
            return sendTextMessage((Text) message);
        }
    }

    /**
     * send an image message to a receiver (group or person)
     *
     * @param image the image that shall be sent
     * @return true if the message was sent successfully
     */
    private boolean sendImageMessage(Image image) {
        try {
            Entity entity = image.getEntity();
            if (entity instanceof Group group) {
                for (Person person : group.getMembers()) {
                    if (currentSession.getUsername().equalsIgnoreCase(person.getName())) {
                        continue;
                    }
                    chatServer.sendImageMessage(currentSession.getUsername(), currentSession.getPassword(), person.getName(), image.getMimeType(), image.getImageData());
                }
            } else {
                chatServer.sendImageMessage(currentSession.getUsername(), currentSession.getPassword(), entity.getName(), image.getMimeType(), image.getImageData());
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * send an image message to a receiver (group or person)
     *
     * @param text the text that shall be sent
     * @return true if the message was sent successfully
     */
    private boolean sendTextMessage(Text text) {
        try {
            Entity entity = text.getEntity();
            if (entity instanceof Group) {
                for (Person person : ((Group) entity).getMembers()) {
                    if (currentSession.getUsername().equalsIgnoreCase(person.getName())) {
                        continue;
                    }
                    chatServer.sendTextMessage(currentSession.getUsername(), currentSession.getPassword(), person.getName(), text.getText());
                }
            } else {
                chatServer.sendTextMessage(currentSession.getUsername(), currentSession.getPassword(), entity.getName(), text.getText());
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * get a list of all messages
     *
     * @return arraylist of all messages or null if the request failed
     */
    public ArrayList<Message> getMessages() {
        if (currentSession == null) {
            return null;
        }
        try {
            ArrayList<Message> messages = new ArrayList<>();
            // formats for the timestamp
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
            for (String stringMessage : chatServer.getMessages(currentSession.getUsername(), currentSession.getPassword(), 0)) {
                String[] messageContent = stringMessage.split("\\|");
                Message message;
                // transforming the timestamp into the new format
                String timestamp = outputFormat.format(inputFormat.parse(messageContent[1]));
                if (messageContent[4].equals("txt")) {
                    if (messageContent[2].equals("in")) {
                        message = new Text(getPerson(messageContent[3]), MessageDirection.IN, timestamp, messageContent[5]);
                    } else {
                        message = new Text(getPerson(messageContent[3]), MessageDirection.OUT, timestamp, messageContent[5]);
                    }
                } else {
                    if (messageContent[2].equals("in")) {
                        message = new Image(getPerson(messageContent[3]), MessageDirection.IN, timestamp, messageContent[7]);
                    } else {
                        message = new Image(getPerson(messageContent[3]), MessageDirection.OUT, timestamp, messageContent[7]);
                    }
                }
                messages.add(message);
            }
            return messages;
        } catch (IOException e) {
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }
}
