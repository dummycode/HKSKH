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
    private List<Shelter> shelters;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        // Retrieve the database
        AppDatabase adb = AppDatabase.getDatabase(getApplicationContext());
        ShelterManager shelterManager = new ShelterManager(adb);

        // Get shelters from intent
        if (getIntent().hasExtra("shelters")) {
            this.shelters = getIntent().getParcelableArrayListExtra("shelters");
        } else {
            this.shelters = new ArrayList(shelterManager.getAll());
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
        mMap = googleMap;

        LatLng location;

        // Go through each shelter, adding a marker to the lat/long of the shelter, adding the name as a title,
        // and the address as the snippet
        for (Shelter shelter : this.shelters) {
            location = new LatLng(shelter.getLatitude(), shelter.getLongitude());
            mMap.addMarker(
                    new MarkerOptions().position(location)
                            .title(shelter.getName() + " - " + shelter.getPhoneNumber())
                            .snippet(shelter.getAddress())
            );
        }

        // if the list is not empty, set the view to the location of the first shelter
        if (!shelters.isEmpty()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(shelters.get(0).getLatitude(),
                    shelters.get(0).getLongitude())));
        }
        mMap.moveCamera(CameraUpdateFactory.zoomTo(11));
    }
}
