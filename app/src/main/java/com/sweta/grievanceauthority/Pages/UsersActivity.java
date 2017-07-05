package com.sweta.grievanceauthority.Pages;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sweta.grievanceauthority.FireChatHelper.ExtraIntent;
import com.sweta.grievanceauthority.R;
import com.sweta.grievanceauthority.adapter.ShowUsersAdapter;
import com.sweta.grievanceauthority.model.ShowUsersModel;
import com.sweta.grievanceauthority.model.Stats;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public RecyclerView recyclerView;
    public ArrayList<ShowUsersModel> model = new ArrayList<>();
    public ShowUsersAdapter adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRefDatabase;
    private ArrayList<ShowUsersModel> Users;
    public int count = -1;
    public int TO_REMOVE;
    TextView raised,pending,solved;
    private DatabaseReference NotificationRef;
    private ArrayList<String > UIDs=new ArrayList<>();
    private String mRecipientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        Bundle extras = getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        //mRecipientId=extras.getString(ExtraIntent.EXTRA_RECIPIENT_ID);
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
                adapter = new ShowUsersAdapter(UsersActivity.this, Users, mAuth);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        raised=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.raised));
        solved=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.resolved));
        pending=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.pending));
        initializeCountDrawer();
    }

    private void initializeCountDrawer(){

        solved.setGravity(Gravity.CENTER_VERTICAL);
        solved.setTypeface(null, Typeface.BOLD);
        solved.setTextColor(getResources().getColor(R.color.colorAccent));
        raised.setGravity(Gravity.CENTER_VERTICAL);
        raised.setTypeface(null,Typeface.BOLD);
        raised.setTextColor(getResources().getColor(R.color.colorAccent));
        pending.setGravity(Gravity.CENTER_VERTICAL);
        pending.setTypeface(null,Typeface.BOLD);
        pending.setTextColor(getResources().getColor(R.color.colorAccent));
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Statistics");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Stats stats=dataSnapshot.getValue(Stats.class);
                solved.setText(stats.Completed);
                pending.setText(stats.Pending);
                raised.setText(stats.Raised);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.resolved) {

        } else if (id == R.id.raised) {

        } else if (id == R.id.pending) {

        }
        else  if(id==R.id.Broadcast)
        {
            final Dialog d= new Dialog(UsersActivity.this);
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setCanceledOnTouchOutside(false);
            d.setContentView(R.layout.writemessage);

            final EditText editText=(EditText) d.findViewById(R.id.DialogText);
            Button button=(Button) d.findViewById(R.id.Submit_ID);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UIDs=new ArrayList<String>();

                    final  String Message=editText.getText().toString().trim();
                    if(!TextUtils.isEmpty(Message)) {
                        NotificationRef.push().setValue(Message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Snackbar.make(UsersActivity.this.findViewById(R.id.content_users),"Success",Snackbar.LENGTH_SHORT).show();

                            }
                        });

                        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String TIME=System.currentTimeMillis()+"";

                                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                                {


                                    UIDs.add(dataSnapshot1.getKey());
                                    Log.v("BroadCastValues",UIDs.size()+"....");

                                    FirebaseDatabase.getInstance().getReference().child("Chats").child(dataSnapshot1.getKey()).child(mAuth.getCurrentUser().getUid()).child(TIME).child("text").setValue(Message);
                                    FirebaseDatabase.getInstance().getReference().child("Chats").child(dataSnapshot1.getKey()).child(mAuth.getCurrentUser().getUid()).child(TIME).child("uid").setValue(mAuth.getCurrentUser().getUid());

                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }


                }
            });

            d.show();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
