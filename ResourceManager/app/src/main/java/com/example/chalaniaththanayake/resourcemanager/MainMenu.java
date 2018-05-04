package com.example.chalaniaththanayake.resourcemanager;

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
        setContentView(R.layout.activity_user_options);

        //Button btnProjectRequests = (Button) findViewById(R.id.btnProjectRequests);
        Button btnCreateProject = (Button) findViewById(R.id.btnCreateProject);
        Button btnViewProjects = (Button) findViewById(R.id.btnViewProjects);

        TextView tvUsername = (TextView)findViewById(R.id.tvUsername);
        tvUsername.setText(DataHolder.getUsername());

        btnCreateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,CreateProject.class);
                startActivity(i);
            }
        });

        btnViewProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,ViewProjects.class);
                startActivity(i);
            }
        });

        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenu.this,MainActivity.class);
                startActivity(i);
            }
        });


    }
}