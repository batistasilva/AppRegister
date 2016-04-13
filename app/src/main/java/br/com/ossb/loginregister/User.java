package br.com.ossb.loginregister;

/**
 * Created by batista on 29/07/15.
 */
public class User {
    String name, username, password;
    int age;

    public User(String name, int age, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.name = "";
        this.age = -1;
    }

}
