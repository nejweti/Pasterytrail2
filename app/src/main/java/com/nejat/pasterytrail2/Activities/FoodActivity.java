package com.nejat.pasterytrail2.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nejat.pasterytrail2.Classes.Food;
import com.nejat.pasterytrail2.Classes.MenuModel;
import com.nejat.pasterytrail2.Classes.User;
import com.nejat.pasterytrail2.Fragments.FoodDetailFragment;
import com.nejat.pasterytrail2.R;
import com.nejat.pasterytrail2.ViewHolder.Category;
import com.nejat.pasterytrail2.ViewHolder.FoodViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FoodActivity extends AppCompatActivity {

    User user;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView mRecyclerView;
    FirebaseRecyclerOptions<Food> options;
    FirebaseRecyclerAdapter adapter;
    String categoryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        user = (User) getApplicationContext();
        firebaseDatabase = firebaseDatabase.getInstance();
        categoryId = user.getCategoryId();
        Query query = FirebaseDatabase.getInstance().getReference().child("foods").orderByChild("category").equalTo(categoryId);
        mRecyclerView = (RecyclerView) findViewById(R.id.food_menu);


        options = new FirebaseRecyclerOptions.Builder<Food>()
                        .setQuery(query, Food.class)
                        .build();

        Log.i("excuuu",options.toString());

//        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        loadData();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("listen","yes");
        adapter.startListening();
    }


    private void loadData() {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {

            @Override
            public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_linear,parent,false);
                return new FoodViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final FoodViewHolder holder, final int position, @NonNull Food model) {
                holder.food_name.setText(model.getName());
                Picasso.with(FoodActivity.this).load(model.getImage()).into(holder.food_image, new Callback() {

                    @Override
                    public void onSuccess() {
                        Log.i("Picasso","Loaded");
                    }

                    @Override
                    public void onError() {

                    }
                });
                holder.food_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.setFoodId(adapter.getRef(position).getKey());
                        startActivity(new Intent(FoodActivity.this,FoodDetailActivity.class));
                    }
                });

            }



        };

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
