package com.example.project_mom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.widget.Button;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button main_BTN_Add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_BTN_Add = findViewById(R.id.main_BTN_Add);
        main_BTN_Add.setOnClickListener(myListener);
        Log.d("","");

    }
    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            start();
        }
    };
    public void start(){

//        String input = main_EDT_input.getText().toString();
//        boolean result = MyLogic.buttonCheck(input);
//        main_LBL_info.setText("Result" + result);
    }
}


