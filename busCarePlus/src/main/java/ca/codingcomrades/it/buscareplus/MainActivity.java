// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus;

import static ca.codingcomrades.it.buscareplus.R.string.exitmessage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import ca.codingcomrades.it.buscareplus.databinding.ActivityMainBinding;
import ca.codingcomrades.it.buscareplus.menu.AboutusActivity;
import ca.codingcomrades.it.buscareplus.menu.FeedbackActivity;
import ca.codingcomrades.it.buscareplus.menu.HelpActivity;
import ca.codingcomrades.it.buscareplus.menu.MyAccount;


public class MainActivity extends AppCompatActivity{
    Snackbar snackbar;
    LocalData data = new LocalData();
    Handler handler = new Handler();
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public boolean isConnected = true;
    UserData usr = new UserData();
    ImageView img;
    public boolean isBackground;
    public Intent myIntent;
    boolean flag= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_settings, R.id.nav_safety,R.id.nav_maintenance,R.id.nav_map)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        snackbar = Snackbar.make(binding.getRoot(), "Not Connected to Internet!", Snackbar.LENGTH_INDEFINITE);
        checkInternet();

//        Fragment fragment = new MapsFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.nav_map,fragment)
//                .commit();
        Log.d("MainAct", "onCreate: "+ isConnected);
    }


    //Behavioral Patterns
    //Command Design Pattern
    @Override
    public void onResume() {

        super.onResume();
        checkInternet();
        applySettings();

    }

    public void applySettings(){
        SharedPreferences prefs = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        String port = data.getPreference(this,"port",1);
        String ds = data.getPreference(this,"ds",1);

        if (port.equalsIgnoreCase("true")) {

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
    public void onTrimMemory(int level){
        super.onTrimMemory(level);
        if(level == TRIM_MEMORY_UI_HIDDEN){
            isBackground = true;
            myIntent = new Intent(getBaseContext(),Notification.class);
            startService(myIntent);
            Log.d("vishesh", "onTrimMemory: start");
        }


    }

    public void onBack() {
        //Creational Pattern
        //Builder Pattern
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.exit);
        builder.setMessage(exitmessage)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                        System.exit(0);
                    }
                })
                .setIcon(R.drawable.alert)
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed(){
            onBack();
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if( id == R.id.help){
            callHelp();
            return true;
        }
        else if(id == R.id.myaccountImage){
                Intent intent = new Intent(this, MyAccount.class);
                startActivity(intent);
        }
        else if (id == R.id.feedback){
            callFeedback();
            return true;
        }
        else if (id == R.id.logout){
            userLogout();
            return true;
        }
        else if (id == R.id.aboutus){
            Intent intent1 = new Intent(this, AboutusActivity.class);
            startActivity(intent1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Onclick(View v){
        Intent intent = new Intent(this, MyAccount.class);
        startActivity(intent);
    }
    public void callFeedback(){
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);
    }
    public void callHelp(){
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
    public void userLogout() {
        FirebaseAuth.getInstance().signOut();
        stopService((new Intent(MainActivity.this, Notification.class)));
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
//        finish();
    }
  public void checkInternet(){

     handler.postDelayed((new Runnable() {
        @Override
        public void run() {
            if(!usr.isInternetAvailable(getApplicationContext(), binding.getRoot())){
                if(flag){
                    flag = false;
                    snackbar.show();
                }}
            else{
                    snackbar.dismiss();
                    flag =true;

                Log.d("internet", "run: We have internet");
            }
            checkInternet();
        }
     } ),1000);
}

}