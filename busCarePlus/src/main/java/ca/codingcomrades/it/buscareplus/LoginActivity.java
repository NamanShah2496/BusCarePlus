// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB
//more better way in youtube watch later

package ca.codingcomrades.it.buscareplus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    LocalData data = new LocalData();
    String userEmail,userPassword;
    Button login;
    Boolean remember = false;
    CheckBox rememberMe;
    EditText emailField, passwordField;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordPattern ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    int errorCode = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        database = FirebaseDatabase.getInstance();
        fStore =FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        rememberMe = findViewById(R.id.RememberMeCheckBox);
        emailField = findViewById(R.id.LoginEmail);
        passwordField = findViewById(R.id.LoginPassword);
        Log.d("TAG", "onCreate: "+ fAuth.getUid());
        login = findViewById(R.id.Login_btn);
        login.setOnClickListener(v-> callHome());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        emailField.setText("");
        passwordField.setText("");
        applySettings();
    }
    public void callHome(){
        userEmail = emailField.getText().toString().trim();
        userPassword = passwordField.getText().toString();
       if(validateName(userEmail,userPassword)){

           fAuth.signInWithEmailAndPassword(emailField.getText().toString(), passwordField.getText().toString()).addOnSuccessListener(authResult -> { // Firebase authentication
               savePreference();
               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(intent);
           }).addOnFailureListener(e -> toastPrint(getString(R.string.toast_login_fail))).addOnSuccessListener(e -> toastPrint(getString(R.string.toast_login_success)));
       }else{
           toastPrint(getString(R.string.firebase_login_fail));
       }
    }

    public void savePreference(){
        remember = rememberMe.isChecked();
        LocalData data = new LocalData();
        data.savePreferences(this,getString(R.string.remember),remember);
    }

   public  boolean validateName(String Email, String Password){

  toastPrint(getString(R.string.validating_msg));
        String userEmail = Email;
        String userPassword = Password;
        boolean validate = true;
        if (!validator(userEmail,userPassword) && errorCode==1 ) {
            validate = false;
            emailField.setError(getString(R.string.empty_email_error));
        }
        if (!validator(userEmail,userPassword) && errorCode==2 ){
            validate = false;
            emailField.setError(getString(R.string.invalid_email_error));
        }
        if(!validator(userEmail,userPassword) && errorCode==3){
            validate = false;
            passwordField.setError("Invalid Password");
        }
        return validate;
    }
    public boolean validator(String Email, String Password){
        String userEmail = Email;
        String userPassword = Password;
        boolean validate = true;
        if (userEmail.isEmpty()) {
            errorCode =1;
         return false;
        }
        if (!(userEmail.matches(emailPattern))){
            errorCode =2;
            return false;
        }
        if(!(userPassword.matches(passwordPattern))){
            errorCode =3;
            return false;
        }
        return validate;

    }
    public void displayError(){

    }
    public void toastPrint(String msg) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    public boolean isRemember(){
        Boolean rem;

        rem = data.getPreference(this,"remember");

        return rem;
    }
    @Override
    protected void onStart(){
        super.onStart();
        remember = isRemember();

        if((FirebaseAuth.getInstance().getCurrentUser() != null) && (remember)){
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            Log.d("TAG", "onStart: Check");
            Log.d("TAG", "onStart: " + firebaseAuth.getUid());
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
       public void applySettings(){
        String port = data.getPreference(this,"port",1);
        String ds = data.getPreference(this,"ds",1);

        if (port.equalsIgnoreCase("true")) {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
        if (ds.equalsIgnoreCase("true")) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}