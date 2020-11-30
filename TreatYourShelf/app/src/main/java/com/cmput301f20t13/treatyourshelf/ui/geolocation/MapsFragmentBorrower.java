package com.cmput301f20t13.treatyourshelf.ui.geolocation;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


// Referenced https://www.youtube.com/watch?v=MWowf5SkiOE&ab_channel=CodingWithMitch

public class MapsFragmentBorrower extends Fragment {


    private GoogleMap mMap;
    private boolean mapReady = false;
    private LatLng markerAddress;
    private TextView address_tv;

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
            navigateToMarkerAddress();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps_borrower, container, false);

        Button return_btn = (Button) view.findViewById(R.id.return_btn);
        address_tv = (TextView) view.findViewById(R.id.address_tv);


        // Get latLat passed to it.
        assert this.getArguments() != null;
        String[] coordString = (this.getArguments().getString("COORD")).split(",");
        float lat = 0;
        float lon = 0;
        lat = Float.parseFloat(coordString[0]);
        lon = Float.parseFloat(coordString[1]);

        markerAddress = new LatLng(lat, lon);

        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Returning...", Toast.LENGTH_SHORT).show();
                // Return when clicked return. The address is saved on:
                //if(beenSet) double lat = markerAddress.latitude; double lon = markerAddress.longitude;
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
            }
        });

        return view;
    }

    private void navigateToMarkerAddress(){
        mMap.addMarker(new MarkerOptions().position(markerAddress).title("Recieve Book Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerAddress, mMap.getMaxZoomLevel()));
        address_tv.setText(markerAddress.toString());
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