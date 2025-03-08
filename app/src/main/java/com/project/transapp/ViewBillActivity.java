package com.project.transapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.transapp.Adapter.TripAdapter;
import com.project.transapp.Model.Trip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;


public class ViewBillActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private TripAdapter tripAdapter;
    private List<Trip> tripList;
    private List<Trip> originalTripList;

    private List<Trip> filteredList = new ArrayList<>();

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("Trip");

    private DrawerLayout drawerLayout;

    private EditText etDate, etParty, etTruck, etLocation;
    private Button btnApplyFilter;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);

        drawerLayout = findViewById(R.id.drawer_layout);
        // Setup Toolbar with Drawer Toggle
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        // bill date picker - start
        etDate = headerView.findViewById(R.id.etDate);
        Calendar calendar = Calendar.getInstance();
        // etDate.setText(sdf.format(calendar.getTime()));
        etDate.setOnClickListener(v -> openDatePicker());
        // bill date picker - end

        etParty = headerView.findViewById(R.id.etParty);
        etTruck = headerView.findViewById(R.id.etTruck);
        etLocation = headerView.findViewById(R.id.etLocation);
        btnApplyFilter = headerView.findViewById(R.id.btnApplyFilter);


        recyclerView = findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tripList = new ArrayList<>();
        originalTripList = new ArrayList<>();


        recyclerView = findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tripAdapter = new TripAdapter(tripList);
        recyclerView.setAdapter(tripAdapter);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Trip trip = dataSnapshot.getValue(Trip.class);
                    assert trip != null;
                    Log.e("bill", trip.toString());
                    tripList.add(trip);
                }
                originalTripList.addAll(tripList);
                tripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tripAdapter = new TripAdapter(tripList);
        recyclerView.setAdapter(tripAdapter);

        btnApplyFilter.setOnClickListener(v -> applyFilter());

    }

    private void openDatePicker() {
        // Build Date Picker
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // Default to today
                .setCalendarConstraints(new CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now()) // Only future dates
                        .build()).build();

        // Show Picker
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        // Get Selected Date
        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            etDate.setText(sdf.format(selection)); // Set formatted date
        });
    }

    private void applyFilter() {

        String dateFilter = etDate.getText().toString().trim().toLowerCase();
        String partyFilter = etParty.getText().toString().trim().toLowerCase();
        String truckFilter = etTruck.getText().toString().trim().toLowerCase();
        String locationFilter = etLocation.getText().toString().trim().toLowerCase();
        boolean isFiterHasValue = false;
        if (!dateFilter.isEmpty() || !partyFilter.isEmpty() || !truckFilter.isEmpty() || !locationFilter.isEmpty()) {
            isFiterHasValue = true;
        }
        if (isFiterHasValue) {
            filteredList.clear();

            for (Trip trip : originalTripList) {
                if ((partyFilter.isEmpty() || trip.getPartyName().toLowerCase().contains(partyFilter)) &&
                        (truckFilter.isEmpty() || trip.getTruckNumber().toLowerCase().contains(truckFilter)) &&
                        (locationFilter.isEmpty() || trip.getFrom().toLowerCase().contains(locationFilter) || trip.getTo().toLowerCase().contains(locationFilter))) {
                    filteredList.add(trip);
                }
            }

            tripAdapter.updateList(filteredList); // Update RecyclerView
        } else {
            tripAdapter.updateList(originalTripList); // Update RecyclerView
        }
    }
}