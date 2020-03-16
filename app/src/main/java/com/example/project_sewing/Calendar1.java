package com.example.project_sewing;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public  class Calendar1 extends AppCompatActivity {

    private CalendarView main_CAL;
    private Button main_BTN_Back,main_BTN_Add,main_phoneNumber;
    private ListView cal_ListView;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
    private ArrayAdapter<String> arrayAdapter;
    public static ArrayList<String> allOnDay = new ArrayList<>();
    private final int MANAGER =1;
    public static int dayChoose,monthChoose,yearChoose, Position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        First_page.allEvents = new AllEvents();
        cal_ListView = findViewById(R.id.cal_ListView);
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                First_page.allEvents = dataSnapshot.child("clients").getValue(AllEvents.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        main_CAL = findViewById(R.id.main_CAL);
        main_BTN_Back = findViewById(R.id.main_BTN_Back);
        main_BTN_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        main_BTN_Add = findViewById(R.id.main_BTN_Add);
        main_phoneNumber = findViewById(R.id.main_phoneNumber);
        main_BTN_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEventDialog exNewEventDialog = new newEventDialog();
                exNewEventDialog.show(getSupportFragmentManager(),"exe");

            }
        });
        main_phoneNumber.setText(BlankFragment_Client.currentPhone);
        main_phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(First_page.whoIsTheUser == MANAGER){
                    main_phoneNumber.setVisibility(View.INVISIBLE);
                }else {
                    Logout exNewEventDialog = new Logout();
                    exNewEventDialog.show(getSupportFragmentManager(),"exe");
                }
            }
        });

        main_CAL.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, final int year, final int month, final int dayOfMonth) {
                printTheEvents(year, month, dayOfMonth);
                dayChoose = dayOfMonth;
                monthChoose = month + 1;
                yearChoose = year;
                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, allOnDay) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        return view;
                    }
                };
                cal_ListView.setAdapter(arrayAdapter);
                cal_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Position = position;
                        if(!allOnDay.get(position).equals("You don't have a queues today")) {
                            finishSewing exNewEventDialog = new finishSewing();
                            exNewEventDialog.show(getSupportFragmentManager(), "exe");
                        }
                    }
                });
            }
        });
    }
    public void printTheEvents(int year, int month, int dayOfMonth) {
        removeAllFromArrayL();
        if (First_page.allEvents != null && First_page.allEvents.getData()!=null )
            for (int i = 0; i < First_page.allEvents.getData().size(); i++) {
                Object x = First_page.allEvents.getData().keySet().toArray()[i];
                for (int j = 0; j < First_page.allEvents.getData().get(x).size(); j++) {
                    Event1 cur = First_page.allEvents.getData().get(x).get(j);
                    if (cur != null) {
                        if (cur.getMonth() == month + 1 && cur.getDay() == dayOfMonth && cur.getYear() == year) {
                            if (First_page.whoIsTheUser == MANAGER) {
                                if (cur.getStatus() == 1)
                                    allOnDay.add(cur.getNumber() + " for " + cur.getType() + " at " + cur.getHour());
                                else if (cur.getStatus() == 0)
                                    allOnDay.add("You have a queue for approval" + cur.getNumber() + " for " + cur.getType() + " at " + cur.getHour());
                            } else {
                                if (cur.getNumber().equals(BlankFragment_Client.currentPhone)) {
                                    if (cur.getStatus() == 1)
                                        allOnDay.add("You have a queue at " + cur.getHour());
                                }
                            }
                        }
                    }
                }
            }
        if(allOnDay.isEmpty())
            allOnDay.add("You don't have a queues today");
    }

    private void removeAllFromArrayL() {
        for (int i = 0; i < allOnDay.size(); i++)
            allOnDay.remove(i);
    }

}


