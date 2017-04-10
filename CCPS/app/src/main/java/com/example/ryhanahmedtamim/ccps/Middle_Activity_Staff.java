package com.example.ryhanahmedtamim.ccps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Middle_Activity_Staff extends AppCompatActivity {

    Button submitDutyButton;
    Button pendingDutyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle__activity__staff);

        submitDutyButton = (Button) findViewById(R.id.submitDutyButton);
        pendingDutyButton = (Button) findViewById(R.id.pendingDutyButton);

       final String contractId = getIntent().getStringExtra("id");

        submitDutyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Middle_Activity_Staff.this,StaffActivity.class);
                intent.putExtra("id",contractId);
                startActivity(intent);
            }
        });
        pendingDutyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Middle_Activity_Staff.this,PendingOfStaffActivity.class);
                intent.putExtra("id",contractId);
                startActivity(intent);

            }
        });
    }
}
