package br.com.ossb.loginregister;

/**
 * Created by batista on 29/07/15.
 */
public class User {
    private String name, email, username, password;
    private String age;

    public User(){};

    public User(String name, String email, String age, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.name = "";
        this.email = "";
        this.age = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
