package com.example.project_sewing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class AllTheEventsOfClient extends AppCompatActivity {

    public static ListView allEventsOfClient_ListView,allEventsOfClient_ListViewWait;
    public static ArrayAdapter<String> arrayAdapterOK,arrayAdapterWaiting;
    public static ArrayList<String> allOnDayOK = new ArrayList<>();
    private ArrayList<String> allOnDayWaiting = new ArrayList<>();
    private Button allEventsOfClient_back;
    public static int Position;
   public static int activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_the_events_of_client);
        checkEvents();
        allEventsOfClient_ListViewWait = findViewById(R.id.allEventsOfClient_ListViewWait);
        allEventsOfClient_ListView = findViewById(R.id.allEventsOfClient_ListView);
        arrayAdapterOK = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, allOnDayOK){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =  super.getView(position,convertView,parent);
                return view;
            }
        };
        allEventsOfClient_ListView.setAdapter(arrayAdapterOK);
        allEventsOfClient_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Position = position;
                activity=1;
                if(!allEventsOfClient_ListView.getItemAtPosition(position).equals("There are no future queues")) {
                    cancelEvent exNewEventDialog = new cancelEvent();
                    exNewEventDialog.show(getSupportFragmentManager(), "exe");
                    arrayAdapterOK.notifyDataSetChanged();
                }
            }
        });

        arrayAdapterWaiting = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, allOnDayWaiting){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =  super.getView(position,convertView,parent);
                return view;
            }
        };
        allEventsOfClient_ListViewWait.setAdapter(arrayAdapterWaiting);
        allEventsOfClient_back = findViewById(R.id.allEventsOfClient_back);
        allEventsOfClient_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkEvents() {
        removeAllFromArray(allOnDayWaiting);
        removeAllFromArray(allOnDayOK);

        if (First_page.allEvents != null) {
            ArrayList<Event1> elist = First_page.allEvents.getData().get(BlankFragment_Client.currentPhone);
            if(elist != null) {
                for (int i = 0; i < elist.size(); i++) {
                    if (elist.get(i).getStatus() == 1) {
                        String buf = elist.get(i).getDay() + "/" + elist.get(i).getMonth() + "/" + elist.get(i).getYear() + " at " + elist.get(i).getHour();
                        if(!checkDuplication(buf))
                            allOnDayOK.add(buf);
                    }
                    else
                        allOnDayWaiting.add(elist.get(i).getDay() + "/" + elist.get(i).getMonth() + "/" + elist.get(i).getYear() + " at " + elist.get(i).getHour());
                }
            }
            if(allOnDayWaiting.isEmpty())
                allOnDayWaiting.add("There are no queues waiting for approval");

            if(allOnDayOK.isEmpty())
                allOnDayOK.add("There are no future queues");
        }
    }

    private boolean checkDuplication(String buf) {
        for (String i :allOnDayOK ) {
            if(i.equals(buf))
                return true;
        }
        return false;
    }

    private void removeAllFromArray(ArrayList<String>arrayList){
        if(arrayList.size()>0) {
            arrayList.removeAll(arrayList);
        }
    }
}
