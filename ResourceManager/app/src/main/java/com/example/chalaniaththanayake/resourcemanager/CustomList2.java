//To Create ListView Items as a Custom Layout
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

public class CustomList2 extends ArrayAdapter<String>{
    private final Activity context;
    private final ArrayList web;

    public CustomList2(Activity context, ArrayList web) {
        super(context, R.layout.list_single2, web);
        this.context = context;
        this.web = web;

    }
    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single2, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(web.get(position).toString());

        return rowView;
    }

}