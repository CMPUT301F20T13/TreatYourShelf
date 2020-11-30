package com.cmput301f20t13.treatyourshelf.ui.geolocation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

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
import com.cmput301f20t13.treatyourshelf.Utils;
import com.cmput301f20t13.treatyourshelf.data.Notification;
import com.cmput301f20t13.treatyourshelf.ui.RequestDetails.RequestDetailsFragmentDirections;
import com.cmput301f20t13.treatyourshelf.ui.RequestDetails.RequestDetailsViewModel;
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

public class MapsFragmentOwner extends Fragment {


    private GoogleMap mMap;
    private boolean mapReady = false;
    private boolean hasBeenSet = false;
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
            mapReady = true;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps_owner, container, false);

        Button enter_btn = (Button) view.findViewById(R.id.enter_btn);
        Button return_btn = (Button) view.findViewById(R.id.return_btn);
        TextInputLayout location_layout = (TextInputLayout) view.findViewById(R.id.location_layout);
        address_tv = (TextView) view.findViewById(R.id.address_tv);

        assert this.getArguments() != null;
        String isbnString = this.getArguments().getString("ISBN");
        String requesterString = this.getArguments().getString("REQUESTER");

        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboardFrom(requireContext(), v);
                String searchString = location_layout.getEditText().getText().toString();
                navigateToString(searchString);
            }
        });

        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return when clicked return. The address is saved on:
                RequestDetailsViewModel requestDetailsViewModel = new ViewModelProvider(requireActivity())
                        .get(RequestDetailsViewModel.class);

                float lat = 0;
                float lon = 0;
                if(hasBeenSet) {
                    lat = (float)markerAddress.latitude;
                    lon = (float)markerAddress.longitude;
                }
                String coordinates = String.valueOf(lat) + "," + String.valueOf(lon);
                requestDetailsViewModel.setLocation(isbnString, requesterString, coordinates);

                Notification notification =
                        new Notification("Request Accepted", "Yay :)", Utils.emailStripper(requesterString));
                Utils.sendNotification(notification.getNotification(), requireContext());
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
            }
        });

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

        // Sets the address to the first location found
        if (addresses.size() > 0){
            if(hasBeenSet){
                Toast.makeText(getContext(), "Changed location, to new marker.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Set location.", Toast.LENGTH_SHORT).show();
                hasBeenSet = true;
            }
            Address address = addresses.get(0);
            markerAddress = new LatLng(address.getLatitude(), address.getLongitude());
            address_tv.setText(markerAddress.toString());

            mMap.addMarker(new MarkerOptions().position(markerAddress).title(searchString));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), mMap.getMaxZoomLevel()));
        }else{
            Toast.makeText(getContext(), "No location found.", Toast.LENGTH_SHORT).show();
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