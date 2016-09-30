import com.sun.org.apache.xpath.internal.operations.Mod;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jeremypitt on 9/26/16.
 */
public class Main {

    static HashMap<String, User> users = new HashMap<>();
//    static HashMap<String, Character> characters = new HashMap<>();
    static ArrayList<Character> characters = new ArrayList<>();
    static int charId = 1;


    public static void main(String[] args) {
        Spark.init();
        Spark.get("/",
                ((request, response) -> {
                    String charId = request.queryParams("charId");

                    HashMap m = new HashMap();

                    Session session = request.session();
                    String userName = session.attribute("userName");

                    for (Character c: characters){
                        if (c.userName.equals(userName)){
                            c.author = "notNull";
                        } else {
                            c.author = null;
                        }
                    }

                    m.put("userName", userName);
                    m.put("charId", charId);
                    m.put("characters", characters);
                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );

        Spark.post("/login",
                ((request, response) -> {
                    String userName = request.queryParams("loginName");
                    String password = request.queryParams("password");
                    if (userName == null){
                        throw new Exception("Login name not found");
                    }

                    User user = users.get(userName);
                    if (user == null){
                        user = new User(userName, password);
                        users.put(userName, user);
                    }
                    if (! users.get(userName).password.equals(password)) {
                        response.redirect("");
                        return "";
                    }
                        Session session = request.session();
                        session.attribute("userName", userName);

                    response.redirect("/");
                    return "";
                })
        );

        Spark.post("/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post("/create-character",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");

                    String charName = request.queryParams("charName");
                    String charTitle = request.queryParams("charTitle");
                    String charGenre = request.queryParams("charGenre");
                    String charMedium = request.queryParams("charMedium");

                    if (charName == null || charTitle == null || charGenre == null || charMedium == null){
                        throw new Exception("Please don't leave any fields blank.");
                    }
                    Character character = new Character(charId, charName, charTitle, charGenre, charMedium, userName);

                    characters.add(character);

                    charId++;

                    response.redirect("/");
                    return "";
                })
                );

        Spark.post(
                "/delete-character",
                ((request, response) -> {
                    int deleteCharacter = Integer.parseInt(request.queryParams("deleteChar"));
                    Character toRemove = new Character();
                    for (Character c: characters){
                        if (c.id == deleteCharacter){
                            toRemove = c;
                        }
                    }
                    characters.remove(toRemove);

                    response.redirect("/");
                    return "";
                })
        );

        Spark.get(
                "/edit-item",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    int id = Integer.parseInt(request.queryParams("editChar"));
                    session.attribute("id", id);
                    Character characterEd = new Character();
                    for (Character c : characters){
                        if (c.userName.equals(userName)){
                            c.author = "notNull";
                        }else{
                            c.author = null;
                        }
                        if (c.id == id){
                            characterEd = c;
                        }
                    }
                    m.put("character", characterEd.id);
                    return new ModelAndView(m, "edit-item.html");

                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "edit-item",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    int id= Integer.parseInt(request.queryParams("editChar"));
                    String charName = request.queryParams("charName");
                    String charTitle = request.queryParams("charTitle");
                    String charGenre = request.queryParams("charGenre");
                    String charMedium = request.queryParams("charMedium");
                    if (charName == null || charTitle == null || charGenre == null || charMedium == null){
                        throw new Exception("Please don't leave any fields blank.");
                    }
                    Character character = new Character(id, charName, charTitle, charGenre, charMedium, userName);
                    characters.set(id, character);

                    response.redirect("/");
                    return "";

                })
        );


    }

}
