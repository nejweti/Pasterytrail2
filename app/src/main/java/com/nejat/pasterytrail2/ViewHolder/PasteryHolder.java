package com.nejat.pasterytrail2.ViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nejat.pasterytrail2.R;

public class PasteryHolder extends RecyclerView.ViewHolder {
    public TextView pastry_name;

    public PasteryHolder(View itemView) {
        super(itemView);
        pastry_name = (TextView) itemView.findViewById(R.id.pastry_name);
    }
}
