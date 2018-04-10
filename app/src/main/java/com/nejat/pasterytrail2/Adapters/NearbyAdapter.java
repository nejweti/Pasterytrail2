package com.nejat.pasterytrail2.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nejat.pasterytrail2.Classes.PastryLocation;
import com.nejat.pasterytrail2.R;

import java.util.List;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> {

    Activity activity;
    List<PastryLocation> pastryLocations;

    public NearbyAdapter(Activity activity, List<PastryLocation> pastryLocations){
        this.activity = activity;
        this.pastryLocations = pastryLocations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_pastery_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mname.setText(pastryLocations.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return pastryLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mname;
        public ViewHolder(View itemView) {
            super(itemView);
            mname = (TextView) itemView.findViewById(R.id.pastry_name);

        }
    }
}
