package com.sweta.grievanceauthority.authfragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sweta.grievanceauthority.MainActivity;
import com.sweta.grievanceauthority.R;

public class RegisterFragment extends Fragment {
    EditText name;
    EditText email;
    EditText mobile_number;
    EditText gender;
    EditText date_of_birth;
    EditText username;
    EditText password;
    Button submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        name = (EditText) view.findViewById(R.id.name);
        email = (EditText) view.findViewById(R.id.email);
        mobile_number = (EditText) view.findViewById(R.id.number);
        gender = (EditText) view.findViewById(R.id.gender);
        date_of_birth = (EditText) view.findViewById(R.id.date_of_birth);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        submit = (Button) view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });




        return view;
    }

}

