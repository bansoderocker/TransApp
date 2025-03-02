package com.project.transapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.transapp.Model.Proprietor;
import com.project.transapp.R;

import java.util.List;

public class ProprietorAdapter extends RecyclerView.Adapter<ProprietorAdapter.ViewHolder> {
    private final Context context;
    private final List<Proprietor> proprietorList;
    private final OnEditClickListener editClickListener;

    public ProprietorAdapter(Context context, List<Proprietor> proprietorList, OnEditClickListener editClickListener) {
        this.context = context;
        this.proprietorList = proprietorList;
        this.editClickListener = editClickListener;
    }

    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_proprietor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Proprietor proprietor = proprietorList.get(position);
        holder.tv_proprietorName.setText(proprietor.getProprietorName());
        holder.tv_contactNumber.setText(proprietor.getContactNumber());
        holder.tv_address.setText(proprietor.getAddress());
        holder.iv_editButton.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return proprietorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_proprietorName, tv_contactNumber,tv_address;
        ImageView iv_editButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_proprietorName = itemView.findViewById(R.id.tv_proprietorName);
            tv_contactNumber = itemView.findViewById(R.id.tv_contactNumber);
            tv_address = itemView.findViewById(R.id.tv_address);
            iv_editButton = itemView.findViewById(R.id.editButton);
        }
    }
}
