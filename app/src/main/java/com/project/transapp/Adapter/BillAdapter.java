package com.project.transapp.Adapter;

import android.content.Context;
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

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private Context context;
    private List<Trip> tripList;

    public BillAdapter(Context context, List<Trip> tripList) {
        this.context = context;
        this.tripList = tripList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.billNo.setText(trip.getBillNumber());
        holder.date.setText(trip.getTripDate());
        holder.truckNumber.setText(trip.getTruckNumber());
        holder.particular.setText(trip.getParticular());
        holder.amount.setText(String.valueOf(trip.getAmount()));

        // Handle Edit Button Click
        holder.btnEdit.setOnClickListener(v -> {
            // Add edit functionality here
        });

        // Handle Delete Button Click
        holder.btnDelete.setOnClickListener(v -> {
            tripList.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView billNo, date, truckNumber, particular, amount;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            billNo = itemView.findViewById(R.id.billNo);
            date = itemView.findViewById(R.id.date);
            truckNumber = itemView.findViewById(R.id.truckNumber);
            particular = itemView.findViewById(R.id.particular);
            amount = itemView.findViewById(R.id.amount);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
