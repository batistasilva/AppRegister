package br.com.ossb.loginregister;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register extends ActionBarActivity implements View.OnClickListener{

    Button btRegister;
    EditText etName, etUsername, etAge, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName    = (EditText) findViewById(R.id.etName);
        etUsername= (EditText) findViewById(R.id.etUsername);
        etAge     = (EditText) findViewById(R.id.etAge);
        etPassword= (EditText) findViewById(R.id.etPassword);

        btRegister = (Button) findViewById(R.id.btRegister);
        btRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btRegister:
                String name = etName.getText().toString();
                int    age  = Integer.parseInt(etAge.getText().toString());
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User(name, age, username, password);

                registerUser(user);

                break;
        }
    }

    private void registerUser(User user){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User retornedUser) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }
}
