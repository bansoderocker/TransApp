package com.project.transapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.project.transapp.Model.ExpenseType;
import com.project.transapp.R;

import java.util.List;

public class ExpenseTypeAdapter extends RecyclerView.Adapter<ExpenseTypeAdapter.ViewHolder> {
    private final Context context;
    private final List<ExpenseType> expenseTypeList;
    private final OnEditClickListener editClickListener;

    public ExpenseTypeAdapter(Context context, List<ExpenseType> expenseTypeList, OnEditClickListener editClickListener) {
        this.context = context;
        this.expenseTypeList = expenseTypeList;
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
        ExpenseType expenseType = expenseTypeList.get(position);
        holder.tv_expenseName.setText(expenseType.getText());
        String pageNumber = String.valueOf(position + 1);
        holder.tv_pageNumber.setText(pageNumber);

String type = expenseType.getType() != null ? expenseType.getType() : "";
        if (type.equalsIgnoreCase("Credit")) {
            holder.ll_item.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_700));
        } else {
            holder.ll_item.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
        }

        // Edit Button
        holder.iv_editButton.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseTypeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_expenseName, tv_pageNumber;
        ImageView iv_editButton;
        LinearLayout ll_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            tv_pageNumber = itemView.findViewById(R.id.tv_pageNumber);
            tv_expenseName = itemView.findViewById(R.id.tv_partyName);
            iv_editButton = itemView.findViewById(R.id.editButton);
        }
    }
}
