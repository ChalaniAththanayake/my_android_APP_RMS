package com.example.chalaniaththanayake.externaluser;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewProjectDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view);

        String username = DataHolder.getUsername();
        final TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvUsername.setText(username);

        getProjectData();

        final Button btnAccept = (Button)findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Acceptproject();
            }
        });

        final Button btnReject = (Button)findViewById(R.id.btnReject);
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RejectProject();
            }
        });

        final Button btnlogout = (Button)findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewProjectDetails.this,MainActivity.class);
                startActivity(i);
            }
        });

    }

    public void getProjectData() {
        final TextView tvProjectTitle = (TextView)findViewById(R.id.tvProjectTitle);
        final TextView tvRequiredSkills = (TextView)findViewById(R.id.tvRequiredSkills);
        final TextView tvStartDate = (TextView)findViewById(R.id.tvStartDate);
        final TextView tvEndDate = (TextView)findViewById(R.id.tvEndDate);
        final TextView tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvProjectTitle.setText(DataHolder.getProjectTitle());

        final String sql = "SELECT requiredSkills,startDate,endDate,projectDescription FROM ProjectData WHERE projectTitle LIKE '%"+DataHolder.getProjectTitle()+"%'";

        //final String URL = "https://chemic-flash.000webhostapp.com/ResourceManagement/external_user/selectdata.php";
        final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/external_user/Get_Project_Details.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray = obj.getJSONArray("server_response");
                    String requiredSkills="", startDate="", endDate="", projectDescription="";

                    for (int count=0;count<jsonArray.length();count++) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        requiredSkills = JO.getString("c1");
                        startDate = JO.getString("c2");
                        endDate = JO.getString("c3");
                        projectDescription = JO.getString("c4");

                        tvRequiredSkills.setText(requiredSkills);
                        tvStartDate.setText(startDate);
                        tvEndDate.setText(endDate);
                        tvDescription.setText(projectDescription);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewProjectDetails.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("sql", sql);
                return hashMap;
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

    public void Acceptproject(){

final String sql = "UPDATE ProjectData SET acceptedUsers = CONCAT(acceptedUsers, '"+DataHolder.getUsername()+", ') WHERE projectTitle = '"+DataHolder.getProjectTitle()+"';";

        final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/external_user/insertdata.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        AlertDialog alertDialog = new AlertDialog.Builder(ViewProjectDetails.this).create();
                        alertDialog.setTitle("Status");
                        alertDialog.setMessage("Accept Successful !!!");
                        alertDialog.show();
                        Intent i = new Intent(ViewProjectDetails.this,PendingProjects.class);
                        startActivity(i);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewProjectDetails.this);
                        builder.setMessage("Accept Failed!").setNegativeButton("Retry", null).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewProjectDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("sql", sql);
                return hashMap;
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

    public void RejectProject(){
        final String sql = "UPDATE ProjectData SET rejectedUsers = CONCAT(rejectedUsers, '"+DataHolder.getUsername()+", ') WHERE projectTitle = '"+DataHolder.getProjectTitle()+"';";

        final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/external_user/insertdata.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        AlertDialog alertDialog = new AlertDialog.Builder(ViewProjectDetails.this).create();
                        alertDialog.setTitle("Status");
                        alertDialog.setMessage("Reject Successful !!!");
                        alertDialog.show();
                        Intent i = new Intent(ViewProjectDetails.this,PendingProjects.class);
                        startActivity(i);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewProjectDetails.this);
                        builder.setMessage("Reject Failed!").setNegativeButton("Retry", null).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewProjectDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("sql", sql);
                return hashMap;
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
