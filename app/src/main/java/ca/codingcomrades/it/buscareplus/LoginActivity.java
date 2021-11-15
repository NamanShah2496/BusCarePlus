// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB


package ca.codingcomrades.it.buscareplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseFirestore fStore;
    DatabaseReference myRef;
    FirebaseAuth fAuth;

    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "sharedPrefs";
    String userEmail,userPassword;
    Button login;
    Boolean remember = false;
    CheckBox rememberMe;
    EditText email,password;
    ProgressDialog progressDialog;
    String txt;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        LocalData local = new LocalData();
        database = FirebaseDatabase.getInstance();
        myRef= database.getReference("Safety/Speed");

       // toastPrint(myRef.getDatabase().toString());
        fStore =FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        fStore =FirebaseFirestore.getInstance();
        rememberMe = findViewById(R.id.RememberMeCheckBox);
        email = findViewById(R.id.LoginEmail);
        password = findViewById(R.id.LoginPassword);

        login = findViewById(R.id.Login_btn);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);

        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask myAsyncTasks = new MyTask();
                myAsyncTasks.execute();
            }
        });

    }


    @Override
    protected void onResume(){
        super.onResume();
        email.setText("");
        password.setText("");
    }
    class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {

          if(validateName()){

                fAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        savePreference();
                        txt = "Success";
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        txt = "Failure,try again";
                    }
                });

          }else{
                Log.d("TAG","Uh oh Something went wrong!!, Try Again");
            }

            return txt;
        }
        public boolean isRemember(){
            Boolean rem;
            LocalData data = new LocalData();
            rem = data.getPreference(getApplicationContext(),"remember");
            Log.d("TAG", "isRemember: Rem"+ rem);
            return rem;
        }
        protected void onStart(){
            remember = isRemember();
            Log.d("TAG", "onStart: "+ remember);
            if((FirebaseAuth.getInstance().getCurrentUser() != null) && (remember)){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        }
        public void savePreference(){
            remember = rememberMe.isChecked();
            LocalData data = new LocalData();
            data.savePreferences(getApplicationContext(),"remember",remember);
        }

        private boolean validateName() {
            //toastPrint("Validating");
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

        @Override
        protected void onPostExecute(String result) {
            if(result == "Failure,try again"){
                progressDialog.hide();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                //toastPrint("success");
            }
            else{
                progressDialog.hide();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //toastPrint("failure, try again");
            }
        }

    }
}