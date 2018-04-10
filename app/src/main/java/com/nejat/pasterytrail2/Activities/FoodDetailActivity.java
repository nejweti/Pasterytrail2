package com.nejat.pasterytrail2.Activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nejat.pasterytrail2.Classes.Food;
import com.nejat.pasterytrail2.Classes.Order;
import com.nejat.pasterytrail2.Classes.User;
import com.nejat.pasterytrail2.Databases.CURRENTORDERDB;
import com.nejat.pasterytrail2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodDetailActivity extends AppCompatActivity implements View.OnClickListener {
    User user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView imageView;
    FloatingActionButton addtoCart;
    TextView foodPrice;
    ElegantNumberButton elegantNumberButton;
    CURRENTORDERDB orderDb;
    Food food;
    List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        user= (User) getApplicationContext();
        orderDb = new CURRENTORDERDB(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("foods");
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.foodDetail_collapsingtoolbar);
        imageView = (ImageView) findViewById(R.id.fooddetail_image);
        foodPrice = (TextView) findViewById(R.id.fooddetail_price);
        addtoCart = (FloatingActionButton) findViewById(R.id.fooddetail_cart);
        elegantNumberButton = (ElegantNumberButton) findViewById(R.id.numberPicker);
        loadData();
        addtoCart.setOnClickListener(this);
    }

    private void addtoCart() {
        if(orderDb.cartFound(user.getFoodId())){
            Toast.makeText(this,"added already",Toast.LENGTH_LONG).show();

        }
        else {
            orderDb.addCart(new Order(user.getFoodId(), food.getName(), food.getPrice(), Integer.parseInt(elegantNumberButton.getNumber()), food.getDiscount()));
            Toast.makeText(FoodDetailActivity.this,"added",Toast.LENGTH_LONG).show();
        }


    }


    private void loadData() {
        if(user.getFoodId()!=null){
            databaseReference.child(user.getFoodId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    food = dataSnapshot.getValue(Food.class);
                    Picasso.with(FoodDetailActivity.this).load(food.getImage()).into(imageView);
                    collapsingToolbarLayout.setTitle(food.getName());
                    foodPrice.setText("Price: $ " + food.getPrice());
                    Log.i("food price",food.getPrice()+"");


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        if(v == addtoCart){
            addtoCart();
        }
    }
}
