package com.nejat.pasterytrail2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.nejat.pasterytrail2.Classes.MenuModel;
import com.nejat.pasterytrail2.R;

/**
 * Created by user on 3/25/2018.
 */

public class MenuCatagoryAdapter extends FirebaseRecyclerAdapter<MenuModel,MenuCatagoryAdapter.ViewHolder> {
    Context mContext;
    FirebaseRecyclerOptions<MenuModel> options;

    public MenuCatagoryAdapter(@NonNull FirebaseRecyclerOptions<MenuModel> options, Context context) {
        super(options);
        this.mContext = context;
        this.options = options;

    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MenuModel model) {
        holder.menu_name.setText(model.getName());
        Log.i("menuname",model.getName()+model.toString());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_catagories_linear,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView menu_name;
        public ImageView menu_image;
        public ViewHolder(View itemView) {
            super(itemView);
             menu_image = (ImageView) itemView.findViewById(R.id.menu_image);
             menu_name = (TextView) itemView.findViewById(R.id.menu_name);

        }
    }
}
