package com.example.project_sewing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CloseEvents extends AppCompatActivity {

    public static ArrayList<String> closeEvents = new ArrayList<>();
    private Button closeEvent_BTN_back;
    public static ArrayAdapter<String> arrayAdapter;
    private ListView closeEventsListView;
    public static int Position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_events);
        closeEventsListView = findViewById(R.id.closeEventsListView);
        closeEventsListView.setAdapter(arrayAdapter);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, closeEvents) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                return view;
            }
        };
        closeEventsListView.setAdapter(arrayAdapter);

        closeEvent_BTN_back = findViewById(R.id.closeEvent_BTN_back);
        closeEvent_BTN_back.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       finish();
                                                   }
                                               }
        );
        if (First_page.allEvents != null) {
            removeAllFromArray(closeEvents);
        }
        for (int i = 0; i < First_page.allEvents.getData().size(); i++) {
            Object x = First_page.allEvents.getData().keySet().toArray()[i];
            for (int j = 0; j < First_page.allEvents.getData().get(x).size(); j++) {
                Event1 cur = First_page.allEvents.getData().get(x).get(j);
                if (cur != null) {
                    if ((cur.getStatus() == 1)) {
                        if (checkTheDate(cur.getDay(), cur.getMonth(), cur.getYear())) {
                            String buf = cur.getNumber() + " on " + cur.getDay() + "/" + cur.getMonth()
                                    + "/" + cur.getYear() + " at " + cur.getHour() + " to " + "" + cur.getType();
                            if (!checkDuplication(buf))
                                closeEvents.add(buf);
                        }
                    }
                }
            }
        }

        if (closeEvents.isEmpty())
            closeEvents.add("There are no close queues");

        closeEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (!closeEventsListView.getItemAtPosition(0).toString().equals("There are no close queues")) {
                    Position = position;
                    AllTheEventsOfClient.activity = 0;
                    closeEventsDialog exNewEventDialog = new closeEventsDialog();
                    exNewEventDialog.show(getSupportFragmentManager(), "exe");
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private boolean checkTheDate(int dayToAdd, int monthToAdd, int yearToAdd) {
        Date date = null;
        String dtStart = yearToAdd + "/" + monthToAdd + "/" + dayToAdd;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            date = format.parse(dtStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = new Date().getTime() - date.getTime();
        if (diff >= 0)
            return false;
        else return true;
    }

    private boolean checkDuplication(String buf) {
        for (String i : closeEvents) {
            if (i.equals(buf))
                return true;
        }
        return false;
    }

    private void removeAllFromArray(ArrayList<String> arrayList) {
        if (arrayList.size() > 0) {
            arrayList.removeAll(arrayList);
        }
    }
}



