package ca.codingcomrades.it.buscareplus.ui.map;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mapbox.android.core.permissions.PermissionsManager;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.*;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

import ca.codingcomrades.it.buscareplus.MainActivity;
import ca.codingcomrades.it.buscareplus.R;


public class MapBox extends Fragment {

    private MainActivity activity;
    private MapView mapView;



    public MapBox() {
        // Required empty public constructor
    }

    public static MapBox newInstance(String param1, String param2) {
        MapBox fragment = new MapBox();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
//        SupportMapFragment mapFragment;
//        if (savedInstanceState == null) {
//
//// Create fragment
//            final FragmentTransaction transaction = (FragmentTransaction) getActivity().getSupportFragmentManager().beginTransaction();
//
//// Build mapboxMap
//            MapboxMapOptions options = MapboxMapOptions.createFromAttributes(this, null);
//            options.camera(new CameraPosition.Builder()
//                    .target(new LatLng(-52.6885, -70.1395))
//                    .zoom(9)
//                    .build());
//
//// Create map fragment
//            mapFragment = SupportMapFragment.newInstance(options);
//
//// Add map fragment to parent container
//            transaction.add(R.id.container,mapFragment,"");
////            transaction.add(R.id.container, mapFragment, "com.mapbox.map");
//            transaction.commit();
//        } else {
//            mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentByTag("ca.codingcomrades.it.buscareplus.ui.map.MapBox");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Mapbox.getInstance(getContext().getApplicationContext(),getString(R.string.mapbox_access_token));
        View view = inflater.inflate(R.layout.fragment_map_box,container,false);

        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                if (activity != null) {
//                    activity.setMapboxMap(mapboxMap);
                }
            }
        });

        return view;
//        return inflater.inflate(R.layout.fragment_map_box, container, false);
    }


}