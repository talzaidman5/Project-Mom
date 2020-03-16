package com.example.project_sewing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class finishSewing extends AppCompatDialogFragment {
    private View view;
    private final int MANAGER =1;
    private ListView cal_ListView;
    private ArrayList<String> allOnDay = new ArrayList<>();
    private final int DONE =2;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
    private Button finish_sewing_BTN_back,finish_sewing_BTN_send;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_finish_sewing, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        cal_ListView = view.findViewById(R.id.cal_ListView);
        finish_sewing_BTN_back = view.findViewById(R.id.finish_sewing_BTN_back);
        finish_sewing_BTN_send =  view.findViewById(R.id.finish_sewing_BTN_send);

        if (First_page.whoIsTheUser == MANAGER) {
            final String[] number = {""};

            finish_sewing_BTN_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });
            final CheckBox finish_sewing_CB = view.findViewById(R.id.finish_sewing_CB);
            final Button finish_sewing_BTN_send = view.findViewById(R.id.finish_sewing_BTN_send);
            finish_sewing_BTN_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finish_sewing_CB.isChecked()) {
                        String[] str = Calendar1.allOnDay.get(Calendar1.Position).split(":");
                        String hour = (str[0].substring(str[0].length() - 2, str[0].length())) + ":" + str[1].substring(0, 2);
                        for (int i = 0; i < First_page.allEvents.getData().size(); i++) {
                            Object x = First_page.allEvents.getData().keySet().toArray()[i];
                            for (int j = 0; j < First_page.allEvents.getData().get(x).size(); j++) {
                                Event1 curEvent = First_page.allEvents.getData().get(x).get(j);
                                if (curEvent.getDay() == Calendar1.dayChoose && curEvent.getMonth() == Calendar1.monthChoose  && curEvent.getHour().equals(hour) && curEvent.getYear() == Calendar1.yearChoose) {
                                    curEvent.setStatus(DONE);
                                    Toast.makeText(getContext(), "Whatsapp will open !", Toast.LENGTH_SHORT).show();

                                    myRef.child("clients").setValue(First_page.allEvents);

                                    number[0] = curEvent.getNumber();
                                }
                            }
                        }
                        myRef.child("clients").setValue(First_page.allEvents);
                        onClickWhatsApp(number[0]);
                        dialog.dismiss();
                    }
                }
            });
        }

        return dialog;

    }
    public void onClickWhatsApp(String number) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        i.putExtra(Intent.EXTRA_TEXT,"Ready");
        getActivity().finish();
        startActivity(Intent.createChooser(i, ""));
    }

}


