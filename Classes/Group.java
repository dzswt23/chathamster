package Classes;
import java.util.ArrayList;

public class Group extends Receiver {

    private String groupName;
    private Arraylist<Person> members;

    public Group(String groupName){
        this.groupName = groupName;
        members = new ArrayList<Person>();
    }

    public String getGroupName(){
        return groupName;
    }

    public void addPerson(Person member){
        members.add(member);
    }
    public boolean removePerson(Person member){
        return members.remove(member);
    }

    public String getMembers(){
        for(int i = 0; i < members.size(); i++){
            System.out.println(members.get(i));
        }
    }

}
