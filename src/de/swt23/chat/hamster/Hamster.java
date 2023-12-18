package de.swt23.chat.hamster;

import de.swt23.chat.manager.ChatManager;
import de.swt23.chat.message.MessageDirection;
import de.swt23.chat.message.Text;
import de.swt23.chat.receiver.Person;

public class Hamster {

    private final ChatManager manager;
    private final Person person;
    private HamsterDirection hamsterDirection;

    private int reihe = 0;
    private int spalte = 0;

    public Hamster(ChatManager manager) {
        this.manager = manager;
        hamsterDirection = HamsterDirection.EAST;
        person = new Person("hamster23ws");
        manager.sendMessage(new Text(person, MessageDirection.OUT, "init"));
    }

    /**
     * sends the message "v" to user "hamster23ws" for it to move forward
     */
    public void vor() {
        manager.sendMessage(new Text(person, MessageDirection.OUT, "v"));
        switch (hamsterDirection) {
            case SOUTH:
                reihe++;
                break;
            case NORTH:
                reihe--;
                break;
            case EAST:
                spalte++;
                break;
            case WEST:
                spalte--;
                break;
            default:
                System.out.println("Error whilst navigating hamster");
                break;
        }

    }

    /**
     * sends the message "l" to user "hamster23ws" for it to turn left
     */
    public void linksUm() {
        manager.sendMessage(new Text(person, MessageDirection.OUT, "l"));
        switch (hamsterDirection) {
            case NORTH:
                hamsterDirection = HamsterDirection.WEST;
                break;
            case EAST:
                hamsterDirection = HamsterDirection.NORTH;
                break;
            case SOUTH:
                hamsterDirection = HamsterDirection.EAST;
                break;
            case WEST:
                hamsterDirection = HamsterDirection.SOUTH;
                break;
            default:
                System.out.println("Error whilst navigating hamster");
                break;
        }
    }

    public void setBlickrichtung(HamsterDirection direction) {
        while (direction != hamsterDirection) {
            linksUm();
        }
    }

    public int getReihe() {
        return reihe;
    }

    public int getSpalte() {
        return spalte;
    }
}