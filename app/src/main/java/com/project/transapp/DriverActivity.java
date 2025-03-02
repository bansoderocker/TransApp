package com.project.transapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.transapp.Adapter.DriverAdapter;
import com.project.transapp.Model.Driver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class DriverActivity extends AppCompatActivity {
    private TextInputEditText etDriverName, etLicenseNumber, etContactNumber;
    private TextView tv_Key;
    private MaterialButton btnSubmit;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private DriverAdapter adapter;
    private List<Driver> driverList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        tv_Key = findViewById(R.id.tv_key);
        etDriverName = findViewById(R.id.et_driver_name);
        etLicenseNumber = findViewById(R.id.et_license_number);
        etContactNumber = findViewById(R.id.et_contact_number);
        btnSubmit = findViewById(R.id.btn_submit_driver);

        databaseReference = FirebaseDatabase.getInstance().getReference("driver");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnSubmit.setOnClickListener(v -> {
            String driverName = etDriverName.getText().toString().trim();
            String licenseNumber = etLicenseNumber.getText().toString().trim();
            String contactNumber = etContactNumber.getText().toString().trim();
            String existingKey = Objects.requireNonNull(tv_Key.getText()).toString().trim();

            if (driverName.isEmpty() || licenseNumber.isEmpty() || contactNumber.length() != 10) {
                Toast.makeText(DriverActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                if (driverName.isEmpty()) {
                    etDriverName.setError("Please enter driver name");
                }
                if (licenseNumber.isEmpty()) {
                    etLicenseNumber.setError("Please enter license number");
                }
                if (contactNumber.length() != 10) {
                    etContactNumber.setError("Please enter contact number");
                }
            } else {
                String key = "driver-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
                String createdOn = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

                if (!existingKey.isEmpty()) key = existingKey;

                Driver driver = new Driver(key, driverName, licenseNumber, contactNumber, createdOn);

                databaseReference.child(key).setValue(driver).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(DriverActivity.this, "Driver Added Successfully!", Toast.LENGTH_SHORT).show();
                        etDriverName.setText("");
                        etLicenseNumber.setText("");
                        etContactNumber.setText("");
                        tv_Key.setText("");
                        btnSubmit.setText("ADD DRIVER");
                    } else {
                        Toast.makeText(DriverActivity.this, "Failed to add driver", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        driverList = new ArrayList<>();
        adapter = new DriverAdapter(getApplicationContext(), driverList, position -> onEdit(position));
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                driverList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Driver driver = dataSnapshot.getValue(Driver.class);
                    assert driver != null;
                    driverList.add(driver);
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
        Driver obj = driverList.get(position);

        tv_Key.setText(obj.getKey());
        etDriverName.setText(obj.getDriverName());
        etLicenseNumber.setText(obj.getLicenseNumber());
        etContactNumber.setText(obj.getContactNumber());

        btnSubmit.setText("UPDATE DRIVER");
    }
}
