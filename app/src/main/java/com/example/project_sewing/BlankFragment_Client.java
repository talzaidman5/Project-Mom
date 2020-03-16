package com.example.project_sewing;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class BlankFragment_Client extends Fragment {

    private EditText fragment_phoneNumber;
    private Button fragment_BTN_enter;
    private ArrayList<Usres> allUsres;
    private boolean checkIfFound = false;
    public static String currentPhone;
    public static boolean isEnter = false;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_blank, container, false);
        fragment_phoneNumber = v.findViewById(R.id.fragment_id);
        fragment_BTN_enter = v.findViewById(R.id.fragment_BTN_enter);
        allUsres = new ArrayList<>();

        fragment_BTN_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPhoneNumber();
            }
        });
        return v;
    }

    public void checkPhoneNumber() {


        if (fragment_phoneNumber.getText().toString().matches("")) {
            fragment_phoneNumber.setError("");
        }
            else if(!fragment_phoneNumber.getText().toString().substring(0,1).equals("0"))
            fragment_phoneNumber.setError("Phone number must start with 0");
        if(fragment_phoneNumber.getText().toString().length()<10)
            fragment_phoneNumber.setError("Invalid phone number");

        if(fragment_phoneNumber.getText().toString().length()>10)
            fragment_phoneNumber.setError("Invalid phone number");

        if(!fragment_phoneNumber.getText().toString().matches("")) {
            if (fragment_phoneNumber.getText().toString().substring(0, 1).equals("0") && (fragment_phoneNumber.getText().toString().length() == 10)) {
                currentPhone = fragment_phoneNumber.getText().toString();
                for (int i = 0; i < allUsres.size(); i++) {
                    if (allUsres.get(i).equals(fragment_phoneNumber.getText().toString() == currentPhone)) {
                        checkIfFound = true;
                        isEnter = true;
                        Intent intent= new Intent(getContext(), MenuActivity.class);
                        getActivity().finish();
                        startActivity(intent);
                    }
                }
                if (!checkIfFound) {
                    allUsres.add(new Usres(fragment_phoneNumber.getText().toString()));
                    isEnter =true;
                    Intent intent= new Intent(getContext(), MenuActivity.class);
                    getActivity().finish();
                    startActivity(intent);
                }
            }
        }

    }
}