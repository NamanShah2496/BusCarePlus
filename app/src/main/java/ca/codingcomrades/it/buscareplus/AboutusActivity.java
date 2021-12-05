
// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB
package ca.codingcomrades.it.buscareplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class AboutusActivity extends AppCompatActivity implements View.OnClickListener {

    Intent viewIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aboutus_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button button1 = findViewById(R.id.naman_link);
        button1.setOnClickListener(this);
        Button button2 = findViewById(R.id.vishesh_link);
        button2.setOnClickListener(this);
        Button button3 = findViewById(R.id.aryan_link);
        button3.setOnClickListener(this);
        Button button4 = findViewById(R.id.jaskirat_link);
        button4.setOnClickListener(this);

    }
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
       if( id == R.id.naman_link) {
           viewIntent = new Intent("android.intent.action.VIEW",
                   Uri.parse("https://www.linkedin.com/in/namanshahdeveloper/"));
           startActivity(viewIntent);
       }
       else if(id == R.id.vishesh_link) {
           viewIntent = new Intent("android.intent.action.VIEW",
                   Uri.parse("https://www.linkedin.com/in/visheshbansal369/"));
           startActivity(viewIntent);
       }
       else if(id == R.id.jaskirat_link) {
           viewIntent = new Intent("android.intent.action.VIEW",
                   Uri.parse("https://www.linkedin.com/in/jaskirat-singh-855902219/"));
           startActivity(viewIntent);
       }
       else if(id == R.id.aryan_link) {
           viewIntent = new Intent("android.intent.action.VIEW",
                   Uri.parse("https://www.linkedin.com/in/aryan-sood-4800351a1/"));
           startActivity(viewIntent);
       }
        }
    }

