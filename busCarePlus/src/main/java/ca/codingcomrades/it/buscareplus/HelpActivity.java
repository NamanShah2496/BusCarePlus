// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

public class HelpActivity extends AppCompatActivity {

//Don't Repeat yourself Principle
    public static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = findViewById(R.id.call_fab);
        fab.setOnClickListener(view -> insertDummyContactWrapper());

    }

    @Override
    public void onResume() {

        super.onResume();

        SharedPreferences prefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
        String port = prefs.getString("port","false");
        String ds = prefs.getString("ds","false");
        if(port.equalsIgnoreCase("true")){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
        if(ds.equalsIgnoreCase("true")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void insertDummyContactWrapper() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            String phone = getString(R.string.contact_number);
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone, null));
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},  REQUEST_PHONE_CALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ConstraintLayout text = findViewById(R.id.constraintLayout);
        Snackbar snackbar;
        if (requestCode == REQUEST_PHONE_CALL && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            snackbar = Snackbar
                    .make(text, R.string.pg, Snackbar.LENGTH_LONG);
        } else  {
            snackbar = Snackbar
                    .make(text, R.string.pd, Snackbar.LENGTH_LONG);
        }
        snackbar.show();
    }
}