package com.example.smartbookapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManageVoting extends AppCompatActivity {

    private CardView addCandidate, viewCandidate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_voting);

        addCandidate = findViewById (R.id.addCandidate);
        viewCandidate = findViewById (R.id.editCandidate);

        addCandidate.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ManageVoting.this, CreateCandidate.class);
                startActivity (intent);
            }
        });
        viewCandidate.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (ManageVoting.this, ResultActivityAdmin.class);
                startActivity (intent);
            }
        });
    }
}