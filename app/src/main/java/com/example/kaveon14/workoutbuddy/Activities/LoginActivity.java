package com.example.kaveon14.workoutbuddy.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import com.example.kaveon14.workoutbuddy.R;
import com.example.kaveon14.workoutbuddy.RemoteDatabase.RequestHandler;
import com.example.kaveon14.workoutbuddy.RemoteDatabase.WorkoutBuddyAPI;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private AutoCompleteTextView username_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        username_textView = (AutoCompleteTextView) findViewById(R.id.username_textView);
        setSignInButton();
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
        UserLoginTask userLoginTask = new UserLoginTask(username);
        userLoginTask.execute();
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

        UserLoginTask(String username) {
            this.username = username;
        }

        @Override
        protected String doInBackground(Void... params) {
            String username_url = WorkoutBuddyAPI.LOGIN_URL+username;

            return new RequestHandler().sendGetRequest(username_url);
        }

        @Override
        protected void onPostExecute(String s) {
            showProgress(true);
            attemptLogin(s);
        }

        private void attemptLogin(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                if(!jsonObject.getBoolean(WorkoutBuddyAPI.JSON_ERROR)) {
                    Toast.makeText(getApplicationContext(), jsonObject.getString(WorkoutBuddyAPI.JSON_ERROR_MESSAGE),
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    //login
                } else {
                    Toast.makeText(getApplicationContext(), jsonObject.getString(WorkoutBuddyAPI.JSON_ERROR_MESSAGE),
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

