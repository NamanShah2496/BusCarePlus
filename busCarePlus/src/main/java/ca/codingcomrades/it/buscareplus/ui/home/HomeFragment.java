
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
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

//import ca.codingcomrades.it.buscareplus.HelpActivity;
import java.util.ArrayList;
import java.util.List;

import ca.codingcomrades.it.buscareplus.LocalData;
import ca.codingcomrades.it.buscareplus.Notification;
import ca.codingcomrades.it.buscareplus.R;
//import ca.codingcomrades.it.buscareplus.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    Handler handler = new Handler();
    DatabaseReference database;
    LocalData localData;
    private HomeViewModel homeViewModel;
    private View view;
    ImageButton speedBtn,passengersBtn,carbonBtn,temperatureBtn;
    double speed,temperatureReading;
    double speed_mph;
    int passengers,carbonReading;
    Spinner busSpinner;
    Button busbutton;
    TextView textView;
    int busNum=927;
    String isMetric,speedLimit,passengerLimit;
    List<Integer> names;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database =FirebaseDatabase.getInstance().getReference();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getText();
        localData = new LocalData();

        names = new ArrayList<>();
    }

public void updateUI(){
    handler.postDelayed(() -> database.child("Data/"+busNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
                  temperatureReading = Double.parseDouble(String.valueOf(task.getResult().child("Maintenance/Temperature").getValue()));
                  carbonReading = Integer.parseInt(String.valueOf(task.getResult().child("Maintenance/Co2").getValue()));
                  passengers = Integer.parseInt(String.valueOf(task.getResult().child("Safety/Passengers").getValue()));
                  speed = Double.parseDouble(String.valueOf(task.getResult().child("Safety/Speed").getValue()));
                changeColor(speed,passengers);
                //TODO no need to pass para, remove and check in test branch
                 }
         updateUI();
        }
    }), 1000);
}

public void changeColor(double speed,int passengers){
        if(isMetric.equalsIgnoreCase("true")){
            if(speed>Double.parseDouble(speedLimit)){
                speedBtn.setBackgroundColor(Color.RED);
            }else{
                speedBtn.setBackgroundColor(0xFF3BDF35);
            }
        }
        else{
            speed_mph = speed*0.621371;
            if(speed_mph>Double.parseDouble(speedLimit)){
                speedBtn.setBackgroundColor(Color.RED);
            }else{
                speedBtn.setBackgroundColor(0xFF3BDF35);
            }
        }

    if(passengers>Integer.parseInt(passengerLimit)
    )
        passengersBtn.setBackgroundColor(Color.RED);
    else
        passengersBtn.setBackgroundColor(0xFF3BDF35);
    if(temperatureReading>24)
        temperatureBtn.setBackgroundColor(Color.RED);
    else
        temperatureBtn.setBackgroundColor(0xFF3BDF35);
    if(carbonReading>1000)
        carbonBtn.setBackgroundColor(Color.RED);
    else
        carbonBtn.setBackgroundColor(0xFF3BDF35);
}
 public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container,false);

        busSpinner = (Spinner)view.findViewById(R.id.busoption);
        buses();
        prefs = getActivity().getSharedPreferences("SHARED_PREFS",Context.MODE_PRIVATE);
        editor = prefs.edit();
     textView = view.findViewById(R.id.busno);
        speedBtn = view.findViewById(R.id.speedBtn);
        passengersBtn = view.findViewById(R.id.passengersBtn);
        temperatureBtn =view.findViewById(R.id.temperatureBtn);
        carbonBtn = view.findViewById(R.id.carbonBtn);
     fetchLocalData();
//     if(prefs.getInt("busNo",927) == 927)
//         busSpinner.setSelection(0);
//     else if(prefs.getInt("busNo",927) == 36)
//         busSpinner.setSelection(1);
//    else
//        busSpinner.setSelection(2);

     busSpinner.setOnItemSelectedListener(this);


     updateUI();
return view;
    }

    public void buses() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                database.child("Data").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }



                            if(task.getResult().child("/").getChildrenCount() == busSpinner.getCount()){
                                Log.d("busCount",String.valueOf(busSpinner.getCount()));
                            }
                            else {
                                busUpdated(task);
                            }

                    }
                });
                buses();
            }
        }, 1000);
    }
    public void busUpdated(Task<DataSnapshot> task){
        names.clear();
        for (DataSnapshot task1 : task.getResult().getChildren()) {

            busNum = Integer.parseInt(task1.getKey());
            names.add(busNum);

        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.color_spinner_layout, names);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        busSpinner.setAdapter(adapter);
    }

    public void applySettings(){
        SharedPreferences prefs = this.getActivity().getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);
        String port = prefs.getString("port","false");
        String ds = prefs.getString("ds","false");
        if(port.equalsIgnoreCase("true")){

            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
        if(ds.equalsIgnoreCase("true")){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    public void busSelected(){
        busNum = Integer.parseInt(busSpinner.getSelectedItem().toString());
        editor.putInt("busNo",busNum);
        editor.apply();
        Log.d("Spinner  ", "busSelected: "+busSpinner.getSelectedItem());
        Log.d("Shared", "busSelected: "+ prefs.getInt("busNo",45));
    }

    public void fetchLocalData(){

        passengerLimit = prefs.getString("capacityval","0");
        speedLimit = prefs.getString("speedval","0");
        isMetric = prefs.getString("metricB","false");
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        busSelected();
        updateText();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d("TAG", "onNothingSelected: ");
    }
    public void updateText() {
        textView.setText(String.valueOf(busNum));
    }




}