
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import ca.codingcomrades.it.buscareplus.HelpActivity;
import ca.codingcomrades.it.buscareplus.Notification;
import ca.codingcomrades.it.buscareplus.R;
//import ca.codingcomrades.it.buscareplus.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Handler handler = new Handler();
    DatabaseReference database;
    private HomeViewModel homeViewModel;
    private View view;
    ImageButton speedBtn,passengersBtn,carbonBtn,temperatureBtn;
    double speed,temperatureReading;
    int passengers,carbonReading;
    Spinner busSpinner;
    Switch notification;
    Button busbutton;
    TextView textView;
    int busNum=927;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database =FirebaseDatabase.getInstance().getReference();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getText();


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
    if(speed>50){
        speedBtn.setBackgroundColor(Color.RED);
    }else{
       speedBtn.setBackgroundColor(0xFF3BDF35);
    }
    if(passengers>30)
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
        busSpinner.setOnItemSelectedListener(this);


        textView = view.findViewById(R.id.busno);
        speedBtn = view.findViewById(R.id.speedBtn);
        passengersBtn = view.findViewById(R.id.passengersBtn);
        temperatureBtn =view.findViewById(R.id.temperatureBtn);
        carbonBtn = view.findViewById(R.id.carbonBtn);
        notification = view.findViewById(R.id.notification_switch);

         updateUI();
return view;
    }

    public void busSelected(){
        SharedPreferences.Editor editor = getContext().getSharedPreferences("pref",Context.MODE_PRIVATE).edit();
        busNum = Integer.parseInt(busSpinner.getSelectedItem().toString());
        editor.putInt("busNo",busNum);
        Log.d("Spinner  ", "busSelected: "+busSpinner.getSelectedItem());

    }


    @Override
    public void onResume() {

        super.onResume();
       // applySettings();
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