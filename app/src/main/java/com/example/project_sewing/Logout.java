package com.example.project_sewing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Logout extends AppCompatDialogFragment {
    private View view;
    private Button main_BTN_Back,main_BTN_Add,main_phoneNumber;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_logout, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        final Button logout_BTN_logout = view.findViewById(R.id.logout_BTN_logout);
        logout_BTN_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        BlankFragment_Client.currentPhone = "";
                        Toast.makeText(getContext(), "Logout!", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        openFirstPage(v);
                    }
        });
        return dialog;
    }

    private void openFirstPage(View v) {
        Intent intent = new Intent(getContext(),First_page.class);
        getActivity().finish();
        startActivity(intent);
    }
}
