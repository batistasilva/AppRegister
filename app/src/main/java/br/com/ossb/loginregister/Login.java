package br.com.ossb.loginregister;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.ossb.util.Security;


public class Login extends ActionBarActivity  implements View.OnClickListener {

    Button btLogin;
    EditText etUsername, etPassword;
    TextView tvRegisterLink;
    UserLocalStore userLocalStore;
    Security sec;
    public static final String STRING_KEY = "1234567891234567";
    private static final String LOG_TAG_DEBUG = "SERVER REQUEST";
    private static final String LOG_TAG_RESULT = "SERVER REQUEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin    = (Button) findViewById(R.id.btLogin);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        btLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btLogin:
                sec = new Security();
                //
                String username = sec.encrypt(etUsername.getText().toString(), STRING_KEY);
                String password = sec.encrypt(etPassword.getText().toString(), STRING_KEY);
                //
                User user = new User(username, password);

                authenticate(user);

               // userLocalStore.storeUserData(user);
               // userLocalStore.setUserLoggedIn(true);

                break;
            case R.id.tvRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }

    private void authenticate(User user){
      ServerRequests serverRequests = new ServerRequests(this);
      serverRequests.fetchUserDataInBackground(user, new GetUserCallback() {
          @Override
          public void done(User retornedUser) {
              if(retornedUser == null){
                  showErrorMessage();
              }else{
                  logUserIn(retornedUser);
                  showMessage();
              }
          }

      });
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Incorrect User Details......:(");
        dialogBuilder.setPositiveButton("ok", null);
        dialogBuilder.show();
    }


    private void showMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("User Loged Sucessfuly......:)");
        dialogBuilder.setPositiveButton("ok", null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser){
        Log.i(LOG_TAG_RESULT, "Name....:" + returnedUser.getName() + "Email....:" + returnedUser.getEmail() + "Username....: " + returnedUser.getUsername() + "Password....: " + returnedUser.getPassword());
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(this, MainActivity.class));
    }
}
