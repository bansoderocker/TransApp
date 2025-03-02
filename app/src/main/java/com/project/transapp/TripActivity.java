package com.project.transapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.transapp.Adapter.BillAdapter;
import com.project.transapp.Model.Trip;
import java.util.ArrayList;
import java.util.List;

public class TripActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BillAdapter adapter;
    private List<Trip> tripList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Trip");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trip);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample Data
        tripList = new ArrayList<>();
//        billList.add(new Bill("TR - 02", "19-02-2022", "MH46AR6253", "Kalamboli To Malad", 7100));
//        billList.add(new Bill("AV - 1093", "12-12-2023", "3956", "Kalamboli To Thane (RKB Global)", 6400));
//        billList.add(new Bill("AV - 1096", "22-12-2023", "MH46AR5578", "Kalamboli To Mulund", 6500));
//        billList.add(new Bill("AV - 2489", "03-02-2024", "MH46AR6253", "Kalamboli To Thane", 6248));
//        billList.add(new Bill("AV - 1087", "11-12-2023", "MH46AR5578", "Kalamboli To Vileparle", 6900));
//        billList.add(new Bill("TR - 2479", "13-12-2023", "MH46AR6253", "Kalamboli To Ambernath", 5600));
//        billList.add(new Bill("TR - 2483", "18-12-2023", "4282", "Kalamboli To Chembur", 8565));

        adapter = new BillAdapter(getApplicationContext(), tripList);
        recyclerView.setAdapter(adapter);


//        myRef.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Log.e("dataSnapshot",dataSnapshot.toString());
//                    Trip trip = dataSnapshot.getValue(Trip.class);
//                    assert trip != null;
//                    Log.e("bill", trip.toString());
//                    tripList.add(trip);
//                }
//                adapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}