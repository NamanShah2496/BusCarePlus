package ca.codingcomrades.it.buscareplus.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsFragment extends Fragment{


    private GoogleMap map;
    DatabaseReference database;
    MarkerOptions markerOptions;
//    SupportMapFragment mapFragment;
    double lat,lng;
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
            Log.d("maps","onMap  ");
//            getData();
//            lat =retLat();
//            lng = retLat();
            markerOptions = new MarkerOptions();
            Log.d("Maps", "onMapReady: "+ retLng() +" lat " +retLat());
            LatLng sydney = new LatLng(retLat(),retLng());

map = googleMap;
            googleMap.addMarker(markerOptions.position(sydney).title("Marker of Live location"));

            googleMap.moveCamera(CameraUpdateFactory.zoomTo(5.0f));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance().getReference();
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
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = String.valueOf(dataSnapshot.child("TestData/"+busNum).getValue());
                map.clear();
                lat = Double.parseDouble(String.valueOf(dataSnapshot.child("TestData/Lat").getValue()));
                lng = Double.parseDouble(String.valueOf(dataSnapshot.child("TestData/Long").getValue()));
                LatLng updated = new LatLng(lat,lng);


                map.addMarker(markerOptions.position(updated).title("Marker of Live location"));

                map.moveCamera(CameraUpdateFactory.zoomTo(5.0f));
                map.moveCamera(CameraUpdateFactory.newLatLng(updated));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public double retLat(){
//        getData();
        return lat;
    }
    public double retLng(){
//        getData();
        return lng;
    }
    public void getData() {
//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
////                String value = String.valueOf(dataSnapshot.child("TestData/"+busNum).getValue());
//                lat = Double.parseDouble(String.valueOf(dataSnapshot.child("TestData/Lat").getValue()));
//                lng = Double.parseDouble(String.valueOf(dataSnapshot.child("TestData/Long").getValue()));
//                LatLng updated = new LatLng(lat,lng);
//
//
////                map.addMarker(new MarkerOptions().position(updated).title("Marker of Live location"));
//
//                map.moveCamera(CameraUpdateFactory.zoomTo(5.0f));
//                map.moveCamera(CameraUpdateFactory.newLatLng(updated));
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//

    }



}