// Section RNA
// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ca.codingcomrades.it.buscareplus.MainActivity;


public class SplashScreen extends AppCompatActivity {
    private Handler handler =new  Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Runnable runnable =new Runnable() {
        @Override
        public void run() {
            if (!isFinishing()) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish(); //so that splash screen doesnt open on resuming
            }
        }
    };
    @Override
    protected void onResume()
    {
        super.onResume();
        handler.postDelayed(runnable,3000);
    }

}