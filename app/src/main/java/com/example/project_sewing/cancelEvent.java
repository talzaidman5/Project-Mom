package com.example.project_sewing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.project_sewing.AllTheEventsOfClient.allOnDayOK;


public class cancelEvent extends AppCompatDialogFragment {
    private View view;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("message");
    private Boolean statusNo=false,statusYes=false;
    public static boolean isDelete = false;
    private boolean check = false;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_cancel_event, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        if(AllTheEventsOfClient.allEventsOfClient_ListView!=null) {
            if (!AllTheEventsOfClient.allEventsOfClient_ListView.getItemAtPosition(0).toString().equals("There are no future queues")) {
                final Button cancelEvent_ok = view.findViewById(R.id.cancelEvent_ok);
                final CheckBox cancelEvent_CB_yes = view.findViewById(R.id.cancelEvent_CB_yes);
                final CheckBox cancelEvent_CB_no = view.findViewById(R.id.cancelEvent_CB_no);

                cancelEvent_CB_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!statusNo) {
                            cancelEvent_CB_yes.setEnabled(false);
                            statusNo = true;
                        } else {
                            cancelEvent_CB_yes.setEnabled(true);
                            cancelEvent_CB_no.setEnabled(true);
                            statusNo = false;
                        }
                    }
                });
                cancelEvent_CB_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!statusYes) {
                            cancelEvent_CB_yes.setEnabled(true);
                            cancelEvent_CB_no.setEnabled(false);
                            statusYes = true;
                        } else {
                            cancelEvent_CB_no.setEnabled(true);
                            cancelEvent_CB_yes.setEnabled(true);
                            statusYes = false;
                        }
                    }
                });
                cancelEvent_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cancelEvent_CB_yes.isChecked()) {
                            String[] str = allOnDayOK.get(AllTheEventsOfClient.Position).split(":");
                            String hour = (str[0].substring(str[0].length() - 2, str[0].length())) + ":" + str[1].substring(0, 2);
                            for (int i = 0; i < First_page.allEvents.getData().size(); i++) {
                                Object x = First_page.allEvents.getData().keySet().toArray()[i];
                                for (int j = 0; j < First_page.allEvents.getData().get(x).size(); j++) {
                                    String[] date = str[0].split("/");
                                    if ((First_page.allEvents.getData().get(x).get(j).getDay() + "").equals(date[0]) &&
                                            (First_page.allEvents.getData().get(x).get(j).getMonth() + "").equals(date[1]) &&
                                            (First_page.allEvents.getData().get(x).get(j).getNumber() + "").equals(BlankFragment_Client.currentPhone)) {

                                        if(AllTheEventsOfClient.activity == 0) {
                                            CloseEvents.closeEvents.remove(CloseEvents.Position);
                                            if(CloseEvents.closeEvents.size()==0)
                                                CloseEvents.closeEvents.add("There are no close queues");
                                        }
                                        else {
                                            allOnDayOK.remove(AllTheEventsOfClient.Position);
                                            if(allOnDayOK.size()==0)
                                                allOnDayOK.add("There are no future queues");
                                        }
                                        First_page.allEvents.getData().get(x).remove(j);
                                        isDelete = true;
                                        myRef.child("clients").setValue(First_page.allEvents);
                                        check = true;
                                        Toast.makeText(getContext(), "The queue has been deleted!", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            }
                        }
                        else if (cancelEvent_CB_no.isChecked()) {
                            dialog.dismiss();
                        }
                    }
                });
            }
        }
        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (check == true) {
            if (AllTheEventsOfClient.activity == 0)
                CloseEvents.arrayAdapter.notifyDataSetChanged();
            else
                AllTheEventsOfClient.arrayAdapterOK.notifyDataSetChanged();
        }
    }
}
