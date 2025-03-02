package com.project.transapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.transapp.Model.Truck;
import com.project.transapp.R;

import java.util.List;
public class TruckAdapter extends RecyclerView.Adapter<TruckAdapter.ViewHolder> {
    private final Context context;
    private final List<Truck> tripList;
    private final OnEditClickListener editClickListener;


    public TruckAdapter(Context context, List<Truck> tripList,OnEditClickListener editClickListener) {
        this.context = context;
        this.tripList = tripList;
        this.editClickListener = editClickListener;

    }
    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_party, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Truck trip = tripList.get(position);
        holder.tv_partyName.setText(trip.getTruckNumber());
        String sr_no = Integer.toString(position + 1);
        holder.tv_pageNumber.setText(sr_no);
        holder.iv_editButton.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_pageNumber, tv_partyName;

        ImageView iv_editButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_pageNumber = itemView.findViewById(R.id.tv_pageNumber);
            tv_partyName = itemView.findViewById(R.id.tv_partyName);
            iv_editButton = itemView.findViewById(R.id.editButton);

        }
    }
}
