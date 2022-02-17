// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB



package ca.codingcomrades.it.buscareplus.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ca.codingcomrades.it.buscareplus.LocalData;
import ca.codingcomrades.it.buscareplus.R;

public class MyAccount extends AppCompatActivity {
    EditText firstName,lastName,phone,age,address,city,province,country;
    Button save;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String uid,path;
    Integer count;
    LocalData data = new LocalData();
    ProgressBar progressBar;
    Map<String, Object> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "My Account onCreate: ");
        setContentView(R.layout.activity_my_account);
        // LoginActivity log =new LoginActivity();
        bindFields();
        retriveUserData();
        save.setOnClickListener(v -> saveUserData());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View view) {
                // save.setOnClickListener(v -> saveUserData());
                count =1;
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
                switch (view.getId()) {
                    case R.id.saveInfoBtn:
                        new MyTask().execute(100);
                        break;
                }
            }
        };
        save.setOnClickListener(listener);

    }


    @Override
    public void onResume() {

        super.onResume();
        applySettings();

    }

    public void applySettings(){
        SharedPreferences prefs = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        path = prefs.getString("accessPath","");
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

    public void bindFields(){
        save = findViewById(R.id.saveInfoBtn);
        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        phone = findViewById(R.id.phoneEditText);
        age = findViewById(R.id.ageEditText);
        address = findViewById(R.id.addressEditText);
        city = findViewById(R.id.cityEditText);
        province = findViewById(R.id.provinceEditText);
        country = findViewById(R.id.countryEditText);
        progressBar = findViewById(R.id.progressBar);
    }
    public void retriveUserData(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        uid = fAuth.getUid();
        DocumentReference df = fStore.collection("Users").document(uid);
        df.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
               try {
                   arr = value.getData();
                   Log.d("firebase", "onEvent: " + arr.get("LastName") + arr.values().toString() + "reg" + arr.values());
                   Log.d("firebase", "onEvent: " + firstName.getText().toString());
                   setFields();
               }catch (Exception e){
                   Log.d("TAG", "My account Exception: ");
               }
            }
        });

    }

    public void setFields(){
        firstName.setText(arr.get(getString(R.string.firebaseKeyFirstName)).toString());
        lastName.setText(arr.get(getString(R.string.firebaseKeyLastName)).toString());
        phone.setText(arr.get(getString(R.string.firebaseKeyPhone)).toString());
        age.setText(arr.get(getString(R.string.firebaseKeyAge)).toString());
        address.setText(arr.get(getString(R.string.firebaseKeyAddress)).toString());
        city.setText(arr.get(getString(R.string.firebaseKeyCity)).toString());
        province.setText(arr.get(getString(R.string.firebaseKeyProvince)).toString());
        country.setText(arr.get(getString(R.string.firebaseKeyCountry)).toString());
    }
    public void saveUserData(){
        DocumentReference df = fStore.collection("Users").document(uid);
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put((getString(R.string.firebaseKeyFirstName)),firstName.getText().toString());
        userInfo.put(getString(R.string.firebaseKeyLastName),lastName.getText().toString());
        userInfo.put(getString(R.string.firebaseKeyPhone), phone.getText().toString());
        userInfo.put(getString(R.string.firebaseKeyAge), age.getText().toString());
        userInfo.put(getString(R.string.firebaseKeyAddress), address.getText().toString());
        userInfo.put(getString(R.string.firebaseKeyCity), city.getText().toString());
        userInfo.put(getString(R.string.firebaseKeyProvince), province.getText().toString());
        userInfo.put(getString(R.string.firebaseKeyCountry), country.getText().toString());
        userInfo.put("isUser","1");
        userInfo.put(getString(R.string.path),path);
        df.set(userInfo);
        toastPrint(getString(R.string.my_acc_info_saved_msg));

    }
    public void toastPrint(String msg) {
        Log.d("TAG", "toastPrint: ");
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }
    class MyTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            for (; count <= params[0]; count++) {
                try {
                    Thread.sleep(1);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            saveUserData();
            finish();

        }
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }
}