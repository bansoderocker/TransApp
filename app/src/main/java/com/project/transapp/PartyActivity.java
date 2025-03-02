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
import com.project.transapp.Adapter.PartyAdapter;
import com.project.transapp.Model.Party;
import com.project.transapp.Model.Truck;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class PartyActivity extends AppCompatActivity {
    private TextInputEditText etPartyName, etPageNumber, etRemark;
    private TextView tv_Key;

    private MaterialButton btnSubmit;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private PartyAdapter adapter;

    private List<Party> partyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        // Initialize Views
        tv_Key = findViewById(R.id.tv_key);
        etPartyName = findViewById(R.id.et_party_name);
        etPageNumber = findViewById(R.id.et_page_number);
        etRemark = findViewById(R.id.et_remark);
        btnSubmit = findViewById(R.id.btn_submit_party);

        databaseReference = FirebaseDatabase.getInstance().getReference("party");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Button Click Listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String partyName = etPartyName.getText().toString().trim();
                String pageNumber = etPageNumber.getText().toString().trim();
                String remark = etRemark.getText().toString().trim();
                String existingKey = Objects.requireNonNull(tv_Key.getText()).toString().trim();

                if (partyName.isEmpty() || pageNumber.isEmpty()) {
                    Toast.makeText(PartyActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {

                    // TODO: Save data to database or perform required action
                    String key = "party-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);

                    String createdOn = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
                    if (!existingKey.isEmpty()) key = existingKey;

                    // Create Party Object
                    Party party = new Party(key, createdOn, pageNumber, partyName, remark);

                    // Insert into Firebase
                    databaseReference.child(key).setValue(party).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(PartyActivity.this, "Party Added Successfully!", Toast.LENGTH_SHORT).show();
                            etPartyName.setText("");
                            etPageNumber.setText("");
                            etRemark.setText("");
                            tv_Key.setText("");
                        } else {
                            Toast.makeText(PartyActivity.this, "Failed to add party", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Toast.makeText(PartyActivity.this, "Party Added Successfully!", Toast.LENGTH_SHORT).show();
                    // finish(); // Close the activity
                }
            }
        });
        partyList = new ArrayList<>();
        Collections.sort(partyList, new Comparator<Party>() {
            @Override
            public int compare(Party p1, Party p2) {
                return Integer.compare(Integer.parseInt(p1.getPageNumber()), Integer.parseInt(p2.getPageNumber()));
            }
        });
        adapter = new PartyAdapter(getApplicationContext(), partyList, position -> onEdit(position));
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                partyList.clear();
                List<Party> tempPartyList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //  Log.e("dataSnapshot", dataSnapshot.toString());
                    Party trip = dataSnapshot.getValue(Party.class);
                    assert trip != null;
                    tempPartyList.add(trip);
                }
                //Log.e("partyList", partyList.toString());
                Collections.sort(tempPartyList, new Comparator<Party>() {
                    @Override
                    public int compare(Party p1, Party p2) {
                        int pageNum1 = p1.getPageNumber() == null || p1.getPageNumber().isEmpty() ? 0 : Integer.parseInt(p1.getPageNumber());
                        int pageNum2 = p2.getPageNumber() == null || p2.getPageNumber().isEmpty() ? 0 : Integer.parseInt(p2.getPageNumber());
                        return Integer.compare(pageNum1, pageNum2);
                    }
                });
                partyList.addAll(tempPartyList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void onEdit(int position) {
        Party obj = partyList.get(position);

        tv_Key.setText(obj.getKey());
        etPartyName.setText(obj.getPartyName());
        etPageNumber.setText(obj.getPageNumber());
        etRemark.setText(obj.getRemark());

        btnSubmit.setText("UPDATE PARTY");
    }

}