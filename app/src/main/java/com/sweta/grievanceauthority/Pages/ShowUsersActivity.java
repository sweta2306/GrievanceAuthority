package com.sweta.grievanceauthority.Pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sweta.grievanceauthority.R;
import com.sweta.grievanceauthority.adapter.ShowUsersAdapter;
import com.sweta.grievanceauthority.authfragments.LoginFragment;
import com.sweta.grievanceauthority.model.ShowUsersModel;

import java.util.ArrayList;


public class ShowUsersActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ArrayList<ShowUsersModel> model = new ArrayList<>();
    public ShowUsersAdapter adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRefDatabase;
    private ArrayList<ShowUsersModel> Users;
    public int count = -1;
    public int TO_REMOVE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_show_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        mUserRefDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        Users = new ArrayList<ShowUsersModel>();
        mUserRefDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = -1;
                for (DataSnapshot UserDetails : dataSnapshot.getChildren()) {
                    ShowUsersModel Profile = UserDetails.getValue(ShowUsersModel.class);
                    Users.add(Profile);
                    count++;
                }
                //Users.remove(TO_REMOVE);
                adapter = new ShowUsersAdapter(ShowUsersActivity.this, Users, mAuth);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}





