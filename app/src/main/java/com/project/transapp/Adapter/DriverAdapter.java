package com.project.transapp.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.transapp.Model.Driver;
import com.project.transapp.R;

import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {
    private final Context context;
    private final List<Driver> driverList;
    private final OnEditClickListener editClickListener;

    public DriverAdapter(Context context, List<Driver> driverList, OnEditClickListener editClickListener) {
        this.context = context;
        this.driverList = driverList;
        this.editClickListener = editClickListener;
    }

    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_driver, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Driver driver = driverList.get(position);
        holder.tv_driverName.setText(driver.getDriverName());
        holder.tv_contactNumber.setText(driver.getContactNumber());
        holder.tv_licenseNumber.setText(driver.getLicenseNumber());

        holder.iv_editButton.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(position);
            }
        });
        holder.iv_copyDetails.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

            String copyText = "Driver Name: " + driver.getDriverName() + "\n"
                    + "Contact: " + driver.getContactNumber();

            ClipData clip = ClipData.newPlainText("Driver Details" , copyText);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Driver Details copied!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_driverName, tv_contactNumber,tv_licenseNumber;
        ImageView iv_editButton,iv_copyDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_driverName = itemView.findViewById(R.id.tv_driverName);
            tv_contactNumber = itemView.findViewById(R.id.tv_contactNumber);
            tv_licenseNumber = itemView.findViewById(R.id.tv_licenseNumber);
            iv_editButton = itemView.findViewById(R.id.editButton);
            iv_copyDetails = itemView.findViewById(R.id.iv_copyDetails);
        }
    }
}
