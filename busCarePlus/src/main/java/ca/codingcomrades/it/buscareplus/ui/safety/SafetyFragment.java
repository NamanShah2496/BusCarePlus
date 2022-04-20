
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.codingcomrades.it.buscareplus.R;
import ca.codingcomrades.it.buscareplus.SpeedGauge;

public class SafetyFragment extends Fragment {

    Handler handler = new Handler();
    private SafetyViewModel safetyViewModel;
    private View view;
    double speed;
    int passengers;
    LottieAnimationView people;
    String rootPath;
    SpeedGauge speedoMeterView;
    TextView speedTextView,passengersTextView,speedLabel,speedLabel_2;
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
        prefs = getActivity().getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

        speedLabel = view.findViewById(R.id.speedometerLabel);
        speedLabel_2 = view.findViewById(R.id.speedometerLabel_2);
        speedoMeterView =view.findViewById(R.id.speedoMeter);
        speedTextView = view.findViewById(R.id.safetySpeedReadings);
        passengersTextView = view.findViewById(R.id.safetyPassengersReading);
        database = FirebaseDatabase.getInstance().getReference();
        fetchLocalData();
        getData();
        return view;
    }
    public void fetchLocalData(){
    rootPath = prefs.getString("accessPath","Canada/TTC");

    }
    public void getData() {

        busNum = prefs.getInt("busNo",927);
        database.child(rootPath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String value = String.valueOf(dataSnapshot.child("/Data/"+busNum).getValue());
                passengers = Integer.parseInt(String.valueOf(dataSnapshot.child("/Data/"+busNum+"/Safety/Passengers").getValue()));
                speed = Double.parseDouble(String.valueOf(dataSnapshot.child("/Data/"+busNum+"/Safety/Speed").getValue()));
                Log.d("New ", "Value is: " + speed);
                changeView(passengers, speed);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void changeView(int passengers,double speed){
        Log.d("speed", "changeView: "+ prefs.getString("metricB","false"));
        String speedVal = prefs.getString("speedval","30");
        String capacityVal = prefs.getString("capacityval","20");
        if(prefs.getString("imperialB", "false").equalsIgnoreCase("false")) {
           Log.d("speed", "changeView: Its inside");
            speedoMeterView.setSpeed((float) speed);
            speedLabel.setText("km/h");
            speedLabel_2.setText("km/h");
            speedoMeterView.changeLimit(Integer.parseInt(speedVal));
            speedTextView.setText(String.valueOf(speed));
            if (speed>Integer.parseInt(speedVal))
                speedTextView.setTextColor(Color.RED);
            else
                speedTextView.setTextColor(Color.GREEN);
        }else {
                    Log.d("speed", "changeView: Its inside");
            speedoMeterView.setSpeed((float) (speed/1.609));
            speedTextView.setText(String.valueOf((float)speed/1.6));
            speedoMeterView.changeLimit(Integer.parseInt(speedVal));
            speedLabel.setText("m.p.h");
            speedLabel_2.setText("m.p.h");
            int fres = (int)(speed*0.621371);
            if (fres>Integer.parseInt(speedVal))
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