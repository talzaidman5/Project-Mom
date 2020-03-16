package com.example.project_sewing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class ActivityCancelEvent extends AppCompatActivity {
    private ArrayList<String> closeEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_event);
        checkEvents();

    }

    private void checkEvents() {

        if (First_page.allEvents != null) {
            ArrayList<Event1> elist = First_page.allEvents.getData().get(BlankFragment_Client.currentPhone);
            if(elist != null) {
                for (int i = 0; i < elist.size(); i++) {
                    if (elist.get(i).getStatus() == 1)
                        closeEvents.add(elist.get(i).getDay() + "/" + elist.get(i).getMonth() + "/" + elist.get(i).getYear() + " at " + elist.get(i).getHour());
                }
            }

            if(closeEvents.isEmpty())
                closeEvents.add("There are no future queues ");
        }
    }


}
