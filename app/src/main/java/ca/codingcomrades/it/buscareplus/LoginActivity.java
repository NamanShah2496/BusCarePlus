package ca.codingcomrades.it.buscareplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseFirestore fStore;
    DatabaseReference myRef;
    FirebaseAuth fAuth;

    String userEmail,userPassword;
    Button login;

    EditText email,password;
    TextView forgotPass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        database = FirebaseDatabase.getInstance();
        myRef= database.getReference("Safety/Speed");
        toastPrint("ran");
        myRef.setValue("12Km/h");
        fStore =FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        DocumentReference df = fStore.collection("Users").document("mSZ8gSOQN2MoE8I1ibiU0F2UF1A3");
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("FullName","Vishesh");
        userInfo.put("Phone", "94459945648");
        userInfo.put("isUser","1");
        df.set(userInfo);
        toastPrint(df.get().toString());
        Log.d("TAG",df.get().toString() );

        email = findViewById(R.id.LoginEmail);
        password = findViewById(R.id.LoginPassword);
        forgotPass = findViewById(R.id.Forgot_Password_Title);


        forgotPass.setOnClickListener(v->toastPrint("Feature Coming soon"));
        login = findViewById(R.id.Login_btn);
        login.setOnClickListener(v-> callHome());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);

        }
    }

    public void callHome(){
       if(validateName()){
           fAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
               @Override
               public void onSuccess(AuthResult authResult) {
                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   startActivity(intent);
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   toastPrint("Failure, try again");
               }
           });
       }else{
           toastPrint("Uh oh Something went wrong!!, Try Again");
       }
    }

    public boolean validateName(){
        toastPrint("Validating");
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
        Log.d("TAG", "toastPrint: ");
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }


}