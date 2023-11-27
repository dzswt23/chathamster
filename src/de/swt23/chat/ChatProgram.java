package de.swt23.chat;

import de.swt23.chat.manager.ChatManager;
import de.swt23.chat.receiver.Group;
import de.swt23.chat.receiver.Person;

import java.util.ArrayList;
import java.util.Scanner;

public class ChatProgram {
    private static Scanner scanner;
    private static ChatManager manager;

    public static String getUserInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    private static void closeScanner() {
        scanner.close();
    }

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

    public static void handleProgramMenu() {
        boolean exitProgram = false;
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
                    System.out.println("List of all users:");
                    for (Person person : manager.getPeople()) {
                        System.out.println("- " + person.getUsername());
                    }
                    break;
                case "b":
                    System.out.println("List of all messages:");
                    ArrayList<String> messages = manager.getMessages();
                    if (messages == null) {
                        System.out.println("An error occurred whilst loading your messages");
                        break;
                    }
                    if (messages.isEmpty()) {
                        System.out.println("You did not receive messages yet");
                        break;
                    }
                    for (String message : messages) {
                        System.out.println("- " + message);
                    }
                    break;
                case "c":
                    System.out.println("Manage groups:");

                    String managegroups;

                    do {
                        System.out.println("Choose between:");
                        System.out.println("    A = Create group");
                        System.out.println("    B = Delete group");
                        System.out.println("    C = Show groups");
                        System.out.println("    D = Add users to group");
                        System.out.println("    E = Remove user from group");
                        System.out.println("    F = Back to main menu");

                        managegroups = getUserInput("Your choice:").toLowerCase();
                        String groupName;
                        switch (managegroups) {
                            case "a":
                                groupName = getUserInput("Enter group name");
                                if (manager.addGroup(groupName)) {
                                    System.out.println("The group " + groupName + " was added successfully");
                                } else {
                                    System.out.println("The group could not be created");
                                }
                                break;
                            case "b":
                                groupName = getUserInput("Enter group name");
                                if (manager.removeGroup(groupName)) {
                                    System.out.println("The group " + groupName + " was removed successfully");
                                } else {
                                    System.out.println("The group could not be removed");
                                }
                                break;
                            case "c":
                                System.out.println("\nAusgabe Liste Gruppe\n");
                                if (manager.getGroups().isEmpty()) {
                                    System.out.println("You have not created a group yet");
                                    break;
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
                                break;
                            case "d":
                                Group group = manager.getGroup(getUserInput("Enter group name"));
                                if (group == null) {
                                    System.out.println("This group does not exist");
                                    break;
                                }
                                Person person = manager.getPerson(getUserInput("Enter name of the user"));
                                if (person == null) {
                                    System.out.println("This person does not exist");
                                    break;
                                }
                                if (manager.addPersonToGroup(person.getUsername(), group.getGroupName())) {
                                    System.out.println(person.getUsername() + " was added successfully to " + group.getGroupName());
                                } else {
                                    System.out.println(person.getUsername() + " could not be added to " + group.getGroupName());
                                }
                                break;
                            case "e":
                                group = manager.getGroup(getUserInput("Enter group name"));
                                if (group == null) {
                                    System.out.println("This group does not exist");
                                    break;
                                }
                                person = manager.getPerson(getUserInput("Enter name of the user"));
                                if (person == null) {
                                    System.out.println("This person does not exist");
                                    break;
                                }
                                if (manager.removePersonFromGroup(person.getUsername(), group.getGroupName())) {
                                    System.out.println(person.getUsername() + " was removed successfully from " + group.getGroupName());
                                } else {
                                    System.out.println(person.getUsername() + " could not be removed from " + group.getGroupName());
                                }
                                break;
                            case "f":
                                break;

                            default:
                                System.out.println("Invalid selection. Please re-enter: A, B, C, D, E, F");
                                break;
                        }
                    } while (!managegroups.equals("f"));

                    break;

                case "d":
                    String recipientMessage = getUserInput("Recipient of the message(group or person):");
                    // Empfänger der Nachricht

                    String imageOrText;

                    do {
                        System.out.println("Choose between:");
                        System.out.println("    A = Image message");
                        System.out.println("    B = Text message");
                        System.out.println("    C = Back to main menu");

                        imageOrText = getUserInput("Your choice:").toLowerCase();
                        switch (imageOrText) {
                            case "a":
                                String imageMessage = getUserInput("Enter path of the image:");
                                break;

                            case "b":
                                String textMessage = getUserInput("Content of the message:");
                                break;

                            case "c":
                                break;

                            default:
                                System.out.println("Invalid selection. Please re-enter: A, B, C");
                                break;
                        }
                    } while (!imageOrText.equals("c"));

                    break;

                case "e":
                    System.out.println("See you next time");
                    exitProgram = true;
                    break;

                default:
                    System.out.println("Invalid selection. Please re-enter: A, B, C, D, E");
                    break;
            }
        }
    }

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
