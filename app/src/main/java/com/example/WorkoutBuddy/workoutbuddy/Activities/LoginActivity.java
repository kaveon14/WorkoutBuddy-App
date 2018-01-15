package com.example.WorkoutBuddy.workoutbuddy.Activities;
// TODO login in only once, maybe once per month, store userId and shit in database,skip this activity
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.WorkoutBuddy.workoutbuddy.R;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.RequestHandlers.RequestHandler;
import com.example.WorkoutBuddy.workoutbuddy.RemoteDatabase.Api.CoreAPI;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView username_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        username_textView = (AutoCompleteTextView) findViewById(R.id.username_textView);

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            setSignInButton();
        } else {
            Toast.makeText(this,"No network connection!",Toast.LENGTH_LONG).show();
        }
        setSkipSignInButton();


    }

    private void setSignInButton() {
        Button btn = (Button) findViewById(R.id.signInBtn);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        username_textView.setError(null);

        // Store values at the time of the login attempt.
        String username = username_textView.getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        UserLoginTask userLoginTask = new UserLoginTask(username,password);
        userLoginTask.execute();
    }

    private void setSkipSignInButton() {
        Button btn = (Button) findViewById(R.id.skipSignInBtn);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);
                skipSignIn();
            }
        });
    }

    private void skipSignIn() {
        //make sure to not load any personal data this way
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        View loginFormView = findViewById(R.id.login_form);
        View progressView = findViewById(R.id.login_progress);

        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class UserLoginTask extends AsyncTask<Void, Void, String> {

        private String username;
        private String password;

        UserLoginTask(String username,String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            HashMap<String,String> map = new HashMap<>();
            map.put("username",username);
            map.put("password",password);
            return new RequestHandler().sendPostRequest(CoreAPI.LOGIN_URL,map);
        }

        @Override
        protected void onPostExecute(String s) {
            showProgress(true);
            attemptLogin(s);
        }

        private void attemptLogin(String s) {
            try {//make sure to get the user id
                JSONObject jsonObject = new JSONObject(s);

                if(!jsonObject.getBoolean(CoreAPI.JSON_ERROR)) {
                    Toast.makeText(getApplicationContext(), jsonObject.getString(CoreAPI.JSON_ERROR_MESSAGE),
                            Toast.LENGTH_SHORT).show();
                    int id = jsonObject.getInt("id");
                    CoreAPI.setUserId(id);

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    //login
                } else {
                    Toast.makeText(getApplicationContext(), jsonObject.getString(CoreAPI.JSON_ERROR_MESSAGE),
                            Toast.LENGTH_SHORT).show();
                    showProgress(false);
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
            showProgress(false);
        }
    }
}

