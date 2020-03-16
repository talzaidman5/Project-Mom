package com.example.project_sewing;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MenuClientFragment extends Fragment {
    private Button menu_BTN_calender,menu_BTN_myEvents,menu_BTN_prices,menu_BTN_exit,menu_BTN_Messages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu_client, container, false);
        menu_BTN_prices = v.findViewById(R.id.menu_BTN_prices);
        menu_BTN_prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPrices();
            }
        });
        menu_BTN_Messages = v.findViewById(R.id.menu_BTN_Messages);
        menu_BTN_Messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMassages();
            }
        });
        menu_BTN_calender = v.findViewById(R.id.menu_BTN_calender);
        menu_BTN_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCalender();
            }
        });
        menu_BTN_exit = v.findViewById(R.id.menu_BTN_exit);
        menu_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        menu_BTN_myEvents = v.findViewById(R.id.menu_BTN_myEvents);
        menu_BTN_myEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllEventOfClient();
            }
        });
        return v;
    }

    private void openMassages() {
        Intent intent = new Intent(getContext(),MassagesActivity.class);
        startActivity(intent);
    }

    private void openAllEventOfClient() {
        Intent intent = new Intent(getContext(),AllTheEventsOfClient.class);
        startActivity(intent);
    }

    public void OpenPrices(){
        Intent intent = new Intent(getContext(),Prices.class);
        startActivity(intent);
    }

    public void OpenCalender(){
        Intent intent = new Intent(getContext(), Calendar1.class);
        startActivity(intent);
    }

}
