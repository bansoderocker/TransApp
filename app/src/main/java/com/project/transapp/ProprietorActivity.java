package com.project.transapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.project.transapp.Adapter.ProprietorAdapter;
import com.project.transapp.Model.Proprietor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class ProprietorActivity extends AppCompatActivity {
    private TextInputEditText etProprietorName, etContactNumber, etAddress;
    private TextView tv_Key;
    private MaterialButton btnSubmit;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ProprietorAdapter adapter;
    private List<Proprietor> proprietorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propriter);

        // Initialize Views
        tv_Key = findViewById(R.id.tv_key);
        etProprietorName = findViewById(R.id.et_proprietor_name);
        etContactNumber = findViewById(R.id.et_contact_number);
        etAddress = findViewById(R.id.et_address);
        btnSubmit = findViewById(R.id.btn_submit_proprietor);

        databaseReference = FirebaseDatabase.getInstance().getReference("proprietor");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Button Click Listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String proprietorName = etProprietorName.getText().toString().trim();
                String contactNumber = etContactNumber.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String existingKey = Objects.requireNonNull(tv_Key.getText()).toString().trim();

                if (proprietorName.isEmpty() || contactNumber.isEmpty()) {
                    Toast.makeText(ProprietorActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    if(proprietorName.isEmpty() ){
                        etProprietorName.setError("Please enter Proprietor Name");
                    }
                    if(contactNumber.isEmpty()){
                        etContactNumber.setError("Please enter Contact Number");
                    }
                } else {
                    String key = "proprietor-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
                    String createdOn = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

                    if (!existingKey.isEmpty()) key = existingKey;

                    // Create Proprietor Object
                    Proprietor proprietor = new Proprietor(key, proprietorName, contactNumber, address,createdOn);

                    // Insert into Firebase
                    databaseReference.child(key).setValue(proprietor).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProprietorActivity.this, "Proprietor Added Successfully!", Toast.LENGTH_SHORT).show();
                            etProprietorName.setText("");
                            etContactNumber.setText("");
                            etAddress.setText("");
                            tv_Key.setText("");
                            btnSubmit.setText("ADD PROPRIETOR");
                        } else {
                            Toast.makeText(ProprietorActivity.this, "Failed to add proprietor", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        proprietorList = new ArrayList<>();
        adapter = new ProprietorAdapter(getApplicationContext(), proprietorList, position -> onEdit(position));
        recyclerView.setAdapter(adapter);

        // Fetch data from Firebase and sort
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                proprietorList.clear();
                List<Proprietor> tempProprietorList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Proprietor proprietor = dataSnapshot.getValue(Proprietor.class);
                    assert proprietor != null;
                    tempProprietorList.add(proprietor);
                }

                // Sort by created date (if needed)
                Collections.sort(tempProprietorList, new Comparator<Proprietor>() {
                    @Override
                    public int compare(Proprietor p1, Proprietor p2) {
                        return p1.getCreatedOn().compareTo(p2.getCreatedOn());
                    }
                });

                proprietorList.addAll(tempProprietorList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Data fetch cancelled", error.toException());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void onEdit(int position) {
        Proprietor obj = proprietorList.get(position);

        tv_Key.setText(obj.getKey());
        etProprietorName.setText(obj.getProprietorName());
        etContactNumber.setText(obj.getContactNumber());
        etAddress.setText(obj.getAddress());

        btnSubmit.setText("UPDATE PROPRIETOR");
    }
}
