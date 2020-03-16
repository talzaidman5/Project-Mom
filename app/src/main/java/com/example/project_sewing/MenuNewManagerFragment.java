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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuNewManagerFragment extends Fragment {

    private EditText fragment_newManager_EDT_phoneNumber, fragment_newManager_EDT_password,fragment_newManager_EDT_id;
    private Button fragment_newManager_BTN_enter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu_new_manager, container, false);
        fragment_newManager_EDT_password = v.findViewById(R.id.fragment_newManager_EDT_password);
        fragment_newManager_EDT_phoneNumber = v.findViewById(R.id.fragment_newManager_EDT_phoneNumber);
        fragment_newManager_BTN_enter = v.findViewById(R.id.fragment_newManager_BTN_enter);
        fragment_newManager_EDT_id = v.findViewById(R.id.fragment_newManager_EDT_id);
        fragment_newManager_BTN_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAllDetails()) {
                    myRef.child("manager").setValue(new Manager(fragment_newManager_EDT_phoneNumber.getText().toString(),
                            fragment_newManager_EDT_password.getText().toString(),fragment_newManager_EDT_id.getText().toString()));
                    Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_SHORT).show();
                    openManagerMenu(v);
                }
            }
        });
        return v;
    }

    private void openManagerMenu(View v) {
        Intent intent = new Intent(v.getContext(),MenuActivity.class);
        getActivity().finish();
        startActivity(intent);
    }

    private boolean checkAllDetails() {

        Boolean checkDetails = true;
        if (fragment_newManager_EDT_phoneNumber.getText().toString().matches("")) {
            fragment_newManager_EDT_phoneNumber.setError("Please fill in a phone number");
            checkDetails = false;

        }else {
            String numRegex   = ".*[0-9].*";
            String alphaRegex = ".*[A-Z].*";
            if (fragment_newManager_EDT_phoneNumber.getText().toString().substring(0, 1).equals("0") == false) {
                fragment_newManager_EDT_phoneNumber.setError("Phone number should start with 0!");
                checkDetails = false;
            }
            if (fragment_newManager_EDT_phoneNumber.getText().toString().length()<10){
                fragment_newManager_EDT_phoneNumber.setError("Phone number is too short");
                checkDetails = false;
            }
            if (fragment_newManager_EDT_password.getText().toString().matches("")) {
                fragment_newManager_EDT_password.setError( "Please fill in a password");
                checkDetails = false;
            }

            if (!(fragment_newManager_EDT_password.getText().toString().matches(numRegex)&&fragment_newManager_EDT_password.getText().toString().matches(alphaRegex))){
                fragment_newManager_EDT_password.setError("The password must have a letter and a number!");
                checkDetails = false;
            }

                if(fragment_newManager_EDT_id.getText().toString().length()!=9)
                fragment_newManager_EDT_id.setError("Error id");
        }
        return checkDetails;
    }




}
