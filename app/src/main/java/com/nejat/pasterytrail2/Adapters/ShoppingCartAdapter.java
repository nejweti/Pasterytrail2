package com.nejat.pasterytrail2.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.nejat.pasterytrail2.Classes.Order;
import com.nejat.pasterytrail2.Classes.User;
import com.nejat.pasterytrail2.Databases.CURRENTORDERDB;
import com.nejat.pasterytrail2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by user on 4/1/2018.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    public Activity activity;
    List<Order> orderList = new ArrayList<>();
    User user;
    onClickedListener onClickedListener;

    public ShoppingCartAdapter(Activity activity, List<Order> orderList) {
        this.activity = activity;
        this.orderList = orderList;
        user = (User) activity.getApplication();

    }

    public void setOnCancelClicked(onClickedListener onClickedListener){
        this.onClickedListener = onClickedListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.cart_linear_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.cart_food_name.setText(orderList.get(position).getProduct_name());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setOrderId(orderList.get(position).getId());
                Log.i("cancelbtn","clicked" + user.getOrderId());
                onClickedListener.onCancelClickedListener();
            }
        });
        holder.numberButton.setNumber(orderList.get(position).getProduct_quantity()+"");

        holder.numberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("eleganum",holder.numberButton.getNumber());
                CURRENTORDERDB currentorderdb = new CURRENTORDERDB(activity);
                Log.i("sizedb",currentorderdb.getAllCarts().size()+"");
                currentorderdb.updateCart(new Order(orderList.get(position).getId(),
                        orderList.get(position).getProduct_id(),
                        orderList.get(position).getProduct_name(),
                        orderList.get(position).getProduct_price(),
                        Integer.parseInt(holder.numberButton.getNumber()),
                        orderList.get(position).getProduct_discount()));
                onClickedListener.onNumberBtnClicked();

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView cart_food_imageView;
        public TextView cart_food_name;
        public CircleImageView cancel;
        public ElegantNumberButton numberButton;

        public ViewHolder(View itemView) {
            super(itemView);
            cart_food_imageView = (ImageView) itemView.findViewById(R.id.cartImage);
            cart_food_name = (TextView) itemView.findViewById(R.id.cart_food_name);
            cancel = (CircleImageView) itemView.findViewById(R.id.cart_remove_button);
            numberButton = (ElegantNumberButton) itemView.findViewById(R.id.cart_quantity);

        }
    }
    public interface onClickedListener{
        void onCancelClickedListener();
        void onNumberBtnClicked();
    }
}

