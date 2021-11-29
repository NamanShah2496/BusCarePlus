
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

public class AboutusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aboutus_activity);
        Button button1 = (Button) findViewById(R.id.naman_link);
        Button button2 = (Button) findViewById(R.id.vishesh_link);
        Button button3 = (Button) findViewById(R.id.aryan_link);
        Button button4 = (Button) findViewById(R.id.jaskirat_link);

        // Technical debt
        button1.setOnClickListener(arg0 -> {
            Intent viewIntent = new Intent("android.intent.action.VIEW",
                    Uri.parse("https://www.linkedin.com/in/namanshahdeveloper/"));
            startActivity(viewIntent);
            button2.setOnClickListener(arg1 -> {
                Intent Intent = new Intent("android.intent.action.VIEW",
                        Uri.parse("https://www.linkedin.com/in/visheshbansal369/"));
                startActivity(Intent);
                button3.setOnClickListener(arg2 -> {
                    Intent Intent1 = new Intent("android.intent.action.VIEW",
                            Uri.parse("https://www.linkedin.com/in/aryan-sood-4800351a1/"));
                    startActivity(Intent1);
                    button4.setOnClickListener(arg3 -> {
                        Intent Intent2 = new Intent("android.intent.action.VIEW",
                                Uri.parse("https://www.linkedin.com/in/jaskirat-singh-855902219/"));
                        startActivity(Intent2);
                        ActionBar actionBar = getSupportActionBar();
                        if (actionBar != null) {
                            actionBar.setHomeButtonEnabled(true);
                            actionBar.setDisplayHomeAsUpEnabled(true);
                        }
                    });
                });
            });
        });
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
}
