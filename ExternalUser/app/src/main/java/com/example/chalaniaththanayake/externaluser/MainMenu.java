package com.example.chalaniaththanayake.externaluser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area1);

        String username = DataHolder.getUsername();
        final TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvUsername.setText(username);

        final Button btnMyProfile = (Button) findViewById(R.id.btnMyProfile);
        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,EditProfile.class);
                startActivity(i);
            }
        });

        final Button btnPendingProjects = (Button) findViewById(R.id.btnPendingProjects);
        btnPendingProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,PendingProjects.class);
                startActivity(i);
            }
        });

        final Button btnAcceptedProjects = (Button) findViewById(R.id.btnAcceptedProjects);
        btnAcceptedProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,AcceptedProjects.class);
                startActivity(i);
            }
        });

        final Button btnRejectedProjects = (Button) findViewById(R.id.btnRejectedProjects);
        btnRejectedProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,RejectedProjects.class);
                startActivity(i);
            }
        });

        final Button btnlogout = (Button) findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
