// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB



package ca.codingcomrades.it.buscareplus.ui.maintenance;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.codingcomrades.it.buscareplus.R;

import ca.codingcomrades.it.buscareplus.ui.settings.SettingViewModel;

public class MaintenanceFragment extends Fragment {
    Handler handler = new Handler();
    private MaintenanceViewModel maintenanceViewModel;
    private View view;
    private TextView temperatureTextView,carbonTextView;
    double temp,carbon;
    String rootPath;
    LottieAnimationView thermometer;
    DatabaseReference database;
    SharedPreferences prefs;
    int busNum=927;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        maintenanceViewModel = new ViewModelProvider(this).get(MaintenanceViewModel.class);
        maintenanceViewModel.getText();

    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maintenance,container,false);
        thermometer = view.findViewById(R.id.thermometer);
        prefs = getActivity().getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

      //  thermometer.setSpeed(1);
       thermometer.setFrame(55);
        temperatureTextView = view.findViewById(R.id.MaintainanceThermoValueTV);
        carbonTextView = view.findViewById(R.id.MaintainanceCarbonValueTV);
        database = FirebaseDatabase.getInstance().getReference();
        fetchLocalData();
        getData();
        
        return view;

    }
    public void getData() {
        busNum = prefs.getInt("busNo",927);
        handler.postDelayed(() -> database.child(rootPath+"/Data/" + busNum + "/Maintenance").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {

                    temp = Double.parseDouble(String.valueOf(task.getResult().child("Temperature").getValue()));
                    carbon = Double.parseDouble(String.valueOf(task.getResult().child("Co2").getValue()));
                    changeView(temp, carbon);
                }
                getData();
            }
        }),1000);
    }
    public void fetchLocalData(){
        rootPath = prefs.getString("accessPath","Canada/TTC");
        Log.d("accessPath", "fetchLocalData: " + rootPath);

    }
    public void changeView(double temp,double carbon){
        temperatureTextView.setText(String.valueOf(temp));
        carbonTextView.setText(String.valueOf(carbon));
        temperatureTextView.append(" °C");
        carbonTextView.append(" ppm");
        thermometer.setMinAndMaxFrame(60,60);
        if(temp>24) {
            temperatureTextView.setTextColor(Color.RED);
            thermometer.setMinAndMaxFrame(158,158);
        }
        else {
            temperatureTextView.setTextColor(Color.GREEN);

        }
        if (carbon>1000)
            carbonTextView.setTextColor(Color.RED);
        else
            carbonTextView.setTextColor(Color.GREEN);
    }

}