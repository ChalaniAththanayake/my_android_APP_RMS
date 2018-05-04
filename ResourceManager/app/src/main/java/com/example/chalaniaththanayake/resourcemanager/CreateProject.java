package com.example.chalaniaththanayake.resourcemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreateProject extends AppCompatActivity{

    String skill,skillAll="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final Button btnAddSkill = (Button) findViewById(R.id.btnAddSkill);
        final TextView tvSetectedSkills = (TextView) findViewById(R.id.tvSetectedSkills);
        final Button btnRemoveSkill = (Button) findViewById(R.id.btnRemoveSkill);
        final Button btnSearch = (Button)findViewById(R.id.btnSearch);
        final Button btnLogout = (Button)findViewById(R.id.btnLogout);
        final Button btnMenu = (Button)findViewById(R.id.btnMenu);

        TextView tvUsername = (TextView)findViewById(R.id.tvUsername);
        tvUsername.setText(DataHolder.getUsername());

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateProject.this,MainMenu.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateProject.this,MainActivity.class);
                startActivity(i);
            }
        });

        Spinner spSkill = (Spinner) findViewById(R.id.spinSkills);
        spSkill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                skill = parent.getSelectedItem().toString().trim();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnAddSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(skillAll.contains(skill+",")){Toast.makeText(CreateProject.this,skill+" Already Added",Toast.LENGTH_SHORT).show();}
                else{ skillAll = skillAll + skill + ", ";
                    tvSetectedSkills.setText(skillAll);}
            }

        });

        btnRemoveSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(skillAll.contains(skill+",")){
                    skillAll = skillAll.replace(skill+",","");
                    tvSetectedSkills.setText(skillAll);
                    Toast.makeText(CreateProject.this,skill+" Removed",Toast.LENGTH_SHORT).show();}
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHolder.setSkills(skillAll);
                String[] splitStr = skillAll.split("\\s+");
                String sql = " skills LIKE '%"+splitStr[0]+"%'";

                for(int i=1;i<splitStr.length;i++){
                    sql = sql + " AND skills LIKE '%"+splitStr[i]+"%'";
                }

                Intent i = new Intent(CreateProject.this,SelectUsers.class);
                i.putExtra("sql",sql);
                startActivity(i);
            }
        });
    }

}
