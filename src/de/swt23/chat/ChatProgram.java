
package de.swt23.chat;
import java.util.Scanner;
import java.util.InputMismatchException;

public class ChatProgram {

    /**
     * user input for username and password
     */
    public static void useinputLoggin() {
        System.out.println("*Welcome to the chat program of group H!*\n");

        boolean loginData = true;
        while(loginData) {
            //Test Bedingung
            String test1 = "olaf";
            String test2 = "test";

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter your username:");
            String username = scanner.nextLine();
            System.out.println("Enter your password:");
            String password = scanner.nextLine();

            if(username.equals(test1) /*"ServerBenutzername"*/ && password.equals(test2) /*"ServerPasswort"*/){
                System.out.println("Correct login details\n\n");
                loginData = false;
            }else{
                System.out.println("Incorrect login details\n\n");
            }
        }
    }

    public static void programMenu(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("*Program menu*\n");
        System.out.println("Choose between:");
        System.out.println("    A = List of all users");
        System.out.println("    B = List of all messages");
        System.out.println("    C = Manage groups");
        System.out.println("    D = Send message");
        System.out.println("    E = Exit prgram menu");
        String letter = scanner.nextLine();

        boolean validSelection = false;

        while (!validSelection) {
            switch (letter) {
                case "A","a":
                    System.out.println("\nAusgabe Liste\n");
                    end();
                    break;

                case "B","b":
                    System.out.println("\nAusgabe Liste\n");
                    end();
                    break;

                case "C","c":
                    System.out.println("\nAusgabe Liste\n");
                    end();
                    break;

                case "D","d":
                    System.out.println("\nAusgabe Liste\n");
                    end();
                    break;

                case "E","e":
                    System.out.println("\nAusgabe Liste\n");
                    end();
                    break;

                default:
                    System.out.println("Invalid selection. Please re-enter: A, B, C, D, E, F");
                    letter = scanner.nextLine();
                    break;
            }
        }
    }

    public static void end() {
        Scanner scanner = new Scanner(System.in);
        boolean cancel = false;
        System.out.println("Press `J` to cancel");
        String in = scanner.nextLine();

        while (!cancel) {
            switch (in) {
                case "J", "j":
                    cancel=true;
                    programMenu();
                    break;
                default:
                    System.out.println("Invalid selection. Please re-enter: J");
                    in = scanner.nextLine();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        useinputLoggin();
        programMenu();
    }
}