package com.cmput301f20t13.treatyourshelf.ui.geolocation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cmput301f20t13.treatyourshelf.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// Referenced https://www.youtube.com/watch?v=MWowf5SkiOE&ab_channel=CodingWithMitch

public class MapsFragment extends Fragment {


    private GoogleMap mMap;
    private boolean mapReady = false;
    private boolean isOwner = false;
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
            mMap = googleMap;
            mapReady = true;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        Button enter_btn = (Button) view.findViewById(R.id.enter_btn);
        TextInputLayout location_layout = (TextInputLayout) view.findViewById(R.id.location_layout);

        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = location_layout.getEditText().getText().toString();
                navigateToString(searchString);
            }
        });

        isOwner = this.getArguments().getBoolean("IS_OWNER");

        if(isOwner){

        }else{ // Borrower
            String location = this.getArguments().getString("STRING_LOCATION");
            if(!location.isEmpty()){
                location_layout.getEditText().setText(location);
                navigateToString(location);
            }
        }

        return view;
    }

    private void navigateToString(String searchString){
        if(!mapReady) return;

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addresses = new ArrayList<>();

        try{
            addresses = geocoder.getFromLocationName(searchString,1); // Gets one result
        }catch (IOException e){
            Log.e("MapsFragment", e.getMessage());
        }
        if (addresses.size() > 0){
            Toast.makeText(getContext(), "Found location", Toast.LENGTH_SHORT).show();
            Address address = addresses.get(0);
            LatLng addLatLng = new LatLng(address.getLatitude(), address.getLongitude());
            Log.d("MapsFragment", address.toString());

            mMap.addMarker(new MarkerOptions().position(addLatLng).title(searchString));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 20f));

            if(isOwner){
                Toast.makeText(getContext(), "Set location", Toast.LENGTH_SHORT).show();
                // Save or send it back
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}