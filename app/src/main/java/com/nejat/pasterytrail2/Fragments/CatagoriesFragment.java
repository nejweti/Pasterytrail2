package com.nejat.pasterytrail2.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nejat.pasterytrail2.Adapters.MenuCatagoryAdapter;
import com.nejat.pasterytrail2.Classes.MenuModel;
import com.nejat.pasterytrail2.R;
import com.nejat.pasterytrail2.ViewHolder.Category;
import com.squareup.picasso.Picasso;

public class CatagoriesFragment extends Fragment {
    RecyclerView recyclerView;
    MenuCatagoryAdapter mMenuCatagoryAdapter;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseRecyclerOptions<MenuModel> options;
    FirebaseRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_catagories, container, false);

        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("category");
        Log.i("databse",databaseReference.toString());
        recyclerView = (RecyclerView) view.findViewById(R.id.menu_categories);

        options = new FirebaseRecyclerOptions.Builder<MenuModel>()
                        .setQuery(databaseReference, MenuModel.class)
                        .build();

        Log.i("options",options.toString());
//        mMenuCatagoryAdapter = new MenuCatagoryAdapter(options,getActivity());
//        recyclerView.setAdapter(mMenuCatagoryAdapter);
        loadCategory();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


    private void loadCategory() {
        Log.i("yes","excuted");
        adapter = new FirebaseRecyclerAdapter<MenuModel, Category>(options) {
            @Override
            public Category onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_catagories_linear,parent,false);
                Log.i("yes","excutedrrrrrrrrr");
                return new Category(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull Category holder, int position, @NonNull MenuModel model) {
                final ProgressBar bar = new ProgressBar(getActivity());
                bar.setVisibility(View.VISIBLE);
                holder.menu_name.setText(model.getName());
                Picasso.with(getActivity()).load(model.getImage())
                        .into(holder.menu_image, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                if(bar != null){
                                    bar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });
                holder.menu_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("menu","isclicked");
                    }
                });
            }
        };



    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }




}
