/**
 * Created by jeremypitt on 9/26/16.
 */
public class Character {
    int id;
    String name;
    String title;
    String genre;
    String medium;
    String userName;
    String author;


    public Character(int id, String name, String title, String genre, String medium, String userName) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.genre = genre;
        this.medium = medium;
        this.userName = userName;
//        this.author = author;
    }

    public Character() {
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", id, name, title, genre, medium);
    }
}
