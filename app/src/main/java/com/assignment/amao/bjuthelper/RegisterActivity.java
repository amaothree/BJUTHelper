package com.assignment.amao.bjuthelper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameView;
    private EditText emailView;
    private EditText passwordView;
    private EditText phoneView;
    private Button regiButton;
    private View mProgressView;
    private View RegiFormView;
    private UserRegisterTask userRegisterTask = null;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this,"587cb0becf1d9c8a1fea192b63e98e32");
        context = this;

        usernameView = (EditText) findViewById(R.id.regi_username);
        emailView = (EditText) findViewById(R.id.regi_email);
        passwordView = (EditText) findViewById(R.id.regi_password);
        phoneView = (EditText) findViewById(R.id.regi_phone);
        regiButton = (Button) findViewById(R.id.regi_registerButton);
        RegiFormView = findViewById(R.id.regi_form);
        mProgressView = findViewById(R.id.regi_progress);

        Intent intent = getIntent();
        emailView.setText(intent.getStringExtra("email"));

        regiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

    }

    private void attemptRegister(){

        if (userRegisterTask != null) {
            return;
        }

        View focusView = null;
        boolean cancel = false;



        String name = usernameView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String phone = phoneView.getText().toString();

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            usernameView.setError(getString(R.string.error_field_required));
            focusView = usernameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)) {
            phoneView.setError(getString(R.string.error_field_required));
            focusView = phoneView;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            phoneView.setError("The length of number must is 11");
            focusView = phoneView;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        } else {
            showProgress(true);
            userRegisterTask = new UserRegisterTask(name,email,password,phone);
            userRegisterTask.execute((Void) null);
        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.length() == 11;
    }

    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            RegiFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            RegiFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    RegiFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            RegiFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String name;
        private final String email;
        private final String password;
        private final String phone;

        public UserRegisterTask(String name, String email, String password, String phone) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.phone = phone;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            final int[] auth = {0};

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }


            return true;
        }

        @Override
        protected void onPostExecute(Boolean i) {
            userRegisterTask = null;
            showProgress(false);

            User user = new User();
            user.setUsername(name);
            user.setEmail(email);
            user.setMobilePhoneNumber(phone);
            user.setPassword(password);
            user.signUp(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if(e==null){
                        Toast.makeText(context, "Register Success!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Log.i("BMOB","失败："+e.getMessage()+","+e.getErrorCode());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            });
        }


        @Override
        protected void onCancelled() {
            userRegisterTask = null;
            showProgress(false);
        }
    }
}
