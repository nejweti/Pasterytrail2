package com.nejat.pasterytrail2.ViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nejat.pasterytrail2.R;

/**
 * Created by user on 3/25/2018.
 */

public class Category extends RecyclerView.ViewHolder {

    public TextView menu_name;
    public ImageView menu_image;
    public CardView menu_linear;
    public Category(View itemView) {
        super(itemView);
        menu_image = (ImageView) itemView.findViewById(R.id.menu_image);
        menu_name = (TextView) itemView.findViewById(R.id.menu_name);
        menu_linear = (CardView) itemView.findViewById(R.id.menu_Category_linear);


    }
}
