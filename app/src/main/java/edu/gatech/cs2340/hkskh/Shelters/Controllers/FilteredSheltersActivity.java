package edu.gatech.cs2340.hkskh.Shelters.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import edu.gatech.cs2340.hkskh.Controllers.MainActivity;
import edu.gatech.cs2340.hkskh.Database.AppDatabase;
import edu.gatech.cs2340.hkskh.R;
import edu.gatech.cs2340.hkskh.Shelters.Models.Shelter;
import edu.gatech.cs2340.hkskh.Shelters.SearchService;
import edu.gatech.cs2340.hkskh.Shelters.ShelterManager;

public class FilteredSheltersActivity extends AppCompatActivity {

    private String userName;
    private AppDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_shelters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mdb = AppDatabase.getInMemoryDatabase(getApplicationContext());

        userName = this.getIntent().getStringExtra("Username");
        RecyclerView filteredList = (RecyclerView) findViewById(R.id.filtered_recycler);
        setupRecyclerView(filteredList);

        LinearLayoutManager newLayoutManager = new LinearLayoutManager(this);
        filteredList.setLayoutManager(newLayoutManager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class).putExtra("Username", userName));
            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        SearchService search = new SearchService(this.mdb);

        //get the desired search type and filter
        String searchRequested = this.getIntent().getStringExtra("Search Type");
        String filter = this.getIntent().getStringExtra("Filter");

        ArrayList<Shelter> list;

        //put in try-catch because the search methods throw errors
        try {
            list = (ArrayList) search.searchChoices(searchRequested, filter);
        } catch (IllegalArgumentException exception){
            list = new ArrayList<>();
        } catch (NoSuchElementException exception) {
            list = new ArrayList<>();
        }

        recyclerView.setAdapter(new SearchShelterRecyclerViewAdapter(list, searchRequested, filter));
    }

    public class SearchShelterRecyclerViewAdapter
            extends RecyclerView.Adapter<SearchShelterRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the items to be shown in this list.
         */
        private final List<Shelter> shelterList;
        private final String searchType;
        private final String filter;

        /**
         * set the items to be used by the adapter
         * @param items the list of items to be displayed in the recycler view
         */
        public SearchShelterRecyclerViewAdapter(List<Shelter> items, String searchType, String filter) {
            shelterList = items;
            this.searchType = searchType;
            this.filter = filter;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*

              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.filtered_recycler_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            //start by getting the element at the correct position
            holder.shelter = shelterList.get(position);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            holder.nameView.setText("" + ((Shelter)(shelterList.get(position))).getName());


            /*
             * set up a listener to handle if the user clicks on this list item, what should happen?
             */
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //on a phone, we need to change windows to the detail view
                    Context context = v.getContext();
                    //create our new intent with the new screen (activity)
                    Intent intent = new Intent(context, ShelterDetailActivity.class);
                        /*
                            pass along the id of the shelter so we can retrieve the correct data in
                            the next window
                         */
                    intent.putExtra("shelter hash key", ((Shelter) holder.shelter).hashCode());
                    //Give info to next screen about this activity
                    intent.putExtra("Previous Screen", "filtered list");
                    //pass on username
                    intent.putExtra("Username", userName);
                    //we need to let the details page keep track of the search type and filter so it can
                    //so we can regenerate the list if the user hits back on the details page
                    intent.putExtra("Search Type", searchType);
                    intent.putExtra("Filter", filter);

                    //now just display the new window
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
            public Shelter shelter;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                nameView = (TextView) view.findViewById(R.id.filtered_recycler_item);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + nameView.getText() + "'";
            }
        }
    }



}
