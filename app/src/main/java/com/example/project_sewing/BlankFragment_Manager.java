package com.example.project_sewing;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BlankFragment_Manager extends Fragment {

    private EditText fragment_id,fragment_password;
    private Button fragment_BTN_enter;

    private  int  MANAGER=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_blank_fragment__manager, container, false);
        fragment_id = v.findViewById(R.id.fragment_id);
        fragment_password = v.findViewById(R.id.fragment_password);
        fragment_BTN_enter = v.findViewById(R.id.fragment_BTN_enter);
        fragment_BTN_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                First_page.whoIsTheUser = MANAGER;
                checkManager(v);
            }
        });
        return v;
    }

    private void checkManager(View v) {
        if(!First_page.manager.getPassword().equals(fragment_password.getText().toString()) ||
                !First_page.manager.getId().equals(fragment_id.getText().toString()) ){
            Toast.makeText(getContext(), "Invalid details", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(v.getContext(),MenuActivity.class);
            getActivity().finish();
            startActivity(intent);
        }
    }
}
