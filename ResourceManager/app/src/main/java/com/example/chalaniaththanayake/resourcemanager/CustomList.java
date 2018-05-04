//View User with Select Button... return all users layouts to view in List View
//Create Users ListView Items as a Custom Layout
package com.example.chalaniaththanayake.resourcemanager;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String>{
    private final Activity context;
    private final ArrayList web;
    private String selectedUsers="";

    public CustomList(Activity context, ArrayList web) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;

    }
    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(web.get(position).toString());

        final Button btnSelect = (Button)rowView.findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnSelect.getText().equals("Selected")){
                    selectedUsers = selectedUsers.replace(web.get(position)+", ","");
                    btnSelect.setText("Select");
                    btnSelect.setBackgroundColor(Color.RED);
                    DataHolder.setData(selectedUsers);
                }
                else {
                    btnSelect.setText("Selected");
                    btnSelect.setBackgroundColor(Color.GREEN);
                    selectedUsers = selectedUsers+web.get(position).toString()+", ";
                    DataHolder.setData(selectedUsers);
                }
                Toast.makeText(context,"Selected Users: "+selectedUsers,Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }

}