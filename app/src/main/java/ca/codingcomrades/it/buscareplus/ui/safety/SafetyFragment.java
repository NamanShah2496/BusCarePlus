
// Naman Shah , n01392496 ,Section RNA
// Aryan Sood , n01393003 ,Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB

package ca.codingcomrades.it.buscareplus.ui.safety;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import ca.codingcomrades.it.buscareplus.R;
import ca.codingcomrades.it.buscareplus.databinding.FragmentSafetyBinding;
import ca.codingcomrades.it.buscareplus.ui.maintenance.MaintenanceViewModel;

public class SafetyFragment extends Fragment {
    Handler handler = new Handler();
    private SafetyViewModel safetyViewModel;
    private View view;
    double speed;
    int passengers;
    TextView speedTextView,passengersTextView;
    DatabaseReference database;
    int busNum =927;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        safetyViewModel = new ViewModelProvider(this).get(SafetyViewModel.class);
        safetyViewModel.getText();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_safety,container,false);
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

        passengersTextView.setText(String.valueOf(passengers));
        speedTextView.setText(String.valueOf(speed));
        if(passengers>30)
            passengersTextView.setBackgroundColor(Color.RED);
        else
            passengersTextView.setBackgroundColor(Color.GREEN);
        if (speed>50)
            speedTextView.setBackgroundColor(Color.RED);
        else
            speedTextView.setBackgroundColor(Color.GREEN);
    }

}