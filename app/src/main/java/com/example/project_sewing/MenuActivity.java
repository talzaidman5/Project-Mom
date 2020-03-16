package com.example.project_sewing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MenuActivity extends AppCompatActivity {
    private MenuClientFragment menuClientFragment;
    private MenuManagerFragment menuManagerFragment;
    private MenuNewManagerFragment menuNewManagerFragment;
    private final int  USER = 0, MANAGER =1,NEW_MANAGER=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(First_page.whoIsTheUser == USER){
            menuClientFragment = new MenuClientFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menu_frameLayout, menuClientFragment);
            transaction.commit();
        }
        else if(First_page.whoIsTheUser == MANAGER){

            menuManagerFragment = new MenuManagerFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menu_frameLayout, menuManagerFragment);
            transaction.commit();
        }
        else if(First_page.whoIsTheUser == NEW_MANAGER){

            menuNewManagerFragment = new MenuNewManagerFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menu_frameLayout, menuNewManagerFragment);
            transaction.commit();
        }
    }

}
