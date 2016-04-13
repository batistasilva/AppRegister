package br.com.ossb.loginregister;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button btLogout;
    EditText etName, etUsername, etAge;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etAge = (EditText) findViewById(R.id.etAge);
        btLogout = (Button) findViewById(R.id.btLogout);

        btLogout.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate() == true){
            displayUserDatails();
            Log.e("MainActivity", "User Logged!!!");
        }else {
            startActivity(new Intent(MainActivity.this, Login.class));
            Log.e("MainActivity", "User Not Logged!!!");
        }
    }


    public void displayUserDatails(){
        User user = userLocalStore.getLoggedInUser();
      // if(!user.equals(null)) {
           if(!user.name.isEmpty()) {
               etName.setText(user.name);
               etUsername.setText(user.username);
               etAge.setText(Integer.toString(user.age));
           }
       //}else{
       //    clearUserLocalStore();
      // }
    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btLogout:
                clearUserLocalStore();
                break;
        }
    }

    private void clearUserLocalStore(){
        userLocalStore.clearUserData();
        userLocalStore.setUserLoggedIn(false);
        startActivity(new Intent(this, Login.class));
    }
}
