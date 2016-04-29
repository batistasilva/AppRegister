package br.com.ossb.loginregister;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by batista on 29/07/15.
 */
public class UserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;//For data local save in android file system

    public UserLocalStore(Context context) {
        this.userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        //
        spEditor.putString("name", user.getName());
        spEditor.putString("email", user.getEmail());
        spEditor.putInt("age", user.getAge());
        spEditor.putString("username", user.getUsername());
        spEditor.putString("password", user.getPassword());
        //
        spEditor.commit();
    }

    public User getLoggedInUser(){
        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email", "");
        int    age  = userLocalDatabase.getInt("age", -1);
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");

        User storeUser = new User(name, email, age, username, password);

        return storeUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedIn", false) == true){
            return true;
        }else{
            return false;
        }
    }

    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
