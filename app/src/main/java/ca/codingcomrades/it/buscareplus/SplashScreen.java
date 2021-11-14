// Section RNA
// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


public class SplashScreen extends AppCompatActivity {
    private Handler handler =new  Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.postDelayed(runnable,3000);
    }

//    @Override
//    public void onResume() {
//
//        super.onResume();
//
//        SharedPreferences prefs = getSharedPreferences("pref", Context.MODE_PRIVATE);
//        String port = prefs.getString("port","false");
//        String ds = prefs.getString("ds","false");
//        if(port.equalsIgnoreCase("true")){
//
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//        }
//        if(ds.equalsIgnoreCase("true")){
//
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        }else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//    }


    public Runnable runnable =new Runnable() {
        @Override
        public void run() {
            Log.d("TAG", "run: ");
            if (!isFinishing()) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish(); //so that splash screen doesnt open on resuming
            }
        }
    };
}