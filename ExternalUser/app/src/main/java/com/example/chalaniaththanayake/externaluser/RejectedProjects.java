//To View Rejected Project Names in ListView
package com.example.chalaniaththanayake.externaluser;

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

public class RejectedProjects extends AppCompatActivity {
    ArrayList arrayList;
    ListView list;
    String username;
    CustomList cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejected_projects);
        list = (ListView)findViewById(R.id.lvProject);
        arrayList = new ArrayList<String>();
        SearchDBData();

        username = DataHolder.getUsername();
        final TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvUsername.setText(username);

        final Button btnMenu = (Button)findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RejectedProjects.this,MainMenu.class);
                startActivity(i);
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Data = list.getAdapter().getItem(position).toString();
                DataHolder.setProjectTitle(Data);
                Intent i = new Intent(RejectedProjects.this,ViewProjectDetails.class);
                startActivity(i);
            }
        });

        final Button btnlogout = (Button) findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RejectedProjects.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    public void SearchDBData() {
        final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/external_user/get_rejected_projects.php";
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
                            cl = new CustomList(RejectedProjects.this, arrayList);
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
                        Toast.makeText(RejectedProjects.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
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
}
