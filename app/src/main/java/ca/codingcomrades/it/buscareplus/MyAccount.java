// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB



package ca.codingcomrades.it.buscareplus;

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

public class MyAccount extends AppCompatActivity {
EditText firstName,lastName,phone,age,address,city,province,country;
Button save;
FirebaseAuth fAuth;
String cityName,uid;
    Integer count;
    ProgressBar progressBar;
    Map<String, Object> arr;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_account);
        LoginActivity log =new LoginActivity();
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

        SharedPreferences prefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
        String port = prefs.getString("port", "false");
        String ds = prefs.getString("ds", "false");
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
                arr = value.getData();
                Log.d("TAG", "onEvent: " + arr.get("LastName")+arr.values().toString() + "reg" +arr.values());
                setFields();

            }
        });

    }

    public void setFields(){
        firstName.setText(arr.get(getString(R.string.firstnameTitle)).toString());
        lastName.setText(arr.get(getString(R.string.last_nameTitle)).toString());
        phone.setText(arr.get(getString(R.string.phoneTitle)).toString());
        age.setText(arr.get(getString(R.string.ageTitle)).toString());
        address.setText(arr.get(getString(R.string.addressTitle)).toString());
       // city.setText("Edmonton");
       city.setText(arr.get(getString(R.string.cityTitle)).toString());
        province.setText(arr.get(getString(R.string.provinceTitle)).toString());
        country.setText(arr.get(getString(R.string.countryTitle)).toString());
    }
    public void saveUserData(){
        DocumentReference df = fStore.collection("Users").document(uid);
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put((getString(R.string.firstnameTitle)),firstName.getText().toString());
        userInfo.put(getString(R.string.last_nameTitle),lastName.getText().toString());
        userInfo.put(getString(R.string.phoneTitle), phone.getText().toString());
        userInfo.put(getString(R.string.ageTitle), age.getText().toString());
        userInfo.put(getString(R.string.addressTitle), address.getText().toString());
        userInfo.put(getString(R.string.cityTitle), city.getText().toString());
        userInfo.put(getString(R.string.provinceTitle), province.getText().toString());
        userInfo.put(getString(R.string.countryTitle), country.getText().toString());
        userInfo.put("isUser","1");
        df.set(userInfo);
        toastPrint("Information Saved!!");

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