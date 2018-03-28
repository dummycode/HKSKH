package edu.gatech.cs2340.hkskh.Shelters.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.gatech.cs2340.hkskh.Controllers.MainActivity;
import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Shelters.ShelterManager;

public class ShelterListActivity extends AppCompatActivity {

    private AppDatabase adb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.adb = AppDatabase.getDatabase(getApplicationContext());

        setContentView(R.layout.activity_shelter_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView shelterRecycler = (RecyclerView) findViewById(R.id.course_list_recycler);
        setupRecyclerView(shelterRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        shelterRecycler.setLayoutManager(layoutManager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        ShelterManager shelterManager = new ShelterManager(this.adb);
        Collection<Shelter> values = shelterManager.getAll();
        ArrayList<Shelter> shelterList = new ArrayList<>(values);
        recyclerView.setAdapter(new SimpleShelterRecyclerViewAdapter(shelterList));
    }

    public class SimpleShelterRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleShelterRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the items to be shown in this list.
         */
        private final List<Shelter> shelterList;

        /**
         * set the items to be used by the adapter
         * @param items the list of items to be displayed in the recycler view
         */
        public SimpleShelterRecyclerViewAdapter(List<Shelter> items) {
            shelterList = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*

              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_shelter_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            // Start by getting the element at the correct position
            holder.shelter = shelterList.get(position);

            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            holder.nameView.setText(shelterList.get(position).getName());
            holder.idView.setText("" + shelterList.get(position).getId());

            /*
             * set up a listener to handle if the user clicks on this list item, what should happen?
             */
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        // On a phone, we need to change windows to the detail view
                        Context context = v.getContext();
                        // Create our new intent with the new screen (activity)
                        Intent intent = new Intent(context, ShelterDetailActivity.class);

                        // Pass along data
                        intent.putExtra("shelterId", holder.shelter.getId());
                        intent.putExtra("Previous Screen", "full list");

                        // Now just display the new window
                        context.startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return shelterList.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Course) and the widgets in
         * the list view (in this case the two TextView)
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView nameView;
            public final TextView idView;
            public Shelter shelter;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                nameView = (TextView) view.findViewById(R.id.shelter_list_item);
                idView = (TextView) view.findViewById(R.id.shelter_list_id);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + nameView.getText() + "'";
            }
        }
    }

}
