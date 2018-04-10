package com.nejat.pasterytrail2.ViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nejat.pasterytrail2.R;

/**
 * Created by user on 3/29/2018.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder {
    public TextView food_name;
    public ImageView food_image;
    public CardView food_linear;
    public FoodViewHolder(View itemView) {
        super(itemView);
        food_image = (ImageView) itemView.findViewById(R.id.food_image);
        food_name = (TextView) itemView.findViewById(R.id.food_name);
        food_linear = (CardView) itemView.findViewById(R.id.food_linear);

    }
}