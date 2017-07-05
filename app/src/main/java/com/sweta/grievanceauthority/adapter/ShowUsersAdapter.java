package com.sweta.grievanceauthority.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.sweta.grievanceauthority.FireChatHelper.ExtraIntent;
import com.sweta.grievanceauthority.Pages.ChatActivity;
import com.sweta.grievanceauthority.R;
import com.sweta.grievanceauthority.model.ShowUsersModel;

import java.util.ArrayList;



/**
 * Created by 1406074 on 02-06-2017.
 */

public class ShowUsersAdapter extends RecyclerView.Adapter<ShowUsersAdapter.ViewHolder> {

    Context context;
    ArrayList<ShowUsersModel> objects;
    private FirebaseAuth mAuth;

    public ShowUsersAdapter(Context context, ArrayList<ShowUsersModel> objects, FirebaseAuth user) {
        this.context = context;
        this.objects = objects;
        this.mAuth = user;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_show_users,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShowUsersAdapter.ViewHolder holder, int position) {
        ShowUsersModel model = objects.get(position);
        holder.user_name.setText(model.getDisplayName());
        //holder.profile_image.setImageResource(model.getPhotoUrl());
    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView user_name;
        public ImageView profile_image;
        public TextView status;
        public RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);

            user_name = (TextView) itemView.findViewById(R.id.sender_name);
            profile_image = (ImageView) itemView.findViewById(R.id.photoImageView);
            status = (TextView) itemView.findViewById(R.id.status);
            container = (RelativeLayout) itemView.findViewById(R.id.container);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowUsersModel user = objects.get(getLayoutPosition());
                    Intent chatintent = new Intent(context, ChatActivity.class);
                    chatintent.putExtra(ExtraIntent.EXTRA_RECIPIENT_ID, user.getUid());
                    context.startActivity(chatintent);

                }
            });
        }

    }
    public ArrayList<ShowUsersModel> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<ShowUsersModel> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }


}
