package edu.gatech.cs2340.hkskh.Shelters.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.gatech.cs2340.hkskh.Controllers.MainActivity;
import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.R.id;
import edu.gatech.cs2340.hkskh.R.layout;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Shelters.ShelterManager;

public class ShelterListActivity extends AppCompatActivity {

    private ArrayList<Shelter> shelters;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the database
        AppDatabase adb = AppDatabase.getDatabase(getApplicationContext());
        ShelterManager shelterManager = new ShelterManager(adb);

        // Get shelters from intent
        if (getIntent().hasExtra("shelters")) {
            shelters = getIntent().getParcelableArrayListExtra("shelters");
        } else {
            shelters = new ArrayList(shelterManager.getAll());
        }

        setContentView(layout.activity_shelter_list);
        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView shelterRecycler = findViewById(id.course_list_recycler);
        shelterRecycler.setAdapter(new ShelterListActivity.SimpleShelterRecyclerViewAdapter());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        shelterRecycler.setLayoutManager(layoutManager);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });

        AppCompatButton map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent toFilteredList =
                        new Intent(ShelterListActivity.this, MapsActivity.class);
                // Pass the search type as an extra
                // to the next screen so we can make the list to display
                toFilteredList.putParcelableArrayListExtra("shelters", shelters);

                startActivity(toFilteredList);
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

    }

    public class SimpleShelterRecyclerViewAdapter
            extends
            RecyclerView.Adapter<ShelterListActivity.SimpleShelterRecyclerViewAdapter.ViewHolder> {


        @Override
        @NonNull
        public ShelterListActivity.SimpleShelterRecyclerViewAdapter.ViewHolder
        onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            /*

              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_shelter_list, parent, false);
            return new ShelterListActivity.SimpleShelterRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final
                                             ShelterListActivity.SimpleShelterRecyclerViewAdapter.ViewHolder holder,
                                     int position) {
            /*
            This is where we have to bind each data element
            in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            // Start by getting the element at the correct position
            holder.shelter = shelters.get(position);

            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              TextView and the string rep of a course in the other.
             */
            holder.nameView.setText(shelters.get(position).getName());
            holder.idView.setText("" + shelters.get(position).getId());

            /*
             * set up a listener to handle if the user clicks on this list item, what should happen?
             */
            holder.mView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                        // On a phone, we need to change windows to the detail view
                        Context context = v.getContext();
                        // Create our new intent with the new screen (activity)
                        Intent intent = new Intent(context, ShelterDetailActivity.class);

                        // Pass along data
                        intent.putExtra("shelterId", holder.shelter.getId());
                        intent.putExtra("Previous Screen", "full list");

                        // Pass the search type as an extra to the next screen so we pass it back
                        intent.putParcelableArrayListExtra("shelters", shelters);

                        // Now just display the new window
                        context.startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return shelters.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Course) and the widgets in
         * the list view (in this case the two TextView)
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;
            final TextView nameView;
            final TextView idView;
            public Shelter shelter;

            ViewHolder(View view) {
                super(view);
                mView = view;
                nameView = view.findViewById(id.shelter_list_item);
                idView = view.findViewById(id.shelter_list_id);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + nameView.getText() + "'";
            }
        }
    }

}
