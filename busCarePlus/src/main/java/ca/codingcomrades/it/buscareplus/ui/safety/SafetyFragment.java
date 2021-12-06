
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

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.codingcomrades.it.buscareplus.R;
import ca.codingcomrades.it.buscareplus.SpeedGauge;

public class SafetyFragment extends Fragment {

    Handler handler = new Handler();
    private SafetyViewModel safetyViewModel;
    private View view;
    double speed;
    int passengers;
    LottieAnimationView people;

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
        people = view.findViewById(R.id.people);
        people.setFrame(10);
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
        busNum = prefs.getInt("busNo",927);
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
        String speedVal = prefs.getString("speedval","0");
        String capacityVal = prefs.getString("capacityval","0");
        if(prefs.getString("metricB", "false").equalsIgnoreCase("false")) {
            Log.d("speed", "changeView: Its inside");
            speedoMeterView.setSpeed((float) (speed/1.609));
            speedTextView.setText(String.valueOf((float)speed/1.6));
            speedoMeterView.changeLimit(Integer.parseInt(speedVal));
            speedLabel.setText("m.p.h");
            int fres = (int)(speed*0.621371);
            if (fres>Integer.parseInt(speedVal))
                speedTextView.setTextColor(Color.RED);
            else
                speedTextView.setTextColor(Color.GREEN);
        }else {
            speedoMeterView.setSpeed((float) speed);
            speedLabel.setText("km/h");
            speedoMeterView.changeLimit(Integer.parseInt(speedVal));
            speedTextView.setText(String.valueOf(speed));
            if (speed>Integer.parseInt(speedVal))
                speedTextView.setTextColor(Color.RED);
            else
                speedTextView.setTextColor(Color.GREEN);
        }
        people.setMinAndMaxFrame(10,10);
        passengersTextView.setText(String.valueOf(passengers));

        if(passengers>Integer.parseInt(capacityVal)) {
            passengersTextView.setTextColor(Color.RED);
            people.setMinAndMaxFrame(50, 50);
        }
        else
            passengersTextView.setTextColor(Color.GREEN);
//        if (speed>Integer.parseInt(speedVal))
//            speedTextView.setTextColor(Color.RED);
//        else
//            speedTextView.setTextColor(Color.GREEN);
    }

}