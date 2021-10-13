package ca.codingcomrades.it.buscareplus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
String userEmail,userPassword;
Button login;
EditText email,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.LoginEmail);
        password = findViewById(R.id.LoginPassword);
        login = findViewById(R.id.Login_btn);
        login.setOnClickListener(v-> callHome());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }
    public void callHome(){
       if(validateName()){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
       }else{
           toastPrint("Uh oh Something went wrong!!, Try Again");
       }
    }
    public boolean validateName(){
        userEmail = email.getText().toString().trim();
        userPassword = password.getText().toString();
        boolean validate = true;
        if(userPassword.length()<5){
            password.setError(getString(R.string.userPass_5_error));
            validate = false;
        }else if (!(userEmail.contains("@"))){
            validate = false;
            email.setError(getString(R.string.invalid_email_error));
        }else if (userEmail.isEmpty()){
            validate = false;
            email.setError(getString(R.string.empty_email_error));
        }else if(userPassword.isEmpty()){
            validate = false;
            password.setError(getString(R.string.userPass_empty_error));
        }
        return validate;
    }
    private void toastPrint(String msg) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }
}