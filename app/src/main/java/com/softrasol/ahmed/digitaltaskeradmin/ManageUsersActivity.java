package com.softrasol.ahmed.digitaltaskeradmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ManageUsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        toolbarInflation();
    }
    private void toolbarInflation() {

        Toolbar toolbar;
        TextView textView;
        ImageButton mBtnBack;
        toolbar = findViewById(R.id.toolbar_manageUsers);
        textView = toolbar.findViewById(R.id.toolbarText);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        textView.setText("Manage User");
        mBtnBack = toolbar.findViewById(R.id.btnBack);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
