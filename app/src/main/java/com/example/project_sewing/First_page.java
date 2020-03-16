package com.example.project_sewing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class First_page extends AppCompatActivity {
    private Spinner first_page_spinner;
    public static int whoIsTheUser=-1;
    private BlankFragment_Client fragment_client;
    private BlankFragment_Manager fragment_manager;
    private MenuNewManagerFragment menuNewManagerFragment;
    private TextView first_page_enter;
    private final int  USER = 0, MANAGER =1;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
    public static AllEvents allEvents;
    public static Manager manager;
    public static AllMassages allMassages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        allEvents = new AllEvents();
        allMassages = new AllMassages();
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                        String key = childSnapshot.getKey();
                        if(key.equals("clients")) {
                            allEvents = childSnapshot.getValue(AllEvents.class);
                        } else if(key.equals("manager")) {
                            manager = childSnapshot.getValue(Manager.class);
                        } else if(key.equals("massages")) {
                            allMassages= childSnapshot.getValue(AllMassages.class);
                        }
                    }

                if (manager == null) {
                    whoIsTheUser = MANAGER;
                    menuNewManagerFragment = new MenuNewManagerFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.first_page_fragment, menuNewManagerFragment);
                    transaction.commitAllowingStateLoss();
                    first_page_spinner.setVisibility(View.GONE);
                    first_page_enter.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


        first_page_spinner = findViewById(R.id.first_page_spinner);
        first_page_enter = findViewById(R.id.first_page_enter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.user,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        first_page_spinner.setAdapter(adapter);
        first_page_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (first_page_spinner.getSelectedItem().toString().equals("Manager")) {
                    whoIsTheUser = MANAGER;
                    fragment_manager = new BlankFragment_Manager();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.first_page_fragment, fragment_manager);
                    transaction.commit();
                }
                else if(first_page_spinner.getSelectedItem().toString().equals("Client")) {
                    whoIsTheUser = USER;
                    fragment_client = new BlankFragment_Client();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.first_page_fragment, fragment_client);
                    transaction.commit();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}


