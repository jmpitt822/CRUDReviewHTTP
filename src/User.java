import java.util.ArrayList;

/**
 * Created by jeremypitt on 9/26/16.
 */
public class User {
    String name;
    String password;
    ArrayList<Character> myCharacters = new ArrayList();
    boolean isMine;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
