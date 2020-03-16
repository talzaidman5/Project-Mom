package com.example.project_sewing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class newEventDialog extends AppCompatDialogFragment {

    private final int  USER = 0, MANAGER =1, THIS_YEAR = 2020;
    private Button btn_back,add_event;
    private Spinner newEvent_SPI_type,newEvent_SPI_day,newEvent_SPI_month,newEvent_SPI_year,newEvent_SPI_hour;
    private EditText newEvent_EDT_phoneNumber;
    private int yearToAdd,dayToAdd, monthToAdd;
    private String typeToAdd, hourToAdd;
    private View view;
    private Boolean checkDetails = true;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_add_event,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        btn_back = view.findViewById(R.id.btn_back);

        initSpinners();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        newEvent_EDT_phoneNumber =  view.findViewById(R.id.newEvent_EDT_phoneNumber);
        add_event = view.findViewById(R.id.add_event);
        newEvent_EDT_phoneNumber.setText(BlankFragment_Client.currentPhone);
        if(First_page.whoIsTheUser == USER) {
            newEvent_EDT_phoneNumber.setEnabled(false);
            newEvent_SPI_day.setSelection(Calendar1.dayChoose);
            newEvent_SPI_month.setSelection(Calendar1.monthChoose);
            newEvent_SPI_year.setSelection(2020%THIS_YEAR);
        }
        add_event.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                Event1 event1;
                if (checkAllDetails()) {
                    if (checkTheDate(dayToAdd, monthToAdd, yearToAdd)) {
                        if (checkValidDate(dayToAdd, monthToAdd, yearToAdd)) {
                            if (First_page.whoIsTheUser == MANAGER)
                                event1 = new Event1(newEvent_EDT_phoneNumber.getText().toString(), typeToAdd, 0, dayToAdd, monthToAdd, yearToAdd, hourToAdd, 1);
                            else
                                event1 = new Event1(newEvent_EDT_phoneNumber.getText().toString(), typeToAdd, 0, dayToAdd, monthToAdd, yearToAdd, hourToAdd, 0);

                            if (checkIfAvailable(event1)) {
                                if (First_page.allEvents != null)
                                    First_page.allEvents.addToList(newEvent_EDT_phoneNumber.getText().toString(), event1);
                                else {
                                    First_page.allEvents = new AllEvents();
                                    Map<String, ArrayList<Event1>> tempData;
                                    tempData = new HashMap<String, ArrayList<Event1>>();
                                    ArrayList<Event1> temp = new ArrayList<>();
                                    temp.add(event1);
                                    First_page.allEvents.setData(tempData);
                                }
                                myRef.child("clients").setValue(First_page.allEvents);
                                Toast.makeText(getContext(), "Event created!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else
                                Toast.makeText(getContext(), "There is a queue at this time in the system!", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getContext(), "Invalid date!", Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getContext(), "The date has already passed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return dialog;
    }

    private void initSpinners() {
        newEvent_SPI_day = view.findViewById(R.id.newEvent_SPI_day);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.day,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newEvent_SPI_day.setAdapter(adapter);
        newEvent_SPI_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isDigit(newEvent_SPI_day.getSelectedItem().toString()))
                    dayToAdd = Integer.valueOf(newEvent_SPI_day.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        newEvent_SPI_month = view.findViewById(R.id.newEvent_SPI_month);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),R.array.month,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newEvent_SPI_month.setAdapter(adapter2);
        newEvent_SPI_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isDigit(newEvent_SPI_month.getSelectedItem().toString()))
                    monthToAdd = Integer.valueOf(newEvent_SPI_month.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        newEvent_SPI_year = view.findViewById(R.id.newEvent_SPI_year);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getContext(),R.array.year,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newEvent_SPI_year.setAdapter(adapter3);
        newEvent_SPI_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isDigit(newEvent_SPI_year.getSelectedItem().toString()))
                    yearToAdd = Integer.valueOf(newEvent_SPI_year.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        newEvent_SPI_type = view.findViewById(R.id.newEvent_SPI_type);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(getContext(),R.array.Types,android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newEvent_SPI_type.setAdapter(adapter4);
        newEvent_SPI_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeToAdd = newEvent_SPI_type.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        newEvent_SPI_hour = view.findViewById(R.id.newEvent_SPI_hour);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(getContext(),R.array.hour,android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newEvent_SPI_hour.setAdapter(adapter5);

        newEvent_SPI_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (newEvent_SPI_month.getSelectedItem().toString().equals("Hour") == false)
                    hourToAdd = newEvent_SPI_hour.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private boolean isDigit(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    private boolean checkAllDetails() {
        checkDetails = true;

        if (newEvent_EDT_phoneNumber.getText().toString().matches("")) {
            newEvent_EDT_phoneNumber.setError("");
            checkDetails = false;
        }else {
            if (newEvent_EDT_phoneNumber.getText().toString().substring(0, 2).equals("05") == false) {
                newEvent_EDT_phoneNumber.setError("Phone number must start with 05!");
                checkDetails = false;
            }
        }
        if(newEvent_EDT_phoneNumber.getText().toString().length()!=10){
            newEvent_EDT_phoneNumber.setError("The phone number length should be 10!");
            checkDetails = false;
        }

        if (newEvent_SPI_day.getSelectedItem().equals("Choose a day")) {
            ((TextView)newEvent_SPI_day.getSelectedView()).setError("");
            checkDetails = false;
        }

        if (newEvent_SPI_month.getSelectedItem().equals("Choose a month")) {
            ((TextView) newEvent_SPI_month.getSelectedView()).setError("error");
            checkDetails = false;

        }

        if (newEvent_SPI_hour.getSelectedItem().equals("Choose a hour")) {
            ((TextView) newEvent_SPI_hour.getSelectedView()).setError("");
            checkDetails = false;

        }

        if (newEvent_SPI_type.getSelectedItem().equals("Choose the type")) {
            ((TextView)newEvent_SPI_type.getSelectedView()).setError("");
            checkDetails = false;
        }

        return checkDetails;
    }

    private boolean checkTheDate(int dayToAdd, int monthToAdd, int yearToAdd) {
        Date date = null;
        String dtStart = yearToAdd+"/"+monthToAdd+"/"+dayToAdd;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            date = format.parse(dtStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = new Date().getTime() - date.getTime();
        if(diff >= 0 )
            return false;
        else return true;
    }

    private boolean checkValidDate(int dayToAdd, int monthToAdd, int yearToAdd) {
        String DATE_FORMAT = "yyyy/MM/dd";
        String date = yearToAdd+"/"+monthToAdd+"/"+dayToAdd;
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean checkIfAvailable(Event1 event) {
        if(First_page.allEvents == null)
            First_page.allEvents = new AllEvents();
        return First_page.allEvents.isExist(event);
    }

}
