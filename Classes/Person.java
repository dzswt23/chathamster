package Classes;

public class Person extends Receiver {

    private String username;
    private String password;

    public Person(String username, String password){
        this.password = password;
        this.username = username;
    }

    public String getUsername(){
        return username;
    }
}
