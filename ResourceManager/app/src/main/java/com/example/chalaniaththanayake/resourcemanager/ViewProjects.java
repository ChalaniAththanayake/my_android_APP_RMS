package com.example.chalaniaththanayake.resourcemanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewProjects extends AppCompatActivity {
    ArrayList arrayList;
    ListView list;
    CustomList2 cl;
    String projectName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_projects);
        arrayList = new ArrayList<String>();
        getProjectNames();

        TextView tvUsername = (TextView)findViewById(R.id.tvUsername);
        tvUsername.setText(DataHolder.getUsername());

        list = (ListView)findViewById(R.id.lvProject);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                projectName = list.getAdapter().getItem(position).toString();
                getProjectData();
            }
        });

        final Button btnMenu = (Button)findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewProjects.this,MainMenu.class);
                startActivity(i);
            }
        });

        final Button btnlogout = (Button) findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewProjects.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void getProjectNames() {
       // final String URL = "https://chemic-flash.000webhostapp.com/ResourceManagement/resource_manager/getprojectNames.php";
        final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/resource_manager/getprojectNames.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("server_response");
                            int count = 0;
                            String projectTitle="";
                            arrayList.clear();
                            while (count < jsonArray.length()) {
                                JSONObject JO = jsonArray.getJSONObject(count);
                                projectTitle = JO.getString("projectTitle");
                                arrayList.add(projectTitle);
                                count++;
                            }
                            cl = new CustomList2(ViewProjects.this, arrayList);
                            list.setAdapter(cl);
                            cl.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewProjects.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("username", DataHolder.getUsername());
                return params;
            }
        };
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }

    public void getProjectData() {
        //final String URL = "https://chemic-flash.000webhostapp.com/ResourceManagement/resource_manager/getprojectdata.php";
        final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/resource_manager/getprojectdata.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("server_response");
                            int count = 0;
                            String projectTitle="",projectDescription="",requiredSkills="",startDate="",endDate="",selectedUsers="",acceptedUsers="",rejectedUsers="";
                                JSONObject JO = jsonArray.getJSONObject(count);
                                projectTitle = JO.getString("projectTitle");
                                projectDescription = JO.getString("projectDescription");
                                requiredSkills = JO.getString("requiredSkills");
                                startDate = JO.getString("startDate");
                                endDate = JO.getString("endDate");
                                selectedUsers = JO.getString("selectedUsers");
                                acceptedUsers = JO.getString("acceptedUsers");
                                rejectedUsers = JO.getString("rejectedUsers");

                            AlertDialog.Builder builder = new AlertDialog.Builder(ViewProjects.this);
                            builder.setMessage("Project Title: "+projectTitle+"\nProject Description: "+projectDescription+"\nRequired Skills: "+requiredSkills+"\nStart Date: "+startDate+"\nEnd Date: "+endDate+"\nSelected Users: "+selectedUsers+"\nAccepted Users: "+acceptedUsers+"\nRejected Users: "+rejectedUsers).setNegativeButton("OK", null).create().show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewProjects.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("projectName",projectName);
                return params;
            }
        };
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }
}
