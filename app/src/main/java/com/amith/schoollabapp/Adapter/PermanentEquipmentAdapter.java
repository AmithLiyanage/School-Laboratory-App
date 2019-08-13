package com.amith.schoollabapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amith.schoollabapp.Model.PermanentEquipment;
import com.amith.schoollabapp.R;

import java.util.List;

public class PermanentEquipmentAdapter extends RecyclerView.Adapter<PermanentEquipmentAdapter.ViewHolder> {

    private Context mContext;
    private List<PermanentEquipment> mPermanentEquipment;

    public PermanentEquipmentAdapter(Context mContext, List<PermanentEquipment> mPermanentEquipment) {
        this.mPermanentEquipment = mPermanentEquipment;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PermanentEquipmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.permanent_equipment_item, parent, false);
        return new PermanentEquipmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PermanentEquipmentAdapter.ViewHolder holder, int position) {


        PermanentEquipment permanentEquipment = mPermanentEquipment.get(position);
        Log.d("dsa2", ""+permanentEquipment.getRecomended());
        holder.permanent_equipment_name.setText(permanentEquipment.getItem_name());
        holder.available.setText(permanentEquipment.getAvailable().toString());
        holder.measurement.setText(permanentEquipment.getMeasurement());

    }

    @Override
    public int getItemCount() {
        return mPermanentEquipment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView permanent_equipment_name;
        public TextView available;
        public TextView measurement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            permanent_equipment_name = itemView.findViewById(R.id.permanent_equipment_name);
            available = itemView.findViewById(R.id.permanent_equipment_avaliable);
            measurement = itemView.findViewById(R.id.permanent_equipment_measurement);
        }
    }
}

