package de.swt23.chat;

import java.util.Scanner;

public class ChatProgram {
    private static final String SERVER_USERNAME = "olaf";
    private static final String SERVER_PASSWORD = "test";
    private static final Scanner scanner = new Scanner(System.in);

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

            if (username.equals(SERVER_USERNAME) && password.equals(SERVER_PASSWORD)) {
                System.out.println("Correct login details\n\n");
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
                    System.out.println("\nAusgabe Liste\n");
                    break;

                case "b":
                    System.out.println("List of all messages:");
                    System.out.println("\nAusgabe Liste\n");
                    break;

                case "c":
                    System.out.println("Manage groups:");

                    String managegroups;

                    do {
                        System.out.println("Choose between:");
                        System.out.println("    A = Create group");
                        System.out.println("    B = Delete group");
                        System.out.println("    C = Show group");
                        System.out.println("    D = Add users to group");
                        System.out.println("    E = Remove user from group");
                        System.out.println("    F = Back to main menu");

                        managegroups = getUserInput("Your choice:").toLowerCase();
                        switch (managegroups) {
                            case "a":
                                String createGroup = getUserInput("Enter group name");
                                break;

                            case "b":
                                String delteGroup = getUserInput("Enter group name");
                                break;

                            case "c":
                                System.out.println("\nAusgabe Liste Gruppe\n");
                                break;

                            case "d":
                                String addPersonGroup = getUserInput("Enter group name");
                                String addPerson = getUserInput("Enter name of the user");
                                break;

                            case "e":
                                String deltePersonGroup = getUserInput("Enter group name");
                                String deltePerson = getUserInput("Enter name of the user");
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
        try {
            requestUserLogin();
            handleProgramMenu();
        } finally {
            closeScanner();
        }
    }
}
