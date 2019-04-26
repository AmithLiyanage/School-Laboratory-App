package com.amith.schoollabapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amith.schoollabapp.R;
import com.amith.schoollabapp.Model.MenuItem;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<MenuItem> mData;

    public RecycleViewAdapter(Context mContext, List<MenuItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_item_title.setText(mData.get(position).getTitle());
        holder.img_item_thumbnail.setImageResource(mData.get(position).getThumbnail());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item_title;
        ImageView img_item_thumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_title = (TextView) itemView.findViewById(R.id.recycler_view);
            img_item_thumbnail = (ImageView) itemView.findViewById(R.id.item_image);

        }
    }
}
