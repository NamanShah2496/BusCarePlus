// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB



package ca.codingcomrades.it.buscareplus.ui.maintenance;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.codingcomrades.it.buscareplus.R;
import ca.codingcomrades.it.buscareplus.databinding.FragmentMaintenanceBinding;
import ca.codingcomrades.it.buscareplus.databinding.FragmentSafetyBinding;
import ca.codingcomrades.it.buscareplus.ui.settings.SettingViewModel;

public class MaintenanceFragment extends Fragment {
    Handler handler = new Handler();
    private MaintenanceViewModel maintenanceViewModel;
    private View view;
    private TextView temperatureTextView,carbonTextView;
    int temp,carbon;
    DatabaseReference database;
    int busNum=927;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        maintenanceViewModel = new ViewModelProvider(this).get(MaintenanceViewModel.class);
        maintenanceViewModel.getText();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maintenance,container,false);
        temperatureTextView = view.findViewById(R.id.MaintainanceThermoValueTV);
        carbonTextView = view.findViewById(R.id.MaintainanceCarbonValueTV);
        database = FirebaseDatabase.getInstance().getReference();
        getData();
        return view;

    }
    public void getData() {
       handler.postDelayed(() -> database.child("Data/" + busNum + "/Maintenance").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    temp = Integer.parseInt(String.valueOf(task.getResult().child("Temperature").getValue()));
                    carbon = Integer.parseInt(String.valueOf(task.getResult().child("Co2").getValue()));
                    changeView(temp, carbon);
                }
                getData();
            }
        }),1000);
    }
    public void changeView(int temp,int carbon){
        temperatureTextView.setText(String.valueOf(temp));
        carbonTextView.setText(String.valueOf(carbon));
    }



}