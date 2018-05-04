package com.example.chalaniaththanayake.resourcemanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SelectUsers extends AppCompatActivity {
    ArrayList arrayList;
    ListView list;
    CustomList cl;
    String sql, DBData;
    String message="";

    int defaultStartYear, defaultStartMonth, defaultStartDay;
    int StartYear, StartMonth, StartDay;
    int defaultEndYear, defaultEndMonth, defaultEndDay;
    int EndYear, EndMonth, EndDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userarea);

        Bundle logonData = getIntent().getExtras();
        sql = logonData.getString("sql");

        list = (ListView) findViewById(R.id.lv1);
        arrayList = new ArrayList<String>();
        SearchDBData();

        TextView tvUsername = (TextView)findViewById(R.id.tvUsername);
        tvUsername.setText(DataHolder.getUsername());

         final Calendar c = Calendar.getInstance();
         defaultStartYear = defaultEndYear = c.get(Calendar.YEAR); // current year
         defaultStartMonth = defaultEndMonth = c.get(Calendar.MONTH); // current month
         defaultStartDay = defaultEndDay = c.get(Calendar.DAY_OF_MONTH); // current day

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Data = list.getAdapter().getItem(position).toString();
                SearchUserData(Data);
            }
        });

        final EditText etStartDate = (EditText) findViewById(R.id.etStartDate);
        etStartDate.setFocusable(false);
        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDatePick();
            }
        });

        final EditText etEndDate = (EditText) findViewById(R.id.etEndDate);
        etEndDate.setFocusable(false);
        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndDatePick();
            }
        });

        final Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertProjectData();
                sendNotification2();
            }
        });

        final Button btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectUsers.this, MainMenu.class);
                startActivity(i);
            }
        });

        final Button btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectUsers.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    public void SearchDBData() {
        //final String URL = "https://chemic-flash.000webhostapp.com/ResourceManagement/resource_manager/search_users.php";
        final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/resource_manager/search_users.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("server_response");
                            int count = 0;
                            String name, Disaster_Description, Disaster_Time, Date;

                            arrayList.clear();
                            while (count < jsonArray.length()) {
                                JSONObject JO = jsonArray.getJSONObject(count);
                                name = JO.getString("username");
                                arrayList.add(name);

                                count++;
                            }
                            cl = new CustomList(SelectUsers.this, arrayList);
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
                        Toast.makeText(SelectUsers.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("sql", sql);
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

    public void SearchUserData(final String s) {
        //final String URL = "https://chemic-flash.000webhostapp.com/ResourceManagement/resource_manager/get_userdata.php";
        final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/resource_manager/get_userdata.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("server_response");
                            int count = 0;
                            String firstname,lastname,username, hometown, tel_no, email, dob, skills;
                            while (count < jsonArray.length()) {
                                JSONObject JO = jsonArray.getJSONObject(count);
                                firstname = JO.getString("firstname");
                                lastname = JO.getString("lastname");
                                username = JO.getString("username");
                                hometown = JO.getString("hometown");
                                tel_no = JO.getString("telno");
                                email = JO.getString("email");
                                dob = JO.getString("dob");
                                skills = JO.getString("skills");
                                DBData = "First Name: " + firstname +"\nLast Name: " + lastname +"\nUsername: " + username + "\nHometown: " + hometown + "\nTel No: " + tel_no + "\nEmail: " + email + "\nDate Of Birth: " + dob + "\nSkills: " + skills;
                                count++;
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelectUsers.this);
                            builder.setMessage(DBData)
                                    .setPositiveButton("OK", null)
                                    .create()
                                    .show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SelectUsers.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("usersql", " username LIKE '%" + s + "%'");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void insertProjectData() {
        //final String URL = "https://chemic-flash.000webhostapp.com/ResourceManagement/resource_manager/insert_project_detail.php";
        final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/resource_manager/insert_project_detail.php";

        final EditText etProjectTitle = (EditText) findViewById(R.id.etProjectTitle);
        final String projectTitle = etProjectTitle.getText().toString().trim();

        final EditText etDescription = (EditText) findViewById(R.id.etDescription);
        final String projectDescription = etDescription.getText().toString().trim();

        final String selectedUsers = DataHolder.getData();
        message = "Project Title: "+projectTitle+" Skills: "+DataHolder.getSkills()+" Description:"+projectDescription;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //System.out.println(response);
                        Toast.makeText(SelectUsers.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SelectUsers.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                DecimalFormat df = new DecimalFormat("00");
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("projectTitle", projectTitle);
                params.put("projectDescription", projectDescription);
                params.put("selectedUsers", selectedUsers);
                params.put("skills", DataHolder.getSkills());
                params.put("startDate",StartYear+df.format(StartMonth+1)+df.format(StartDay));
                params.put("endDate",EndYear+df.format(EndMonth+1)+df.format(EndDay));
                params.put("projectManager",DataHolder.getUsername());
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

    public void sendNotification2() {
        final String selectedUsers = DataHolder.getData();

        String[] splitStr = selectedUsers.split("\\s+");
        sql = "username LIKE '%" + splitStr[0] + "%'";
        sql = sql.replace(",", "");

        for (int i = 1; i < splitStr.length; i++) {
            sql = sql + " OR username LIKE '%" + splitStr[i] + "%'";
            sql = sql.replace(",", "");
        }
        //  Toast.makeText(SelectUsers.this, sql, Toast.LENGTH_LONG).show();
        //final String URL = "https://chemic-flash.000webhostapp.com/ResourceManagement/resource_manager/send_notification.php";
        final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/resource_manager/send_notification.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String str = jsonResponse.getString("success");
                    //Toast.makeText(SelectUsers.this, sql, Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectUsers.this);
                    builder.setMessage(str).setPositiveButton("OK", null).create().show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SelectUsers.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sql", sql);
                params.put("message", message);
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

    public void startDatePick(){
       // final Calendar c = Calendar.getInstance();
       // int mYear = c.get(Calendar.YEAR); // current year
       // int mMonth = c.get(Calendar.MONTH); // current month
       // int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        DatePickerDialog dpd = new DatePickerDialog(SelectUsers.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                StartYear = defaultStartYear = year;
                StartMonth = defaultStartMonth = monthOfYear;
                StartDay = defaultStartDay = dayOfMonth;

                DecimalFormat df = new DecimalFormat("00");
                final EditText etStartDate = (EditText) findViewById(R.id.etStartDate);
                etStartDate.setText(year+":"+df.format(monthOfYear+1)+":"+df.format(dayOfMonth));
            }
        },defaultStartYear,defaultStartMonth,defaultStartDay);
        dpd.show();
    }

    private void EndDatePick() {
        DatePickerDialog dpd = new DatePickerDialog(SelectUsers.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                EndYear = defaultEndYear = year;
                EndMonth = defaultEndMonth = monthOfYear;
                EndDay = defaultEndDay = dayOfMonth;

                DecimalFormat df = new DecimalFormat("00");
                final EditText etEndDate = (EditText) findViewById(R.id.etEndDate);
                etEndDate.setText(year+":"+df.format(monthOfYear+1)+":"+df.format(dayOfMonth));
            }
        },defaultEndYear,defaultEndMonth,defaultEndDay);
        dpd.show();
    }



}
