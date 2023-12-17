package de.swt23.chat;

import de.swt23.chat.backtracking.Backtracking;
import de.swt23.chat.hamster.Hamster;
import de.swt23.chat.manager.ChatManager;
import de.swt23.chat.message.Image;
import de.swt23.chat.message.Message;
import de.swt23.chat.message.MessageDirection;
import de.swt23.chat.message.Text;
import de.swt23.chat.receiver.Entity;
import de.swt23.chat.receiver.Group;
import de.swt23.chat.receiver.Person;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatProgram {
    private final Scanner scanner;
    private final ChatManager manager;

    private Hamster hamster;

    public ChatProgram() {
        scanner = new Scanner(System.in);
        manager = new ChatManager();
    }

    /**
     * The main entry point of the chat program.
     */
    public static void main(String[] args) {
        ChatProgram program = new ChatProgram();
        program.requestUserLogin();
        program.handleProgramMenu();
    }

    /**
     * Reads user input from the console.
     *
     * @param prompt The prompt to display to the user.
     * @return The user's input.
     */
    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * print a menu and get a selection by the user
     *
     * @param options the options the menu shall contain
     * @return the choice the user made
     */
    private int displayMenuAndGetChoice(String[] options) {
        while (true) {
            System.out.println("\nPlease enter a number:\n");
            // print the menu
            for (int i = 0; i < options.length; i++) {
                System.out.println("\t" + (i + 1) + " = " + options[i]);
            }
            // catch if the user did not enter a number
            try {
                // get the user input
                int number = Integer.parseInt(getUserInput("\nYour choice: "));
                // check if the number is valid
                if (number >= 1 && number <= options.length) {
                    return number;
                } else {
                    System.out.println("Invalid input, please enter a number from 1 - " + options.length);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input type, please enter a number from 1 - " + options.length);
            }
        }
    }

    /**
     * Requests user login credentials until a valid login is provided.
     */
    public void requestUserLogin() {
        System.out.println("* Welcome to the chat program of group H! *\n");

        boolean validLogin = false;
        while (!validLogin) {
            String username = getUserInput("Enter your username: ");
            String password = getUserInput("Enter your password: ");

            if (manager.login(username, password)) {
                System.out.println("Welcome " + username + ".");
                validLogin = true;
            } else {
                System.out.println("Incorrect login details!");
            }
        }
    }

    /**
     * Handles the main program menu, allowing users to perform various actions.
     */
    public void handleProgramMenu() {
        boolean exitProgram = false;
        while (!exitProgram) {
            int selection = displayMenuAndGetChoice(new String[]{
                    "Get a list of all users",
                    "Get a list of all messages",
                    "Manage your groups",
                    "Send a message",
                    "Chat Hamster",
                    "Exit"
            });
            switch (selection) {
                case 1:
                    listAllUsers();
                    break;
                case 2:
                    listAllMessages();
                    break;
                case 3:
                    manageGroups();
                    break;
                case 4:
                    sendMessage();
                    break;
                case 5:
                    startChatHamster();
                    break;
                case 6:
                    scanner.close();
                    System.out.println("See you next time!");
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Invalid selection!");
                    break;
            }
        }
    }

    /**
     * Displays a list of all users in the chat.
     */
    public void listAllUsers() {
        System.out.println("List of all users:");
        for (Person person : manager.getPeople()) {
            System.out.println("- " + person.getName());
        }
    }

    /**
     * Displays a list of all messages in the chat.
     */
    public void listAllMessages() {
        System.out.println("List of all messages:");
        ArrayList<Message> messages = manager.getMessages();
        if (messages == null) {
            System.out.println("An error occurred while loading your messages.");
            return;
        }
        if (messages.isEmpty()) {
            System.out.println("You did not receive messages yet.");
            return;
        }
        for (Message message : messages) {
            System.out.println(message);
        }
    }

    /**
     * Manages various group-related actions, such as creating, deleting, and displaying groups.
     */
    public void manageGroups() {
        boolean backToMainMenu = false;
        while (!backToMainMenu) {
            int selection = displayMenuAndGetChoice(new String[]{
                    "Create a new group",
                    "Delete an existing group",
                    "Show all created groups and their members",
                    "Add a person to a group",
                    "Remove a person from a group",
                    "Go back to the main menu"
            });
            switch (selection) {
                case 1:
                    createGroup();
                    break;
                case 2:
                    deleteGroup();
                    break;
                case 3:
                    showGroups();
                    break;
                case 4:
                    addUserToGroup();
                    break;
                case 5:
                    removeUserFromGroup();
                    break;
                case 6:
                    backToMainMenu = true;
                    break;
                default:
                    System.out.println("Invalid selection!");
                    break;
            }
        }
    }

    /**
     * Creates a new group with a specified name.
     */
    public void createGroup() {
        String groupName = getUserInput("Please enter a name for the group: ");
        if (manager.addGroup(groupName)) {
            System.out.println("The group " + groupName + " was added successfully.");
        } else {
            System.out.println("There is already a group or person called " + groupName + "!");
        }
    }

    /**
     * Deletes an existing group with a specified name.
     */
    public void deleteGroup() {
        String groupName = getUserInput("Please enter the name of the group: ");
        if (manager.removeGroup(groupName)) {
            System.out.println("The group " + groupName + " was removed successfully.");
        } else {
            System.out.println("The group could not be removed!");
        }
    }

    /**
     * Displays a list of all created groups and their members.
     */
    public void showGroups() {
        if (manager.getGroups().isEmpty()) {
            System.out.println("You have not created a group yet.");
            return;
        }
        for (Group group : manager.getGroups()) {
            System.out.println("Group name: " + group.getName());
            if (group.getMembers().isEmpty()) {
                System.out.println("This group is empty.\n");
                continue;
            }
            System.out.println("Members:");
            for (Person member : group.getMembers()) {
                System.out.println("- " + member.getName());
            }
            System.out.println("\n");
        }
    }

    /**
     * Adds users to a specified group.
     */
    public void addUserToGroup() {
        Group group = manager.getGroup(getUserInput("Please enter the name of the group: "));
        if (group == null) {
            System.out.println("This group does not exist!");
            return;
        }
        Person person = manager.getPerson(getUserInput("Please enter the name of the user: "));
        if (person == null) {
            System.out.println("This person does not exist!");
            return;
        }
        if (manager.addPersonToGroup(person, group)) {
            System.out.println(person.getName() + " was added successfully to " + group.getName() + ".");
        } else {
            System.out.println(person.getName() + " could not be added to " + group.getName() + ".");
        }
    }

    /**
     * Removes a user from a specified group.
     */
    public void removeUserFromGroup() {
        Group group = manager.getGroup(getUserInput("Please enter the name of the group: "));
        if (group == null) {
            System.out.println("This group does not exist!");
            return;
        }
        Person person = manager.getPerson(getUserInput("Please enter the name of the user: "));
        if (person == null) {
            System.out.println("This person does not exist!");
            return;
        }
        if (manager.removePersonFromGroup(person, group)) {
            System.out.println(person.getName() + " was removed successfully from " + group.getName() + ".");
        } else {
            System.out.println(person.getName() + " could not be removed from " + group.getName() + ".");
        }
    }

    /**
     * Initiates the process of sending a message to a person or group.
     */
    public void sendMessage() {
        String input = getUserInput("Please enter the name of the message recipient (username or group name): ");
        Entity entity = manager.getPerson(input);
        if (entity == null) {
            entity = manager.getGroup(input);
            if (entity == null) {
                System.out.println("There is no group or person called " + input + "!");
                return;
            }
        }

        boolean backToMainMenu = false;
        while (!backToMainMenu) {
            int selection = displayMenuAndGetChoice(new String[]{
                    "Send an image",
                    "Send a text",
                    "Go back to the main menu"
            });
            switch (selection) {
                case 1:
                    sendImageMessage(entity);
                    break;
                case 2:
                    sendTextMessage(entity);
                    break;
                case 3:
                    backToMainMenu = true;
                    break;
                default:
                    System.out.println("Invalid selection!");
                    break;
            }
        }
    }

    /**
     * Sends an image message to the specified receiver.
     * https://docs.oracle.com/javase/8/docs/api/java/io/File.html
     *
     * @param entity The receiver of the image message.
     */
    public void sendImageMessage(Entity entity) {
        boolean validImagePath = false;
        String imagePath = null;
        while (!validImagePath) {
            imagePath = getUserInput("Enter path of the image: ");

            // remove " from the start and end of the message
            if (imagePath.startsWith("\"")) {
                imagePath = imagePath.substring(1, imagePath.length() - 1);
            }
            if (imagePath.endsWith("\"")) {
                imagePath = imagePath.substring(0, imagePath.length() - 2);
            }

            // check if the provided path exists AND that it is not a directory
            File file = new File(imagePath);
            if (file.exists() && !file.isDirectory()) {
                // check if the image is smaller than 1MB
                if ((file.length() / 1024) <= 1024) {
                    validImagePath = true;
                } else {
                    System.out.println("The selected image is bigger than 1MB!");
                }
            } else {
                System.out.println("Invalid image path. Please enter a valid path!");
            }
        }

        Message message = new Image(entity, MessageDirection.OUT, imagePath);
        if (manager.sendMessage(message)) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("The message was not sent!");
        }
    }

    /**
     * Sends a text message to the specified receiver.
     *
     * @param entity The receiver of the text message.
     */
    public void sendTextMessage(Entity entity) {
        Message message = new Text(entity, MessageDirection.OUT, getUserInput("Content of the message: "));
        if (manager.sendMessage(message)) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("The message was not sent!");
        }
    }

    /**
     * Open the remote ChatHamster
     * for Backtracking of a possible path
     *
     */
    public void startChatHamster(){
        System.out.println("Welcome to the chat hamster");
        Backtracking backtracking = manager.startHamster();

        if (backtracking == null) {
            return;
        }

        String input = "";
        while (!input.equals("start")) {
            input = getUserInput("Enter start to send the hamster on its way: ");
        }

        backtracking.geheWeg();
        System.out.println("Your hamster will start moving shortly.");
    }
}