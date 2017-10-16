package com.example.kaveon14.workoutbuddy.Activities;
// TODO  if no network connection just go skip this
// TODO use webview database to check for shit
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.kaveon14.workoutbuddy.RemoteDatabase.WorkoutBuddyAPI;
import com.example.kaveon14.workoutbuddy.RemoteDatabase.RequestHandler;
import com.example.kaveon14.workoutbuddy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.kaveon14.workoutbuddy.RemoteDatabase.WorkoutBuddyAPI.CODE_GET_REQUEST;
import static com.example.kaveon14.workoutbuddy.RemoteDatabase.WorkoutBuddyAPI.CODE_POST_REQUEST;

// IF GOOD OPEN ACTIVITY IF NO
public class Login extends AppCompatActivity  {//replace with one big ass web view

    List<String> list;

    WebView webView;


    ListView listView;

    public void readDe() {//it finally fucking works
        String username = "kaveon1";



        String u = WorkoutBuddyAPI.LOGIN_URL+username;
        PerformNetworkRequest request = new PerformNetworkRequest(u,null,CODE_GET_REQUEST);
        request.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // Set up the login form.


        listView = (ListView) findViewById(R.id.testList);

        list = new ArrayList<>(20);
        readDe();
        list.size();
        System.out.println("LIST: "+list.size());




    }

   /* @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Login.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.


                // Simulate network access.
               // Thread.sleep(2000);

            String link = "http://127.0.0.1/WorkoutBuddy_Scripts/Login.php?email="+mEmail+"& password="+mPassword;
            try {
               URL url = new URL(link);
                URLConnection connection = url.openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
                InputStream stream =  httpURLConnection.getInputStream();

                BufferedReader reader = new BufferedReader( new InputStreamReader(stream));
                String line = "";

                String result = "";
                while((line = reader.readLine()) != null) {
                    result += line;
                }
                System.out.println(result);
                stream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }



            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                finish();
                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            } else {

            }
        }

        @Override
        protected void onCancelled() {

        }
    }

    private void refreshList(JSONArray exercise) throws JSONException {
        list.clear();

        for(int x=0;x<exercise.length();x++) {
            JSONObject object = exercise.getJSONObject(x);

            list.add(object.getString("id"));
            System.out.println(list.get(x));
        }

    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;
        URL u;
        HttpURLConnection conn;
        //the request code to define whether it is a GET or POST
        int requestCode;
        InputStream stream;







        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("THIS: "+s);
            //progressBar.setVisibility(GONE);
            try {

               /* BufferedReader r = new BufferedReader(new InputStreamReader(stream));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    result.append(line);
                }*/

                JSONObject object = new JSONObject(s);


               // refreshList(object.getJSONArray("id"));
                if (!object.getBoolean("error")) {//else wrong username or password
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshList(object.getJSONArray("id"));
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.simple_list_item,list);
                    listView.setAdapter(adapter);
                } else {
                    String error_message = object.getString("message");
                    Toast.makeText(getApplicationContext(),error_message,Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {

            try {
                System.out.println(url);
                u = new URL(url);
                conn = (HttpURLConnection) u.openConnection();
                stream = conn.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }


            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
}

