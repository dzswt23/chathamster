package de.swt23.chat;

import de.swt23.chat.manager.ChatManager;
import de.swt23.chat.message.Image;
import de.swt23.chat.message.Message;
import de.swt23.chat.message.Text;
import de.swt23.chat.receiver.Group;
import de.swt23.chat.receiver.Person;
import de.swt23.chat.receiver.Receiver;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatProgram {
    private static Scanner scanner;
    private static ChatManager manager;
    private static boolean exitProgram = false;
    private static String groupName;

    /**
     * Reads user input from the console.
     * @param prompt The prompt to display to the user.
     * @return The user's input.
     */
    public static String getUserInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Closes the scanner used for user input.
     */
    private static void closeScanner() {
        scanner.close();
    }

    /**
     * Requests user login credentials until a valid login is provided.
     */
    public static void requestUserLogin() {
        System.out.println("*Welcome to the chat program of group H!*\n");

        boolean validLogin = false;
        while (!validLogin) {
            String username = getUserInput("Enter your username:");
            String password = getUserInput("Enter your password:");

            if (manager.login(username, password)) {
                System.out.println("Welcome " + username + "\n\n");
                validLogin = true;
            } else {
                System.out.println("Incorrect login details\n\n");
            }
        }
    }

    /**
     * Handles the main program menu, allowing users to perform various actions.
     */
    public static void handleProgramMenu() {
        String letter;

        while (!exitProgram) {
            System.out.println("*Program menu*\n");
            System.out.println("Choose between:");
            System.out.println("    A = List of all users");
            System.out.println("    B = List of all messages");
            System.out.println("    C = Manage groups");
            System.out.println("    D = Send message");
            System.out.println("    E = Exit program menu");

            letter = getUserInput("Your choice:").toLowerCase();

            switch (letter) {
                case "a":
                    listOfAllUsers();
                    break;
                case "b":
                    listOfAllMessages();
                    break;
                case "c":
                    manageGroups();
                    break;
                case "d":
                    sendMessage();
                    break;
                case "e":
                    exitProgramMenu();
                    break;
                default:
                    System.out.println("Invalid selection. Please re-enter: A, B, C, D, E");
                    break;
            }
        }
    }

    /**
     * Displays a list of all users in the chat.
     */
    public static void listOfAllUsers() {
        System.out.println("List of all users:");
        for (Person person : manager.getPeople()) {
            System.out.println("- " + person.getUsername());
        }
    }

    /**
     * Displays a list of all messages in the chat.
     */
    public static void listOfAllMessages() {
        System.out.println("List of all messages:");
        ArrayList<String> messages = manager.getMessages();
        if (messages == null) {
            System.out.println("An error occurred while loading your messages");
            handleProgramMenu();
        }
        if (messages.isEmpty()) {
            System.out.println("You did not receive messages yet");
            handleProgramMenu();
        }
        for (String message : messages) {
            System.out.println("- " + message);
        }
    }

    /**
     * Manages various group-related actions, such as creating, deleting, and displaying groups.
     */
    public static void manageGroups() {
        System.out.println("Manage groups:");

        boolean group = false;

        while(!group) {
            System.out.println("Choose between:");
            System.out.println("    A = Create group");
            System.out.println("    B = Delete group");
            System.out.println("    C = Show groups");
            System.out.println("    D = Add users to group");
            System.out.println("    E = Remove user from group");
            System.out.println("    F = Back to main menu");

            String manageGroups = getUserInput("Your choice:").toLowerCase();

            switch (manageGroups) {
                case "a":
                    createGroup();
                    break;
                case "b":
                    deleteGroup();
                    break;
                case "c":
                    showGroups();
                    break;
                case "d":
                    addUsersToGroup();
                    break;
                case "e":
                    removeUserFromGroup();
                    break;
                case "f":
                    group = true;
                    break;

                default:
                    System.out.println("Invalid selection. Please re-enter: A, B, C, D, E, F");
                    break;
            }
        }
    }

    /**
     * Creates a new group with a specified name.
     */
    public static void createGroup() {
        groupName = getUserInput("Enter group name");
        if (manager.addGroup(groupName)) {
            System.out.println("The group " + groupName + " was added successfully");
        } else {
            System.out.println("The group could not be created");
        }
    }

    /**
     * Deletes an existing group with a specified name.
     */
    public static void deleteGroup() {
        groupName = getUserInput("Enter group name");
        if (manager.removeGroup(groupName)) {
            System.out.println("The group " + groupName + " was removed successfully");
        } else {
            System.out.println("The group could not be removed");
        }
    }

    /**
     * Displays a list of all created groups and their members.
     */
    public static void showGroups() {
        System.out.println("\nOutput list group\n");
        if (manager.getGroups().isEmpty()) {
            System.out.println("You have not created a group yet");
            manageGroups();
        }
        for (Group group : manager.getGroups()) {
            System.out.println("Group name: " + group.getGroupName());
            if (group.getMembers().isEmpty()) {
                System.out.println("This group is empty\n");
                break;
            }
            System.out.println("Members:");
            for (Person member : group.getMembers()) {
                System.out.println("- " + member.getUsername());
            }
            System.out.println("\n");
        }
    }

    /**
     * Adds users to a specified group.
     */
    public static void addUsersToGroup() {
        Group group = manager.getGroup(getUserInput("Enter group name"));
        if (group == null) {
            System.out.println("This group does not exist");
            manageGroups();
        }
        Person person = manager.getPerson(getUserInput("Enter name of the user"));
        if (person == null) {
            System.out.println("This person does not exist");
            manageGroups();
        }
        if (manager.addPersonToGroup(person.getUsername(), group.getGroupName())) {
            System.out.println(person.getUsername() + " was added successfully to " + group.getGroupName());
        } else {
            System.out.println(person.getUsername() + " could not be added to " + group.getGroupName());
        }
    }

    /**
     * Removes a user from a specified group.
     */
    public static void removeUserFromGroup() {
        Group group = manager.getGroup(getUserInput("Enter group name"));
        if (group == null) {
            System.out.println("This group does not exist");
            manageGroups();
        }
        Person person = manager.getPerson(getUserInput("Enter name of the user"));
        if (person == null) {
            System.out.println("This person does not exist");
            manageGroups();
        }
        if (manager.removePersonFromGroup(person.getUsername(), group.getGroupName())) {
            System.out.println(person.getUsername() + " was removed successfully from " + group.getGroupName());
        } else {
            System.out.println(person.getUsername() + " could not be removed from " + group.getGroupName());
        }
    }

    /**
     * Initiates the process of sending a message to a person or group.
     */
    public static void sendMessage() {
        String input = getUserInput("Recipient of the message(group or person): \n        Or go back to the main menu with C");
        Receiver receiver = manager.getPerson(input);
        if (receiver == null) {
            receiver = manager.getGroup(input);
            if (receiver == null) {
                System.out.println("There is no group or person called " + input);
                handleProgramMenu();
            }
        }

        boolean message = false;

        while(!message){
            System.out.println("Choose between:");
            System.out.println("    A = Image message");
            System.out.println("    B = Text message");
            System.out.println("    C = Back to main menu");

            String imageOrText = getUserInput("Your choice:").toLowerCase();
            switch (imageOrText) {
                case "a":
                    imageMessage(receiver);
                    break;
                case "b":
                    textMessage(receiver);
                    break;
                case "c":
                    message = true;
                    break;
                default:
                    System.out.println("Invalid selection. Please re-enter: A, B, C");
                    break;
            }
        }
    }

    /**
     * Sends an image message to the specified receiver.
     * @param receiver The receiver of the image message.
     */
    public static void imageMessage(Receiver receiver) {
        boolean validImagePath = false;
        String imagePath = null;
        while (!validImagePath) {
            imagePath = getUserInput("Enter path of the image:");
            if (isValidImagePath(imagePath)) {
                validImagePath = true;
            } else {
                System.out.println("Invalid image path. Please enter a valid path.");
            }
        }

        Message message = new Image(receiver, imagePath);
        if (manager.sendMessage(message)) {
            System.out.println("Message sent successfully");
        } else {
            System.out.println("The message was not sent");
        }
    }

    /**
     * Method to check file path.
     */
    private static boolean isValidImagePath(String path) {
        File file = new File(path);
        return file.exists() && !file.isDirectory();
    }

    /**
     * Sends a text message to the specified receiver.
     * @param receiver The receiver of the text message.
     */
    public static void textMessage(Receiver receiver) {
        Message message = new Text(receiver, getUserInput("Content of the message:"));
        if (manager.sendMessage(message)) {
            System.out.println("Message sent successfully");
        } else {
            System.out.println("The message was not sent");
        }
    }

    /**
     * Exits the program menu, setting the exitProgram flag to true.
     * @return The value of exitProgram (true).
     */
    public static boolean exitProgramMenu() {
        System.out.println("See you next time");
        exitProgram = true;
        return exitProgram;
    }

    /**
     * The main entry point of the chat program.
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        manager = new ChatManager();
        try {
            requestUserLogin();
            handleProgramMenu();
        } finally {
            closeScanner();
        }
    }
}
