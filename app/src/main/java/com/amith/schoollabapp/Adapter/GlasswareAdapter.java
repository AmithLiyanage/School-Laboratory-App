package com.amith.schoollabapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amith.schoollabapp.Model.Chemical;
import com.amith.schoollabapp.Model.Glassware;
import com.amith.schoollabapp.R;

import java.util.List;

public class GlasswareAdapter extends RecyclerView.Adapter<GlasswareAdapter.ViewHolder> {

    private Context mContext;
    private List<Glassware> mGlassware;

    public GlasswareAdapter(Context mContext, List<Glassware> mGlassware) {
        this.mGlassware = mGlassware;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.glassware_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glassware glassware = mGlassware.get(position);
        holder.glassware_name.setText(glassware.getGlasswareName());
        holder.available.setText(glassware.getAvailable().toString());
        holder.measurement.setText(glassware.getMeasurement());

    }

    @Override
    public int getItemCount() {
        return mGlassware.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView glassware_name;
        public TextView available;
        public TextView measurement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            glassware_name = itemView.findViewById(R.id.glassware_name);
            available = itemView.findViewById(R.id.glassware_avaliable);
            measurement = itemView.findViewById(R.id.glassware_measurement);
        }
    }
}
