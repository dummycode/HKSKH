package edu.gatech.cs2340.hkskh.Shelters.Controllers;

import android.content.Intent;
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
import edu.gatech.cs2340.hkskh.R.id;
import edu.gatech.cs2340.hkskh.R.layout;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Shelters.ShelterManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private List<Shelter> shelters;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(id.map);

        // Retrieve the database
        AppDatabase adb = AppDatabase.getDatabase(getApplicationContext());
        ShelterManager shelterManager = new ShelterManager(adb);

        Intent currentIntent = getIntent();

        // Get shelters from intent
        if (currentIntent.hasExtra("shelters")) {
            shelters = currentIntent.getParcelableArrayListExtra("shelters");
        } else {
            shelters = new ArrayList(shelterManager.getAll());
        }

        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        int ZOOM = 12;

        GoogleMap mMap = googleMap;

        LatLng location;

        // Go through each shelter, adding a marker to the lat/long of the shelter, adding the name as a title,
        // and the address as the snippet
        for (Shelter shelter : shelters) {
            String title = shelter.getMapTitle();
            location = new LatLng(shelter.getLatitude(), shelter.getLongitude());
            MarkerOptions marker = new MarkerOptions();
            marker.position(location);
            marker.title(title);
            marker.snippet(shelter.getAddress());
            mMap.addMarker(marker);
        }

        // If the list is not empty, set the view to the location of the first shelter
        if (!shelters.isEmpty()) {
            Shelter firstShelter = shelters.get(0);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(
                    new LatLng(firstShelter.getLatitude(), firstShelter.getLongitude())
            ));
        }

        mMap.moveCamera(CameraUpdateFactory.zoomTo(ZOOM));
    }
}
