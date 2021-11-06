
// Naman Shah , n01392496, Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ca.codingcomrades.it.buscareplus.MainActivity;
import ca.codingcomrades.it.buscareplus.R;


public class SettingFragment extends Fragment {

    private SettingViewModel settingViewModel;

    private View view;
    private Switch portraitSwitch;
    private Switch darkSwitch;
    private Button saveButton;

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
        }else{
            getActivity().setTheme(R.style.AppTheme);
        }

        view = inflater.inflate(R.layout.fragment_settings,container,false);
        portraitSwitch = view.findViewById(R.id.PortraitLockSwitch);
        darkSwitch = view.findViewById(R.id.DarkThemeSwitch);

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            darkSwitch.setChecked(true);
        }

        saveButton = view.findViewById(R.id.SettingsSaveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(portraitSwitch.isChecked()){
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                }else{
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }

                if(darkSwitch.isChecked()){
//                    getActivity().getTheme().applyStyle(R.style.darkTheme, true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }else {
//                    getActivity().getTheme().applyStyle(R.style.AppTheme, true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }

            }
        });



        return view;
    }

    private void restartApp(){
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);

    }

}