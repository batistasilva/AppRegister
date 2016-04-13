package br.com.ossb.loginregister;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by batista on 29/07/15.
 */
public class ServerRequests {
    ProgressDialog progressDialog;
    private static final String LOG_TAG_DEBUG = "SERVER REQUEST";
    private static final String LOG_TAG_RESULT = "SERVER REQUEST";
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://192.168.0.102";

    public ServerRequests(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallback){
      progressDialog.show();
      new StoreUserDataAsyncTask(user, userCallback).execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallback Callback){
        progressDialog.show();
        new fetchUserDataAsyncTask(user, Callback).execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void>{
        User user;
        GetUserCallback userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallback){
           this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String link = SERVER_ADDRESS + "/AndroidPhp/Register.php";
                //
                String data = URLEncoder.encode("name", "UTF-8")
                        + "=" + URLEncoder.encode(user.name, "UTF-8");

                data += "&" + URLEncoder.encode("age", "UTF-8")
                        + "=" + user.age +"";

                data += "&" + URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(user.username, "UTF-8");
                //
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(user.password, "UTF-8");

                //Setting link to url to it will be load
                URL url = new URL(link);
                //Load url opened connection
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                //

                Log.i(LOG_TAG_RESULT, "Writing....: " + data);

                wr.write(data);
                wr.flush();
                //
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                String result = sb.toString();

                Log.i(LOG_TAG_RESULT, result);

              //  JSONObject jsonObject = new JSONObject(result);


            } catch (Exception e) {
                Log.e(LOG_TAG_DEBUG, e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallback;

        public fetchUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected User doInBackground(Void... params) {

            User returnedUser = null;

            try {
                String link = SERVER_ADDRESS + "/AndroidPhp/FetchUserData.php";
                //
                String data = URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(user.username, "UTF-8");
                //
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(user.password, "UTF-8");

                //Setting link to url to it will be load
                URL url = new URL(link);
                //Load url opened connection
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                //
                wr.write(data);
                wr.flush();
                //
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                String result = sb.toString();

                Log.i(LOG_TAG_RESULT, result);

                JSONObject jsonObject = new JSONObject(result);

                if(jsonObject.length() == 0){
                    returnedUser = null;
                }else{
                    String name = jsonObject.getString("name");
                    int    age  = jsonObject.getInt("age");

                    returnedUser = new User(name, age, user.username, user.password);
                }

            } catch (Exception e) {
                Log.e(LOG_TAG_DEBUG, e.getMessage());
            }


            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }

}
