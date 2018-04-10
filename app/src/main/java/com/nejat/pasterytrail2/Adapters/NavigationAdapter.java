package com.nejat.pasterytrail2.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.EmailAuthProvider;
import com.nejat.pasterytrail2.Classes.NavData;
import com.nejat.pasterytrail2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/20/2018.
 */

public class NavigationAdapter extends ArrayAdapter<NavData> {

    List<NavData> navDataList;
    Activity activity;

   public NavigationAdapter(Activity context, List<NavData> navData){
       super(context, R.layout.nav_row, navData);
       this.activity = context;
       this.navDataList = navData;
   }
    public static class ViewHolder{
        ImageView mNavImage;
        TextView mtitle;
   }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        NavData navData = getItem(position);
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView  = inflater.inflate(R.layout.nav_row,parent,false);
            holder.mNavImage = (ImageView) convertView.findViewById(R.id.nav_icon);
            holder.mtitle = (TextView) convertView.findViewById(R.id.nav_title);
            convertView.setTag(holder);
        }
        else holder = (ViewHolder) convertView.getTag();

        holder.mtitle.setText(navData.getName());
        holder.mNavImage.setImageResource(navData.getIcon_id());
        return convertView;
    }
}
