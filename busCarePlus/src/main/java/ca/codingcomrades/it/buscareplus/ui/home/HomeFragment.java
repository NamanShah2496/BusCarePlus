
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

//import ca.codingcomrades.it.buscareplus.HelpActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import ca.codingcomrades.it.buscareplus.LocalData;
import ca.codingcomrades.it.buscareplus.Notification;
import ca.codingcomrades.it.buscareplus.R;
//import ca.codingcomrades.it.buscareplus.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    Handler handler = new Handler();
    DatabaseReference database;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String uid,rootPath;
    Map<String, Object> arr;
    LocalData localData;
    private HomeViewModel homeViewModel;
    private View view;
    ImageButton speedBtn,passengersBtn,carbonBtn,temperatureBtn;
    double speed,temperatureReading;
    double speed_mph;
    int passengers,carbonReading;
    String epoch="";
    Spinner busSpinner;
    Button busbutton;
    TextView textView,epoch_display;
    int busNum=927;
    String isMetric,speedLimit,passengerLimit;
    List<Integer> names,dynamicList;
    int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database =FirebaseDatabase.getInstance().getReference();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getText();
        localData = new LocalData();
        dynamicList = new ArrayList<>();
        names = new ArrayList<>();
        retriveUserData();
    }

public void updateUI(){

    handler.postDelayed(() -> database.child(rootPath+"/Data/"+busNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            Log.d("inside", epoch);
            String result;
            if (epoch == "")
                result = "0";
            else{
                result = String.format("%.0f", Double.parseDouble(epoch));
        }
          long d=Long.parseLong(result);
       Date date = new Date((d* 1000L));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatted = format.format(date);

 epoch_display.setText(formatted);
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            }
            else {
              //  Log.d("Firebase", "onComplete: bus: " +task.getResult().toString() );
                Log.d("Maps", "onDataChange: Lats "+ task.getResult().child("Location/Lat").getValue());
                  temperatureReading = Double.parseDouble(String.valueOf(task.getResult().child("Maintenance/Temperature").getValue()));
                  carbonReading = Integer.parseInt(String.valueOf(task.getResult().child("Maintenance/Co2").getValue()));
                  passengers = Integer.parseInt(String.valueOf(task.getResult().child("Safety/Passengers").getValue()));
                  speed = Double.parseDouble(String.valueOf(task.getResult().child("Safety/Speed").getValue()));
                  epoch=(String.valueOf(task.getResult().child("TimeStamp").getValue()));
                  Log.d("test",epoch);
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

        prefs = getActivity().getSharedPreferences("SHARED_PREFS",Context.MODE_PRIVATE);
        editor = prefs.edit();
     textView = view.findViewById(R.id.busno);
       epoch_display=view.findViewById(R.id.epoch);
        speedBtn = view.findViewById(R.id.speedBtn);
        passengersBtn = view.findViewById(R.id.passengersBtn);
        temperatureBtn =view.findViewById(R.id.temperatureBtn);
        carbonBtn = view.findViewById(R.id.carbonBtn);
        fetchLocalData();
        buses();


     busSpinner.setOnItemSelectedListener(this);

     updateUI();
return view;
    }

    public void buses() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                database.child(rootPath+"/Data").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
    public void busUpdated(Task <DataSnapshot> task){
        names.clear();
        for (DataSnapshot task1 : task.getResult().getChildren()) {
            Log.d("Firebase", "busUpdated: task: "+task1);
            busNum = Integer.parseInt(task1.getKey());
            names.add(busNum);


        }
        createBusList(names);
        names = dynamicList;

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.color_spinner_layout, names);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);

        busSpinner.setAdapter(adapter);
    }

    public void createBusList(List<Integer> names){
//        dynamicList.clear();
        int temp = names.get(pos);
        Log.d("dynamic", "createBusList: " +temp);

        dynamicList.add(temp);
        names.remove(pos);
        for(int i=0;i<names.size();i++){
            dynamicList.add(names.get(i));
        }
        Log.d("dynamic", "createBusList: "+ dynamicList);

    }
    public void retriveUserData() {
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        uid = fAuth.getUid();
        DocumentReference df = fStore.collection("Users").document(uid);
        df.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                try {
                    arr = value.getData();
                    rootPath = arr.get("accessPath").toString();
                    editor.putString("accessPath",rootPath);
                    editor.apply();
                    Log.d("Firebase", "onEvent: access: " + rootPath);
                } catch (Exception e) {
                    Log.d("TAG", "My account Exception: ");
                }
            }
        });

    }
    public void busSelected(){
        busNum = Integer.parseInt(busSpinner.getSelectedItem().toString());

        editor.putInt("busNo",busNum);
        editor.apply();
        Log.d("Spinner  ", "busSelected: "+busSpinner.getSelectedItem());
        Log.d("Shared", "busSelected: "+ prefs.getInt("busNo",45));
    }

    public void fetchLocalData(){
        pos = prefs.getInt("pos",0);
        passengerLimit = prefs.getString("capacityval","20");
        speedLimit = prefs.getString("speedval","40");
        isMetric = prefs.getString("metricB","false");
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Spinner", "onItemSelected:position "+ position);

        editor.putInt("pos",position);
        editor.apply();
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