package com.project.transapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    int[] images = {R.drawable.local_shipping_24px};
    String[] names = {"Party", "Truck", "Driver", "Transport", "Expense", "Trip","View Bill"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Click Listeners for Each LinearLayout
        setClickListener(R.id.title1, R.id.layout1);
        setClickListener(R.id.title2, R.id.layout2);
        setClickListener(R.id.title3, R.id.layout3);
        setClickListener(R.id.title4, R.id.layout4);
        setClickListener(R.id.title5, R.id.layout5);
        setClickListener(R.id.title6, R.id.layout6);
        setClickListener(R.id.title7, R.id.layout7);
    }
    private void setClickListener(int textViewId, int layoutId) {
        TextView textView = findViewById(textViewId);
        LinearLayout layout = findViewById(layoutId);

        layout.setOnClickListener(v -> {
            String category = textView.getText().toString();
            redirectToAnotherActivity(category);
        });
    }
    private void redirectToAnotherActivity(String category) {
        Intent intent;
        switch (category) {
            case "Party":
                intent = new Intent(this, PartyActivity.class);
                break;
            case "Propriter":
                intent = new Intent(this, ProprietorActivity.class);
                break;
            case "Driver":
                intent = new Intent(this, DriverActivity.class);
                break;
            case "Truck":
                intent = new Intent(this, TruckActivity.class);
                break;
            case "Expense":
                intent = new Intent(this, ExpenseActivity.class);
                break;
            case "Trip":
                intent = new Intent(this, TripActivity.class);
                break;
            case "View Bill":
                intent = new Intent(this, ViewBillActivity.class);
                break;
            default:
                return;
        }
        intent.putExtra("category", category); // Optional data
        startActivity(intent);
    }
}
