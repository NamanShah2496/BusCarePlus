
// Naman Shah , n01392496, Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ca.codingcomrades.it.buscareplus.LocalData;
import ca.codingcomrades.it.buscareplus.MainActivity;
import ca.codingcomrades.it.buscareplus.R;


public class SettingFragment extends Fragment {

    private SettingViewModel settingViewModel;

    private View view;
    private Switch portraitSwitch;
    private Switch darkSwitch;
    private Button saveButton;
    public TextView speedValue;
    private TextView maxPeopleValue;
    private SeekBar speedbar;
    private SeekBar maxPeoplebar;
    String svalue;
    String mpvalue;
    private RadioGroup radioGroup;
    private RadioButton metricButton;
    private RadioButton imperialButton;
    private String radioChoice = "metric";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        settingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        settingViewModel.getText();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.darkTheme);
        }
        else{
            getActivity().setTheme(R.style.AppTheme);
        }

        view = inflater.inflate(R.layout.fragment_settings,container,false);
        portraitSwitch = view.findViewById(R.id.PortraitLockSwitch);
        darkSwitch = view.findViewById(R.id.DarkThemeSwitch);
        speedbar = view.findViewById(R.id.SpeedSeekBar);
        maxPeoplebar = view.findViewById(R.id.CapacitySeekbar);
        speedValue = view.findViewById(R.id.SpeedTV);
        maxPeopleValue = view.findViewById(R.id.MaxPeopleTV);
        metricButton = view.findViewById(R.id.Unit_Metric_Radio_Btn);
        imperialButton = view.findViewById(R.id.Unit_Imperial_Radio_Btn);

        metricButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val=speedbar.getProgress();
                int res = (int)(val*1.609344);
                speedbar.setProgress(res);
                speedValue.setText(Integer.toString(res) + " Km/hrs");
            }
        });

        imperialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val=speedbar.getProgress();
                int res = (int)(val*0.621371);
                speedbar.setProgress(res);
                speedValue.setText(Integer.toString(res) + " miles/hrs");
            }
        });

        speedbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = progress;
                Log.d("TAG", "onProgressChanged: "+value);
                if(metricButton.isChecked()) {
                    speedValue.setText(Integer.toString(value)+" Kms/hrs");
                }else{
                    speedValue.setText(Integer.toString(value)+" miles/hrs");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                //Toast.makeText(getActivity(),value,Toast.LENGTH_SHORT).show();
            }
        });

        maxPeoplebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = progress;
                maxPeopleValue.setText(Integer.toString(value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saveButton = view.findViewById(R.id.SettingsSaveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getContext().getSharedPreferences("SHARED_PREFS",Context.MODE_PRIVATE).edit();
                if(portraitSwitch.isChecked()){
                    editor.putString("port","true");
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }else{
                    editor.putString("port","false");
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }

                if(darkSwitch.isChecked()){
                    editor.putString("ds","true");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }else {
                    editor.putString("ds","false");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }

                if(metricButton.isChecked()){
                    editor.putString("metricB","true");
                }else {
                    editor.putString("metricB","false");
                }

                if(imperialButton.isChecked()){
                    editor.putString("imperialB","true");
                }else {
                    editor.putString("imperialB","false");
                }

                svalue = Integer.toString(speedbar.getProgress());
                mpvalue = Integer.toString(maxPeoplebar.getProgress());
                editor.putString("speedval",svalue);
                editor.putString("capacityval",mpvalue);

                editor.apply();

            }
        });

        SharedPreferences prefs = this.getActivity().getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        String port = prefs.getString("port","false");
        String ds = prefs.getString("ds","false");
        String metricB = prefs.getString("metricB","false");
        String imperialB = prefs.getString("imperialB","false");
        String speedVal = prefs.getString("speedval","30");
        String capacityVal = prefs.getString("capacityval","20");

        if(isPortraitOn(port)){
            portraitSwitch.setChecked(true);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
        if(isDarkThemeOn(ds)){
            darkSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (isMetricOn(metricB)){
            metricButton.setChecked(true);
            radioChoice = "metric";
        }
        if(isImperialOn(imperialB)){
            imperialButton.setChecked(true);
            radioChoice = "imperial";
        }

        speedbar.setProgress(Integer.parseInt(speedVal));
        if(radioChoice.equalsIgnoreCase("metric")) {
            speedValue.setText(speedVal + " Km/hrs");
        }else {
            speedValue.setText(speedVal + " miles/hrs");
        }
        maxPeoplebar.setProgress(Integer.parseInt(capacityVal));
        maxPeopleValue.setText(capacityVal);

        return view;
    }

    public boolean isPortraitOn(String port){
        if(port.equalsIgnoreCase("true")){
            return true;
        }
            return false;
    }

    public boolean isDarkThemeOn(String ds){
        if(ds.equalsIgnoreCase("true")){
            return true;
        }
        return false;
    }

    public boolean isMetricOn(String metricB){
        if (metricB.equalsIgnoreCase("true")){
            return true;
        }
        return false;
    }

    public boolean isImperialOn(String imperialB){
        if(imperialB.equalsIgnoreCase("true")){
            return true;
        }
        return false;
    }

    private void restartApp(){
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
    }

}