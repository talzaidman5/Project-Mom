package com.example.project_sewing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class MassagesActivity extends AppCompatActivity {

    private ListView massages_listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> allTheMassages = new ArrayList<>();
    private Button massages_BTN_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massages);
        massages_listView = findViewById(R.id.massages_listView);
        addToAllTheMassages();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, allTheMassages){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =  super.getView(position,convertView,parent);
                return view;
            }
        };
        massages_listView.setAdapter(arrayAdapter);
        massages_BTN_back = findViewById(R.id.massages_BTN_back);
        massages_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void addToAllTheMassages() {
        for (Massage massages : First_page.allMassages.getAllMassages())
            if(massages.getPhoneNumber().equals(BlankFragment_Client.currentPhone))
                allTheMassages.add(massages.getMassages());
    }


}