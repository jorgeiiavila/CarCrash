package com.jorgeiiavila.carcrash;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by fernandosalazar on 4/11/18.
 */

public class ChangeCarAdapter extends RecyclerView.Adapter<ChangeCarAdapter.ViewHolder> {

    AdapterCallback callback;
    private ArrayList<Integer> data;

    public ChangeCarAdapter(ArrayList<Integer> data, AdapterCallback callback) {
        this.data = data;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.change_car_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.carOption.setImageResource(data.get(position));
        holder.carOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onItemClicked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public interface AdapterCallback {
        void onItemClicked(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView carOption;

        public ViewHolder(View v) {
            super(v);
            carOption = v.findViewById(R.id.change_car_item_image);
        }
    }


}
