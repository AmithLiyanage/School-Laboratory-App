package com.amith.schoollabapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amith.schoollabapp.Model.Chemical;
import com.amith.schoollabapp.R;

import java.util.List;

public class ChemicalAdapter extends RecyclerView.Adapter<ChemicalAdapter.ViewHolder> {

    private Context mContext;
    private List<Chemical> mChemicals;

    public ChemicalAdapter(Context mContext, List<Chemical> mUsers) {
        this.mChemicals = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chemical_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Chemical chemical = mChemicals.get(position);
        Log.d("dsa2", ""+chemical.getRecomended());
       holder.chemical_name.setText(chemical.getChemicalName2());
        holder.available.setText(chemical.getAvailable().toString());
        holder.measurement.setText(chemical.getMeasurement());

    }

    @Override
    public int getItemCount() {
        return mChemicals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView chemical_name;
        public TextView available;
        public TextView measurement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chemical_name = itemView.findViewById(R.id.chemical_name);
            available = itemView.findViewById(R.id.chemical_avaliable);
            measurement = itemView.findViewById(R.id.chemical_measurement);
        }
    }
}

