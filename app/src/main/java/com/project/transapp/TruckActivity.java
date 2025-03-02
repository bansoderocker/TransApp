package com.project.transapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.transapp.Adapter.TruckAdapter;
import com.project.transapp.Model.Truck;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class TruckActivity extends AppCompatActivity {
    private TextInputEditText etTruckNumber, etRemark;
    private TextView tv_truckKey;
    private MaterialButton btnSubmit;
    private DatabaseReference databaseReference;
    private TruckAdapter adapter;

    private List<Truck> truckList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck);
        // Initialize Views
        etTruckNumber = findViewById(R.id.et_truck_number);
        etRemark = findViewById(R.id.et_remark);
        tv_truckKey = findViewById(R.id.tv_truck_key);
        btnSubmit = findViewById(R.id.btn_submit_truck);

        databaseReference = FirebaseDatabase.getInstance().getReference("truck");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Button Click Listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TruckNumber = Objects.requireNonNull(etTruckNumber.getText()).toString().trim();
                String remark = Objects.requireNonNull(etRemark.getText()).toString().trim();
                String existingKey = Objects.requireNonNull(tv_truckKey.getText()).toString().trim();

                if (TruckNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {

                    // TODO: Save data to database or perform required action
                    String createdOn = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
                    String key = "truck-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
                    if(!existingKey.isEmpty()) key = existingKey;

                    // Create Truck Object
                    Truck truck = new Truck(key,TruckNumber,remark,true);

                    // Insert into Firebase
                    databaseReference.child(key).setValue(truck).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Truck Added Successfully!", Toast.LENGTH_SHORT).show();
                            etTruckNumber.setText("");
                            etRemark.setText("");
                            tv_truckKey.setText("");
                            btnSubmit.setText("ADD TRUCK");
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to add party", Toast.LENGTH_SHORT).show();
                        }
                    });

                   // Toast.makeText(getApplicationContext(), "Truck Added Successfully!", Toast.LENGTH_SHORT).show();
                  //  finish(); // Close the activity
                }
            }
        });
        truckList = new ArrayList<>();
        adapter = new TruckAdapter(getApplicationContext(), truckList,position -> onEdit(position));
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                truckList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.e("dataSnapshot", dataSnapshot.toString());
                    Truck trip = dataSnapshot.getValue(Truck.class);
                    assert trip != null;
                    truckList.add(trip);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void onEdit(int position) {
        Truck truck = truckList.get(position);
        etTruckNumber.setText(truck.getTruckNumber());
        etRemark.setText(truck.getRemark());
        tv_truckKey.setText(truck.getKey());
        btnSubmit.setText("UPDATE TRUCK");
    }

}