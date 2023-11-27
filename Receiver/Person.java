package receiver;

public class Person extends Receiver {

    private String username;

    public Person(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }
}
