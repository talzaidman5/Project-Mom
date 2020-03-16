package com.example.project_sewing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApproveQueueDialog extends AppCompatDialogFragment {
    private View view;
    private Boolean statusNo=false,statusYes=false;
    private final int APPROVED=1;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_finish_sewing, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        final CheckBox status_CB_yes = view.findViewById(R.id.status_CB_yes);
        final CheckBox status_CB_no = view.findViewById(R.id.status_CB_no);
        final Button status_NTB_send = view.findViewById(R.id.status_NTB_send);
        status_CB_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!statusNo) {
                    status_CB_yes.setEnabled(false);
                    statusNo = true;
                }
                else {
                    status_CB_yes.setEnabled(true);
                    status_CB_no.setEnabled(true);
                    statusNo = false;
                }
            }
        });

        status_CB_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!statusYes) {
                    status_CB_yes.setEnabled(true);
                    status_CB_no.setEnabled(false);
                    statusYes = true;
                }
                else {
                    status_CB_no.setEnabled(true);
                    status_CB_yes.setEnabled(true);
                    statusYes = false;
                }
            }
        });

        status_NTB_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status_CB_yes.isChecked()) {
                    for (int i = 0; i < First_page.allEvents.getData().size(); i++) {
                        Object x = First_page.allEvents.getData().keySet().toArray()[i];
                        for (int j = 0; j < First_page.allEvents.getData().get(x).size(); j++) {
                            String[] split = waitingToConfirmationActivity.allOnDay.get(waitingToConfirmationActivity.Position).split("/");
                            Event1 cur = First_page.allEvents.getData().get(x).get(j);
                            if(cur != null) {
                                if ((cur.getDay() + "").equals(split[0]) && (cur.getMonth() + "").equals(split[1])
                                        && (cur.getYear() + "").equals(split[2].substring(0, 4))
                                        && (cur.getHour() + "").equals(split[2].substring(8, 13))) {
                                    cur.setStatus(APPROVED);
                                    First_page.allMassages.getAllMassages().add(new Massage(cur.getDay(), cur.getMonth(), cur.getYear(), cur.getHour(), cur.getNumber()," has been approved"));
                                    myRef.child("massages").setValue(First_page.allMassages);
                                }
                            }
                        }
                    }
                    myRef.child("clients").setValue(First_page.allEvents);
                    Toast.makeText(getContext(), "The queue was successfully approved!", Toast.LENGTH_SHORT).show();
                    waitingToConfirmationActivity.allOnDay.remove(waitingToConfirmationActivity.Position);
                    dialog.dismiss();
                }
                else if(status_CB_no.isChecked()) {
                    if (First_page.allEvents != null) {
                        for (int i = 0; i < First_page.allEvents.getData().size(); i++) {
                            Object x = First_page.allEvents.getData().keySet().toArray()[i];
                            for (int j = 0; j < First_page.allEvents.getData().get(x).size(); j++) {
                                String[] split = waitingToConfirmationActivity.allOnDay.get(waitingToConfirmationActivity.Position).split("/");
                                Event1 cur = First_page.allEvents.getData().get(x).get(j);
                                if (cur != null) {
                                    if ((cur.getDay() + "").equals(split[0]) && (cur.getMonth() + "").equals(split[1])
                                            && (cur.getYear() + "").equals(split[2].substring(0, 4))
                                            && (cur.getHour() + "").equals(split[2].substring(8, 13))) {
                                        First_page.allMassages.getAllMassages().add(new Massage(cur.getDay(), cur.getMonth(), cur.getYear(), cur.getHour(), cur.getNumber(), " has been disapproved"));
                                        First_page.allEvents.getData().get(x).remove(j);
                                        waitingToConfirmationActivity.allOnDay.remove(waitingToConfirmationActivity.Position);
                                        dialog.dismiss();
                                        myRef.child("massages").setValue(First_page.allMassages);
                                        myRef.child("clients").setValue(First_page.allEvents);
                                        Toast.makeText(getContext(), "A cancellation message was sent to the client!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        return dialog;
    }
}
