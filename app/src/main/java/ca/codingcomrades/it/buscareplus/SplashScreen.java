// Section RNA
// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {
    private Handler handler =new  Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.postDelayed(runnable,3000);


    }

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