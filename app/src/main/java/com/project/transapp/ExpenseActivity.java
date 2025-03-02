package com.project.transapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import com.project.transapp.Adapter.ExpenseTypeAdapter;
import com.project.transapp.Model.ExpenseType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ExpenseActivity extends AppCompatActivity {
    private TextInputEditText etExpenseName;
    private TextView tv_key;
    private MaterialButton btnSubmit;
    private RecyclerView recyclerView;
    private ExpenseTypeAdapter adapter;
    private List<ExpenseType> expenseTypeList;
    private DatabaseReference databaseReference;
    private String selectedKey = null; // Track if editing an item
    private  Spinner spinnerExpenseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        etExpenseName = findViewById(R.id.et_Expense_name);
        btnSubmit = findViewById(R.id.btn_submit_party);
        recyclerView = findViewById(R.id.recyclerView);

        spinnerExpenseType = findViewById(R.id.spinnerExpenseType);

// Define Expense Types
        String[] expenseTypes = {"Select Type", "Credit", "Debit"};

// Create ArrayAdapter
        ArrayAdapter<String> adapter_dd = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, expenseTypes);

// Attach Adapter to Spinner
        spinnerExpenseType.setAdapter(adapter_dd);


        databaseReference = FirebaseDatabase.getInstance().getReference("expenseType");
        expenseTypeList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpenseTypeAdapter(this, expenseTypeList, position -> editExpenseType(position));
        recyclerView.setAdapter(adapter);

        loadExpenseTypes();

        btnSubmit.setOnClickListener(v -> {
            String name = etExpenseName.getText().toString().trim();
            String type = spinnerExpenseType.getSelectedItem().toString();


            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(type) || type.equals("Select Type")) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                if (name.isEmpty()) {
                    etExpenseName.setError("Please enter name");
                }
                if (type.equals("Select Type"))
                    Toast.makeText(this, "Please Select Type", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();

                return;
            }
            String createdOn = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

            if (selectedKey == null) {
                addExpenseType(name, type, createdOn);
            } else {
                updateExpenseType(selectedKey, name, type, createdOn);
            }

        });
    }

    private void addExpenseType(String name, String type, String createdOn) {
        String key = databaseReference.push().getKey();
        ExpenseType expenseType = new ExpenseType(key, name, type, createdOn);
        if (key != null) {
            databaseReference.child(key).setValue(expenseType).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show();
                    etExpenseName.setText("");
                    spinnerExpenseType.setSelection(0);
                    //etExpenseType.setText("");
                }
            });
        }
    }

    private void updateExpenseType(String key, String name, String type, String createdOn) {
        ExpenseType updatedExpense = new ExpenseType(key, name, type, createdOn);
        databaseReference.child(key).setValue(updatedExpense).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Expense updated", Toast.LENGTH_SHORT).show();
                selectedKey = null;
                etExpenseName.setText("");
                spinnerExpenseType.setSelection(0);
                btnSubmit.setText("Add Expense");
            }
        });
    }

    private void loadExpenseTypes() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                expenseTypeList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    ExpenseType expenseType = data.getValue(ExpenseType.class);
                    if (expenseType != null) {
                        expenseTypeList.add(expenseType);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ExpenseActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editExpenseType(int position) {
        ExpenseType expenseType = expenseTypeList.get(position);
        etExpenseName.setText(expenseType.getText());
        spinnerExpenseType.setSelection(Objects.equals(expenseType.getType(), "Credit") ? 1 : 2);
        //   etExpenseType.setText(expenseType.getType());
        selectedKey = expenseType.getKey();
        btnSubmit.setText("Update Expense");
    }
}
