package com.example.project_sewing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class waitingToConfirmationActivity extends AppCompatActivity {

    private ListView waitingToConfirmation_ListView;
    private ArrayAdapter<String> arrayAdapter;
    public static ArrayList<String> allOnDay = new ArrayList<>();
    private Button allEventsOfClient_back;
    public static int Position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitng_to_confirmation);
        checkEvents();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        waitingToConfirmation_ListView = findViewById(R.id.waitingToConfirmation_ListView);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, allOnDay) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                return view;
            }
        };
        waitingToConfirmation_ListView.setAdapter(arrayAdapter);
        if (allOnDay.get(0) != "There are no queues waiting for approval") {

            waitingToConfirmation_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                    Position = position;
                    ApproveQueueDialog exNewEventDialog = new ApproveQueueDialog();
                    exNewEventDialog.show(getSupportFragmentManager(), "exe");
                }
            });
        }

        allEventsOfClient_back = findViewById(R.id.allEventsOfClient_back);
        allEventsOfClient_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void checkEvents() {

        if(First_page.allEvents!= null)
            for (int i = 0; i < First_page.allEvents.getData().size(); i++) {
                Object x = First_page.allEvents.getData().keySet().toArray()[i];
                for (int j = 0; j < First_page.allEvents.getData().get(x).size(); j++) {
                    Event1 cur = First_page.allEvents.getData().get(x).get(j);
                    if(cur != null) {
                        if (cur.getStatus() == 0)
                            allOnDay.add(cur.getDay() + "/" + cur.getMonth() + "/" + cur.getYear() +
                                    " at " + cur.getHour());
                    }
                }
            }
        if(allOnDay.isEmpty())
            allOnDay.add("There are no queues waiting for approval");
    }
}
