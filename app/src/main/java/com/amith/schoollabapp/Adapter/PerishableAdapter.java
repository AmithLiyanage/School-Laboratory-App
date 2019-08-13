package com.amith.schoollabapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amith.schoollabapp.Model.Perishable;
import com.amith.schoollabapp.R;

import java.util.List;

public class PerishableAdapter extends RecyclerView.Adapter<PerishableAdapter.ViewHolder> {

    private Context mContext;
    private List<Perishable> mPerishable;

    public PerishableAdapter(Context mContext, List<Perishable> mPerishable) {
        this.mPerishable = mPerishable;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.perishable_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Perishable perishable = mPerishable.get(position);
        Log.d("dsa2", ""+perishable.getRecomended());
        holder.perishable_name.setText(perishable.getItem_name());
        holder.available.setText(perishable.getAvailable().toString());
        holder.measurement.setText(perishable.getMeasurement());

    }

    @Override
    public int getItemCount() {
        return mPerishable.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView perishable_name;
        public TextView available;
        public TextView measurement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            perishable_name = itemView.findViewById(R.id.perishable_name);
            available = itemView.findViewById(R.id.perishable_avaliable);
            measurement = itemView.findViewById(R.id.perishable_measurement);
        }
    }
}
