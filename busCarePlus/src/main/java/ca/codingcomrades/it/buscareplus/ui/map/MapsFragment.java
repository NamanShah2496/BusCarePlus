package ca.codingcomrades.it.buscareplus.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ca.codingcomrades.it.buscareplus.R;
import kotlinx.coroutines.channels.ActorKt;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.util.Map;

public class MapsFragment extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String uid,rootPath;
    SharedPreferences prefs;
    Map<String, Object> arr;
    Handler handler = new Handler();
    private GoogleMap map;
    DatabaseReference database;
    MarkerOptions markerOptions;
    int busNum;
    CameraPosition cam;;
    //    SupportMapFragment mapFragment;
    double lat, lng;

//    double lat=43.7446603;
//    double lng = -79.5940434;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        @Override
        public void onMapReady(GoogleMap googleMap) {

//            MarkerOptions markerOptions = new MarkerOptions();
            Log.d("maps", "onMap  ");
//            getData();
//            lat =retLat();
//            lng = retLat();
            markerOptions = new MarkerOptions();
            Log.d("Maps", "onMapReady: " + retLng() + " lat " + retLat());
            LatLng sydney = new LatLng(retLat(), retLng());

            map = googleMap;
            googleMap.addMarker(markerOptions.position(sydney).title("Marker of Live location"));

            googleMap.moveCamera(CameraUpdateFactory.zoomTo(12.0f));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            googleMap.getUiSettings().setZoomControlsEnabled(true);


        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance().getReference();
        prefs = getActivity().getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE);

        fetchLocalData();
//        getData();
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getData();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
//            getData();
            mapFragment.getMapAsync(callback);
        }

        database.child(rootPath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                cam = map.getCameraPosition();
                map.clear();
//                Log.d("Maps", "onDataChange: "+rootPath);
                lat = Double.parseDouble(String.valueOf(dataSnapshot.child("/Data/"+busNum+"/Location/Lat").getValue()));
                lng =  Double.parseDouble(String.valueOf(dataSnapshot.child("/Data/"+busNum+"/Location/Long").getValue()));
                LatLng updated = new LatLng(lat, lng);


                map.addMarker(markerOptions.position(updated).title("Marker of Live location"));

                map.moveCamera(CameraUpdateFactory.zoomTo(cam.zoom));
                map.moveCamera(CameraUpdateFactory.newLatLng(updated));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void fetchLocalData(){
        busNum = prefs.getInt("busNo",927);
        rootPath = prefs.getString("accessPath","Canada/TTC");

    }


    public double retLat() {
//        getData();
        return lat;
    }

    public double retLng() {
//        getData();
        return lng;
    }



    }



