package edu.gatech.cs2340.hkskh.Shelters.Controllers;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Shelters.ShelterManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        //Retrieve the database
        AppDatabase adb = AppDatabase.getDatabase(getApplicationContext());

        //Create an iterable list out of the collection of shelters
        ShelterManager load = new ShelterManager(adb);
        List<Shelter> shelters = new ArrayList(load.getAll());

        LatLng location;

        // Go through each shelter, adding a marker to the lat/long of the shelter, adding the name as a title,
        // and the address as the snippet
        for (Shelter shelter: shelters) {
            location = new LatLng(shelter.getLatitude(), shelter.getLongitude());
            mMap.addMarker(new MarkerOptions().position(location).title(shelter.getName()).snippet(shelter.getAddress()));
        }

        // if the list is not empty, set the view to the location of the first shelter
        if (!shelters.isEmpty()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(shelters.get(0).getLatitude(),
                    shelters.get(0).getLongitude())));
        }
        mMap.moveCamera(CameraUpdateFactory.zoomTo(11));
    }
}
