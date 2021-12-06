// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB
//more better way in youtube watch later

package ca.codingcomrades.it.buscareplus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
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

    String userEmail,userPassword;
    Button login;
    Boolean remember = false;
    CheckBox rememberMe;
    EditText email,password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        database = FirebaseDatabase.getInstance();
        fStore =FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        rememberMe = findViewById(R.id.RememberMeCheckBox);
        email = findViewById(R.id.LoginEmail);
        password = findViewById(R.id.LoginPassword);
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
        email.setText("");
        password.setText("");
    }
    public void callHome(){
       if(validateName()){

           fAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(authResult -> { // Firebase authentication
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
    public boolean validateName(){
        toastPrint(getString(R.string.validating_msg));
        userEmail = email.getText().toString().trim();
        userPassword = password.getText().toString();
        boolean validate = true;
        if (userEmail.isEmpty()) {
            validate = false;
            email.setError(getString(R.string.empty_email_error));
        }else if (!(userEmail.matches(emailPattern))){
            validate = false;
            email.setError(getString(R.string.invalid_email_error));
        }else  if(userPassword.length()<5){
        password.setError(getString(R.string.userPass_5_error));
        validate = false;
        }else if(userPassword.isEmpty()){
            validate = false;
            password.setError(getString(R.string.userPass_empty_error));
        }
        return validate;
    }
    public void toastPrint(String msg) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    public boolean isRemember(){
        Boolean rem;
        LocalData data = new LocalData();
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
}