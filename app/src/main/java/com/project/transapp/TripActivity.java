package com.project.transapp;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.transapp.Model.ExpenseType;
import com.project.transapp.Model.Party;
import com.project.transapp.Model.Proprietor;

import com.project.transapp.Model.Trip;
import com.project.transapp.Model.Truck;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class TripActivity extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("Trip");
    private DatabaseReference proprietorRef;
    private AutoCompleteTextView autoCompleteProprietor, autoCompleteParty, autoCompleteTruck, autoCompleteFrom, autoCompleteTo, autoCompleteExtraDelivery;
    List<String> nameList = new ArrayList<>();
    List<String> partyNameList = new ArrayList<>();
    List<String> expenseTypeNameList = new ArrayList<>();
    List<Party> partyList = new ArrayList<>();
    List<ExpenseType> expenseTypeList = new ArrayList<>();

    List<Truck> truckList = new ArrayList<>();
    List<String> truckNumberList = new ArrayList<>();
    ArrayAdapter<String> adapter_P, adapter_party, adapter_truck, adapter_expenseType;
    private TextInputEditText etDate, et_bill_number, et_weight, et_rate, et_fix_Amount, et_amount;
    AutoCompleteTextView autoCompleteExpenseType;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private ImageView iv_add_extra_delivery;
    private TextInputLayout layout_extraDelivery;

    private boolean isSelfTruckBoolean = true;

    private TextView tvTotalExpense;
    private double totalExpense = 0; // Track total amount


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        autoCompleteProprietor = findViewById(R.id.autoCompleteProprietor);
        getProprietorNameList();
        adapter_P = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, nameList);
        autoCompleteProprietor.setAdapter(adapter_P);
        autoCompleteProprietor.setThreshold(1);

        // party Auto complete text spinner - start
        autoCompleteParty = findViewById(R.id.autoCompleteParty);
        getPartyNameList();
        adapter_party = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, partyNameList);
        autoCompleteParty.setAdapter(adapter_party);
        autoCompleteParty.setThreshold(1);
        // party Auto complete text spinner - end

        getExpenseTypeList();
        autoCompleteExpenseType = findViewById(R.id.autoCompleteExpenseType);
        adapter_expenseType = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, expenseTypeNameList);
        autoCompleteExpenseType.setAdapter(adapter_expenseType);
        // autoCompleteExpenseType.setThreshold(1);
        // Prevent Keyboard Popup & Show Dropdown on Single Click
        autoCompleteExpenseType.setOnClickListener(v -> autoCompleteExpenseType.showDropDown());
        autoCompleteExpenseType.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                autoCompleteExpenseType.showDropDown();
            }
        });
        // bill date picker - start
        etDate = findViewById(R.id.et_date);
        Calendar calendar = Calendar.getInstance();
        etDate.setText(sdf.format(calendar.getTime()));
        etDate.setOnClickListener(v -> openDatePicker());
        // bill date picker - end
        autoCompleteTruck = findViewById(R.id.autoCompleteTruck);
        iv_add_extra_delivery = findViewById(R.id.iv_add_extra_delivery);
        layout_extraDelivery = findViewById(R.id.layout_extraDelivery);
        MaterialButton btn_submit = findViewById(R.id.btn_submit_trip);
        et_bill_number = findViewById(R.id.et_bill_number);
        autoCompleteFrom = findViewById(R.id.autoCompleteFrom);
        autoCompleteTo = findViewById(R.id.autoCompleteTo);
        autoCompleteExtraDelivery = findViewById(R.id.autoCompleteExtraDelivery);
        et_weight = findViewById(R.id.et_weight);
        et_rate = findViewById(R.id.et_rate);
        et_fix_Amount = findViewById(R.id.et_fix_Amount);
        et_amount = findViewById(R.id.et_amount);

        // truck list -start
        getTruckNumberList();
        adapter_truck = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, truckNumberList);
        autoCompleteTruck.setAdapter(adapter_truck);
        autoCompleteTruck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                isSelfTruckBoolean = true;
                for (Truck truck : truckList) {
                    if (truck.getTruckNumber().equals(editable)) {
                        isSelfTruckBoolean = false;
                        Toast.makeText(TripActivity.this, editable, Toast.LENGTH_SHORT).show();

                        break;
                    }
                }
            }
        });
        autoCompleteTruck.setThreshold(1);
        // truck list - end


        // particular - adding
        iv_add_extra_delivery.setOnClickListener(v -> {
            autoCompleteExtraDelivery.setVisibility(View.VISIBLE);
            layout_extraDelivery.setVisibility(View.VISIBLE);
            iv_add_extra_delivery.setVisibility(View.GONE);
        });

        TextInputLayout layout_fix_amount = findViewById(R.id.layout_fix_amount);
        layout_fix_amount.setVisibility(isSelfTruckBoolean ? View.GONE : View.VISIBLE);

        btn_submit.setOnClickListener(v -> {
            String proprietorName = autoCompleteProprietor.getText().toString();
            String partyName = autoCompleteParty.getText().toString();
            String billNumber = Objects.requireNonNull(et_bill_number.getText()).toString();
            String date = Objects.requireNonNull(etDate.getText()).toString();
            String truckNumber = autoCompleteTruck.getText().toString();
            String from = autoCompleteFrom.getText().toString();
            String to = autoCompleteTo.getText().toString();
            String extraDelivery = autoCompleteExtraDelivery.getText().toString();
            String weight = Objects.requireNonNull(et_weight.getText()).toString();
            String rate = Objects.requireNonNull(et_rate.getText()).toString();
            String fixAmountStr = et_fix_Amount.getText().toString();
            double fixAmount = 0;
            if (!fixAmountStr.isEmpty()) {
                fixAmount = Double.parseDouble(fixAmountStr);
            }
            double amount = Double.parseDouble(Objects.requireNonNull(et_amount.getText()).toString());
            String key = "trip-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            String createdOn = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

            // if (!existingKey.isEmpty()) key = existingKey;

            Trip trip = new Trip(key, proprietorName, partyName, billNumber, date, truckNumber, from, to, extraDelivery, weight, rate, fixAmount, amount, createdOn);
            myRef.child(key).setValue(trip);
            Toast.makeText(TripActivity.this, "Trip Added Successfully", Toast.LENGTH_SHORT).show();
        });

        Button btnAddExpense = findViewById(R.id.btn_add_expense);
        TableLayout tableExpense = findViewById(R.id.table_expense);
        EditText etExpenseAmount = findViewById(R.id.et_expense_amount);
        tvTotalExpense = findViewById(R.id.tv_total_amount);

        btnAddExpense.setOnClickListener(v -> {
            String expenseType = autoCompleteExpenseType.getText().toString().trim();
            String expenseAmount = etExpenseAmount.getText().toString().trim();

            // Validate Inputs
            if (expenseType.isEmpty()) {
                autoCompleteExpenseType.setError("Select an expense type");
                return;
            }
            if (expenseAmount.isEmpty()) {
                etExpenseAmount.setError("Enter an amount");
                return;
            }

            double amount = Double.parseDouble(expenseAmount);

            // Create a new TableRow
            TableRow row = new TableRow(this);

            // Create TextView for Expense Type
            TextView txtExpenseType = new TextView(this);
            txtExpenseType.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            txtExpenseType.setText(expenseType);

            // Create TextView for Amount
            TextView txtExpenseAmount = new TextView(this);
            txtExpenseAmount.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            txtExpenseAmount.setText(expenseAmount);

            // Create Remove Button
            Button btnRemove = new Button(this);
            btnRemove.setText("X");

            // Remove Row Logic
            btnRemove.setOnClickListener(view -> {
                double removedAmount = Double.parseDouble(txtExpenseAmount.getText().toString());

                // Deduct from total
                totalExpense -= removedAmount;
                tvTotalExpense.setText(String.format("Total Expense: %.2f", totalExpense));

                // Remove the row
                tableExpense.removeView(row);
            });

            // Add Views to the Row
            row.addView(txtExpenseType);
            row.addView(txtExpenseAmount);
            row.addView(btnRemove);

            // Add the Row to the Table
            tableExpense.addView(row);

            // Update Total Expense
            totalExpense += amount;
            tvTotalExpense.setText("Total Expense: " + String.format("%.2f", totalExpense));

            // Clear Inputs for Next Entry
            autoCompleteExpenseType.setText("");
            etExpenseAmount.setText("");
        });

    }

    private void getProprietorNameList() {
        proprietorRef = FirebaseDatabase.getInstance().getReference("proprietor");

        proprietorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameList.clear(); // Clear old data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Proprietor trip = dataSnapshot.getValue(Proprietor.class);
                    if (trip != null) {
                        nameList.add(trip.getProprietorName());
                    }
                }

                // Ensure Dropdown Appears
                // autoCompleteProprietor.post(() -> autoCompleteProprietor.showDropDown());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
            }
        });
    }

    private void getPartyNameList() {
        proprietorRef = FirebaseDatabase.getInstance().getReference("party");

        proprietorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                partyNameList.clear(); // Clear old data
                partyList.clear(); // Clear old data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Party trip = dataSnapshot.getValue(Party.class);
                    if (trip != null) {
                        partyNameList.add(trip.getPartyName());
                        partyList.add(trip);
                    }
                }
                // Ensure Dropdown Appears
                //   autoCompleteParty.post(() -> autoCompleteParty.showDropDown());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
            }
        });
    }

    private void getExpenseTypeList() {
        proprietorRef = FirebaseDatabase.getInstance().getReference("expenseType");

        proprietorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                expenseTypeNameList.clear(); // Clear old name data
                expenseTypeList.clear(); // Clear old data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ExpenseType obj = dataSnapshot.getValue(ExpenseType.class);
                    if (obj != null) {
                        expenseTypeNameList.add(obj.getText());
                        expenseTypeList.add(obj);
                    }
                }
                // Ensure Dropdown Appears
                //   autoCompleteParty.post(() -> autoCompleteParty.showDropDown());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
            }
        });
    }

    private void getTruckNumberList() {
        proprietorRef = FirebaseDatabase.getInstance().getReference("truck");

        proprietorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                truckNumberList.clear(); // Clear old data
                truckList.clear(); // Clear old data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Truck trip = dataSnapshot.getValue(Truck.class);
                    if (trip != null) {
                        truckNumberList.add(trip.getTruckNumber());
                        truckList.add(trip);
                    }
                }
                // Ensure Dropdown Appears
                //  autoCompleteTruck.post(() -> autoCompleteTruck.showDropDown());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
            }
        });
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

}
