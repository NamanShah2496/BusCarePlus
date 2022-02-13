// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus.menu;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import ca.codingcomrades.it.buscareplus.LocalData;
import ca.codingcomrades.it.buscareplus.R;

public class HelpActivity extends AppCompatActivity {
    SharedPreferences prefs;
    LocalData data = new LocalData();

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
        prefs = this.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        Button download = findViewById(R.id.download);
        download.setOnClickListener(view  -> onDownload());


        FloatingActionButton fab = findViewById(R.id.call_fab);
        fab.setOnClickListener(view -> insertDummyContactWrapper());

    }

    public void onDownload() {

        String website = prefs.getString("userManualDwnLink","https://developer.android.com/training/data-storage/shared/documents-files");
        Log.d("download", "onDownload: "+ website);
        Intent intent = null;
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(website));
        startActivity(intent);
    }

    @Override
    public void onResume() {

        super.onResume();
        applySettings();

    }


    public void applySettings(){
        SharedPreferences prefs = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
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