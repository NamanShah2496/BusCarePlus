
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
        metricButton = view.findViewById(R.id.MetricRB);
        imperialButton = view.findViewById(R.id.ImperialRB);

        metricButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speedValue.setText(Integer.toString(speedbar.getProgress()) + " Km/hrs");
            }
        });

        imperialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speedValue.setText(Integer.toString(speedbar.getProgress()) + " miles/hrs");
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
//                Toast.makeText(getActivity(),value,Toast.LENGTH_SHORT).show();

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
//                maxPeopleValue.setText(value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
//            darkSwitch.setChecked(true);
//        }

        saveButton = view.findViewById(R.id.SettingsSaveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getContext().getSharedPreferences("pref",Context.MODE_PRIVATE).edit();
                if(portraitSwitch.isChecked()){
                    editor.putString("port","true");
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }else{
                    editor.putString("port","false");
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
//                editor.apply();

                if(darkSwitch.isChecked()){
//                    getActivity().getTheme().applyStyle(R.style.darkTheme, true);
                    editor.putString("ds","true");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }else {
//                    getActivity().getTheme().applyStyle(R.style.AppTheme, true);
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

        SharedPreferences prefs = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String port = prefs.getString("port","false");
        String ds = prefs.getString("ds","false");
        String metricB = prefs.getString("metricB","false");
        String imperialB = prefs.getString("imperialB","false");
        String speedVal = prefs.getString("speedval","0");
        String capacityVal = prefs.getString("capacityval","0");

        if(port.equalsIgnoreCase("true")){
            portraitSwitch.setChecked(true);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
        if(ds.equalsIgnoreCase("true")){
            darkSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (metricB.equalsIgnoreCase("true")){
            metricButton.setChecked(true);
            radioChoice = "metric";
        }
        if(imperialB.equalsIgnoreCase("true")){
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

    private void restartApp(){
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
    }

}