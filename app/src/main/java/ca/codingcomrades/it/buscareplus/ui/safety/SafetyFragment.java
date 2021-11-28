
// Naman Shah , n01392496 ,Section RNA
// Aryan Sood , n01393003 ,Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus.ui.safety;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.codingcomrades.it.buscareplus.R;
import ca.codingcomrades.it.buscareplus.SpeedGauge;
import in.unicodelabs.kdgaugeview.KdGaugeView;

public class SafetyFragment extends Fragment {
    Handler handler = new Handler();
    private SafetyViewModel safetyViewModel;
    private View view;
    double speed;
    int passengers;

    SpeedGauge speedoMeterView;
    TextView speedTextView,passengersTextView,speedLabel;
    DatabaseReference database;
    SharedPreferences prefs;
    int busNum =927;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        safetyViewModel = new ViewModelProvider(this).get(SafetyViewModel.class);
        safetyViewModel.getText();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_safety,container,false);
        prefs = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        speedLabel = view.findViewById(R.id.speedometerLabel);
        speedoMeterView =view.findViewById(R.id.speedoMeter);
        speedTextView = view.findViewById(R.id.safetySpeedReadings);
        passengersTextView = view.findViewById(R.id.safetyPassengersReading);
        database = FirebaseDatabase.getInstance().getReference();
        getData();
        return view;
    }
    public void getData() {
        handler.postDelayed(() -> database.child("Data/" + busNum + "/Safety").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    passengers = Integer.parseInt(String.valueOf(task.getResult().child("Passengers").getValue()));
                    speed = Double.parseDouble(String.valueOf(task.getResult().child("Speed").getValue()));
                    changeView(passengers, speed);
                }
                getData();
            }
        }),1000);
    }
    public void changeView(int passengers,double speed){
        Log.d("speed", "changeView: "+ prefs.getString("metricB","false"));
        if(prefs.getString("metricB", "false").equals("true")) {
            Log.d("speed", "changeView: Its inside");
            speedoMeterView.setSpeed((float) (speed/1.609));
            speedTextView.setText(String.valueOf((float)speed/1.6));
            speedoMeterView.changeLimit();
            speedLabel.setText("m.p.h");
        }else {
            speedoMeterView.setSpeed((float) speed);
            speedLabel.setText("km/h");
            speedTextView.setText(String.valueOf(speed));
        }
        passengersTextView.setText(String.valueOf(passengers));

        if(passengers>30)
            passengersTextView.setTextColor(Color.RED);
        else
            passengersTextView.setTextColor(Color.GREEN);
        if (speed>50)
            speedTextView.setTextColor(Color.RED);

        else
            speedTextView.setTextColor(Color.GREEN);
    }

}