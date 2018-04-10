package com.nejat.pasterytrail2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nejat.pasterytrail2.Adapters.ShoppingCartAdapter;
import com.nejat.pasterytrail2.Classes.Order;
import com.nejat.pasterytrail2.Classes.User;
import com.nejat.pasterytrail2.Databases.CURRENTORDERDB;
import com.nejat.pasterytrail2.Databases.TOTALORDERDB;
import com.nejat.pasterytrail2.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener,ShoppingCartAdapter.onClickedListener {
    CURRENTORDERDB orderDb;
    RecyclerView recyclerView;
    ShoppingCartAdapter shoppingCartAdapter;
    TextView totalPriceText;
    List<Order> orderList;
    int totalPrice = 0;
    private Button btnCheckOut;
    private CircleImageView removeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        orderDb  =  new CURRENTORDERDB(this);
        totalPriceText = (TextView) findViewById(R.id.cart_total_price);
        btnCheckOut = (Button) findViewById(R.id.checkout);
        recyclerView = (RecyclerView) findViewById(R.id.cart_recyclerView);
        removeBtn = (CircleImageView) findViewById(R.id.cart_remove_button);
        btnCheckOut.setOnClickListener(this);
        addtoList();
        shoppingCartAdapter =  new ShoppingCartAdapter(this,orderList);
        shoppingCartAdapter.setOnCancelClicked(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        Log.e("quatnttu","orderList.get(0).getProduct_quantity()");
        recyclerView.setAdapter(shoppingCartAdapter);
        getTotalPrice();
        totalPriceText.setText(totalPrice+"");
    }

    private void getTotalPrice() {
        orderList = new ArrayList<>();
        orderList = orderDb.getAllCarts();
        totalPrice = 0;
        for(int i = 0 ; i< orderList.size();i++){


            int price = Integer.parseInt(orderList.get(i).getProduct_price());
            int quantity = orderList.get(i).getProduct_quantity();
            int priceCalc = price * quantity;


            totalPrice += priceCalc;
           Log.i("price",totalPrice+"");
        }
    }

    private void addtoList(){
        orderList = new ArrayList<>();
        orderList = orderDb.getAllCarts();
    }

    @Override
    public void onClick(View v) {
        orderList = new ArrayList<>();
        orderList = orderDb.getAllCarts();
        if(v == btnCheckOut){
            if(orderList.size() > 0) {
                startActivity(new Intent(ShoppingCartActivity.this, DeliveryMethodsActivity.class));
                Toast.makeText(ShoppingCartActivity.this, "Proceed check out", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(ShoppingCartActivity.this, "Your Cart is Empty", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onCancelClickedListener() {
        User user = (User) getApplicationContext();
        orderDb.deleteCart(user.getOrderId());
        orderList = new ArrayList<>();
        orderList = orderDb.getAllCarts();
        shoppingCartAdapter =  new ShoppingCartAdapter(this,orderList);
        shoppingCartAdapter.setOnCancelClicked(this);
        recyclerView.setAdapter(shoppingCartAdapter);
        getTotalPrice();
        totalPriceText.setText(totalPrice+"");
    }

    @Override
    public void onNumberBtnClicked() {
        getTotalPrice();
        totalPriceText.setText(totalPrice+"");
    }


}
