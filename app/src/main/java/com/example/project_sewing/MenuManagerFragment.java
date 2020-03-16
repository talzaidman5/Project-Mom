package com.example.project_sewing;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MenuManagerFragment extends Fragment {
    private Button menu_BTN_calender,menu_BTN_eventRequest,menu_BTN_closeEvents,menu_BTN_prices,menu_BTN_exit,menu_BTN_graph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blank_manager, container, false);

        menu_BTN_prices= v.findViewById(R.id.menu_BTN_prices);
        menu_BTN_prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPrices();
            }
        });
        menu_BTN_calender = v.findViewById(R.id.menu_BTN_calender);
        menu_BTN_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalender();
            }
        });
        menu_BTN_graph = v.findViewById(R.id.main_BTN_graph);
        menu_BTN_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGraph();
            }
        });
        menu_BTN_exit = v.findViewById(R.id.menu_BTN_exit);
        menu_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        menu_BTN_eventRequest = v.findViewById(R.id.menu_BTN_eventRequest);
        menu_BTN_eventRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWaitingToConfirmationActivity();
            }
        });
        menu_BTN_closeEvents = v.findViewById(R.id.menu_BTN_closeEvents);
        menu_BTN_closeEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCloseEvents();
            }
        });
        return v;
    }

    private void openGraph() {
        Intent intent = new Intent(getContext(), graphActivity.class);
        startActivity(intent);
    }

    private void openPrices() {
        Intent intent = new Intent(getContext(), Prices.class);
        startActivity(intent);
    }

    private void openCalender() {
        Intent intent = new Intent(getContext(), Calendar1.class);
        startActivity(intent);
    }

    private void openWaitingToConfirmationActivity() {
        Intent intent = new Intent(getContext(),waitingToConfirmationActivity.class);
        startActivity(intent);
    }
    private void openCloseEvents() {
        Intent intent = new Intent(getContext(),CloseEvents.class);
        startActivity(intent);
    }


}
