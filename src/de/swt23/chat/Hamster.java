package de.swt23.chat;
import de.swt23.chat.message.MessageDirection;
import de.swt23.chat.manager.ChatManager;
import de.swt23.chat.message.Text;
import de.swt23.chat.receiver.Person;

public class Hamster {

    private ChatManager manager;

    /**
     * sends the message "v" to user "hamster32ws" for it to move forward
     */
    public void vor(){
        manager.sendMessage(new Text(new Person("hamster23ws"), MessageDirection.OUT, "v"));
    }

    /**
     * sends the message "l" to user "hamster32ws" for it to turn left
     */
    public void linksUm(){
        manager.sendMessage(new Text(new Person("hamster23ws"), MessageDirection.OUT, "l"));
    }
}
