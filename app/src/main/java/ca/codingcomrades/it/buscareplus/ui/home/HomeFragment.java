
// Naman Shah , n01392496, Section RNA
// Aryan Sood , n01393003 ,Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;

//import ca.codingcomrades.it.buscareplus.HelpActivity;
import ca.codingcomrades.it.buscareplus.R;
//import ca.codingcomrades.it.buscareplus.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private View view;
    Spinner bus;
    Button busbutton;
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getText();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container,false);
      // view.setBackgroundColor(0xFF20FFFF);
        bus = (Spinner)view.findViewById(R.id.busoption);
        busbutton = view.findViewById(R.id.busbutton);
        textView = view.findViewById(R.id.busno);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String port = prefs.getString("port","false");
        String ds = prefs.getString("ds","false");
        if(port.equalsIgnoreCase("true")){

            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
        if(ds.equalsIgnoreCase("true")){
           // view.setBackgroundColor(Color.BLACK);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        busbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateText(v);
            }
        });
return view;
    }

    public void updateText(View v) {
        String busnumber  =(String)((TextView)bus.getSelectedView()).getText();
        textView.setText(busnumber);
    }

}