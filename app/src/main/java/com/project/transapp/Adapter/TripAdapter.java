package com.project.transapp.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.transapp.Model.Trip;
import com.project.transapp.R;

import java.util.List;


public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private final List<Trip> tripList;
    private OnExpenseClickListener expenseClickListener;

    public interface OnExpenseClickListener {
        void onAddExpenseClick(Trip trip);
    }

    public TripAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_item, parent, false);
        return new TripViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);

        holder.tvTripDate.setText("📅 " + trip.getDate());
        holder.tvProprietor.setText("🏢" + trip.getProprietorName());
        holder.tvParty.setText("👤 Party: " + trip.getPartyName());
        holder.tvBill.setText("🧾 Bill No: " + trip.getBillNumber());
        holder.tvTruck.setText("🚛 " + trip.getTruckNumber());
        holder.tvFromTo.setText("📍 " + trip.getFrom() + " → " + trip.getTo());
        holder.tvAmount.setText("💰 Amount ₹" + trip.getAmount());
        holder.tvExpense.setText("💰 Expense ₹" + trip.getAmount());

        holder.tvAmount.setTextColor(Color.parseColor("#4CAF50"));

        holder.btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expenseClickListener != null) {
                    expenseClickListener.onAddExpenseClick(trip);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public void updateList(List<Trip> newList) {
        tripList.clear();
        tripList.addAll(newList);
        notifyDataSetChanged(); // Refresh RecyclerView
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView tvTripDate, tvProprietor, tvParty, tvBill, tvTruck, tvFromTo, tvAmount, tvExpense;
        Button btnAddExpense;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTripDate = itemView.findViewById(R.id.tv_trip_date);
            tvProprietor = itemView.findViewById(R.id.tv_trip_proprietor);
            tvParty = itemView.findViewById(R.id.tv_trip_party);
            tvBill = itemView.findViewById(R.id.tv_trip_bill);
            tvTruck = itemView.findViewById(R.id.tv_trip_truck);
            tvFromTo = itemView.findViewById(R.id.tv_trip_from_to);
            tvAmount = itemView.findViewById(R.id.tv_trip_amount);
            tvExpense = itemView.findViewById(R.id.tv_trip_expense);
            btnAddExpense = itemView.findViewById(R.id.btnAddExpense);
        }
    }
}
