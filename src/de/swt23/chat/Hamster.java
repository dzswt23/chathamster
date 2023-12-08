package de.swt23.chat;

import de.swt23.chat.message.MessageDirection;
import de.swt23.chat.manager.ChatManager;
import de.swt23.chat.message.Text;
import de.swt23.chat.receiver.Person;

public class Hamster {

    private ChatManager manager;
    private Person person;
    private Direction hamsterDirection;
    private int Blickrichtung;
    private int turns;

    public Hamster(ChatManager manager) {
        this.manager = manager;
        person = new Person("hamster23ws");
        manager.sendMessage(new Text(person, MessageDirection.OUT, "init"));
        this.hamsterDirection = Direction.EAST;
    }

    /**
     * sends the message "v" to user "hamster32ws" for it to move forward
     */
    public void vor() {
        manager.sendMessage(new Text(person, MessageDirection.OUT, "v"));
    }

    /**
     * sends the message "l" to user "hamster32ws" for it to turn left
     */
    public void linksUm() {
        manager.sendMessage(new Text(person, MessageDirection.OUT, "l"));
        switch (hamsterDirection) {
            case NORTH:
                hamsterDirection = Direction.WEST;
                break;
            case EAST:
                hamsterDirection = Direction.NORTH;
                break;
            case SOUTH:
                hamsterDirection = Direction.EAST;
                break;
            case WEST:
                hamsterDirection = Direction.SOUTH;
                break;
            default:
                System.out.println("klappt nicht!!!!!");
                break;
        }
    }

    public void setBlickrichtung(Direction direction){
        while(direction != hamsterDirection){
            linksUm();
        }
    }
}
