package com.example.cardstore;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Button;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.cardstore.databinding.ActivityMaps2Binding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String data;


    Button button;
    private LocationManager locationManager;
    private ActivityMaps2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        data = getIntent().getExtras().getString("message_key", "defaultKey");




    }


    public void Atras (View view) {

        view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));

    }
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        switch (data) {
            case "Wargaming":
                LatLng wargaming = new LatLng(-33.430252, -70.618391);
                mMap.addMarker(new MarkerOptions().position(wargaming).title("Wargaming.cl"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wargaming, 15));


                break;
            case "Pokenono":
                LatLng pokenono = new LatLng(-33.45694, -70.64827);
                mMap.addMarker(new MarkerOptions().position(pokenono).title("Pokenono"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pokenono, 15));
                break;
            case "USA":
                LatLng meplesFam = new LatLng(33.804201, -117.911158);
                mMap.addMarker(new MarkerOptions().position(meplesFam).title("MeeplesFamily"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meplesFam, 15));
                break;


        }

    }


}