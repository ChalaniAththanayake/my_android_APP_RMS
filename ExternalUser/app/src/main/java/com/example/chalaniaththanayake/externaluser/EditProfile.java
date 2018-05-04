//To view and edit profile details
package com.example.chalaniaththanayake.externaluser;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    String fname="", lname="", hometown="", phoneno="", email="", dob="", sql="", currentpassword="", newpassword="";
    String skill="", skillAll="";
    TextView tvSkill;
    int defaultBirthYear=1990, defaultBirthMonth=0, defaultBirthDay=01;
    int BirthYear, BirthMonth, BirthDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final TextView tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvUsername.setText(DataHolder.getUsername());
        tvSkill = (TextView) findViewById(R.id.tvSkills);

        getUserData();
        skillSelect();

        final Button btnUpdateProfile = (Button) findViewById(R.id.btnUpdateProfile);
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileUpdate();
            }
        });

        final Button btnMenu = (Button)findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditProfile.this,MainMenu.class);
                startActivity(i);
            }
        });

        final EditText etDOB = (EditText) findViewById(R.id.etDOB);
        etDOB.setFocusable(false);
        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BirthDayPick();
            }
        });

        final Button btnlogout = (Button) findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditProfile.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void getUserData() {
        final EditText etFirstname = (EditText) findViewById(R.id.etFirstname);
        final EditText etLastname = (EditText) findViewById(R.id.etLastname);
        final EditText et_hometown = (EditText) findViewById(R.id.et_hometown);
        final EditText etPhoneno = (EditText) findViewById(R.id.etPhoneno);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etDOB = (EditText)findViewById(R.id.etDOB);

        final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/external_user/Get_Profile_Details.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("server_response");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject JO = jsonArray.getJSONObject(i);
                                fname = JO.getString("firstname");
                                lname = JO.getString("lastname");
                                hometown = JO.getString("hometown");
                                phoneno = JO.getString("telno");
                                email = JO.getString("email");
                                dob = JO.getString("dob");
                                defaultBirthYear= Integer.parseInt(dob.substring(0,4));
                                defaultBirthMonth= Integer.parseInt(dob.substring(5,7));
                                DecimalFormat df = new DecimalFormat("00");
                                String BirthMonth = df.format(defaultBirthMonth+1);
                                defaultBirthDay= Integer.parseInt(dob.substring(8,10));
                                skillAll = JO.getString("skills");

                                etFirstname.setText(fname);
                                etLastname.setText(lname);
                                et_hometown.setText(hometown);
                                etPhoneno.setText(phoneno);
                                etEmail.setText(email);
                                tvSkill.setText(skillAll);
                                etDOB.setText(defaultBirthYear+"-"+(BirthMonth)+"-"+defaultBirthDay);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditProfile.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("username", DataHolder.getUsername());
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

    public void skillSelect() {
        final Button btnAddSkill = (Button) findViewById(R.id.btnAddSkill);
        final Button btnRemoveSkill = (Button) findViewById(R.id.btnRemoveSkill);

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
                if (skillAll.contains(skill)) {
                    Toast.makeText(EditProfile.this, skill + " Already Added", Toast.LENGTH_SHORT).show();
                } else {
                    skillAll = skillAll + skill + ", ";
                    tvSkill.setText(skillAll);
                }
            }

        });

        btnRemoveSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skillAll.contains(skill)) {
                    skillAll = skillAll.replace(skill + ", ", "");
                    tvSkill.setText(skillAll);
                    Toast.makeText(EditProfile.this, skill + " Removed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void profileUpdate() {
        final EditText etFirstname = (EditText) findViewById(R.id.etFirstname);
        final EditText etLastname = (EditText) findViewById(R.id.etLastname);
        final EditText et_hometown = (EditText) findViewById(R.id.et_hometown);
        final EditText etPhoneno = (EditText) findViewById(R.id.etPhoneno);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etCurrentPassword = (EditText) findViewById(R.id.etCurrentPassword);
        final EditText etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        final EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        etFirstname.setError(null);
        etLastname.setError(null);
        et_hometown.setError(null);
        etPhoneno.setError(null);
        etEmail.setError(null);
        etCurrentPassword.setError(null);
        etNewPassword.setError(null);
        etConfirmPassword.setError(null);

        if (etFirstname.getText().toString().length() < 1) {
            etFirstname.setError("First Name cannot be empty");
        } else if (etLastname.getText().toString().length() < 1) {
            etLastname.setError("Last Name cannot be empty");
        } else if (et_hometown.getText().toString().length() < 1) {
            et_hometown.setError("Username cannot be empty");
        } else if (etPhoneno.getText().toString().length() < 1) {
            etPhoneno.setError("Password cannot be empty");
        } else if (etEmail.getText().toString().length() < 1) {
            etEmail.setError("Confirm Password cannot be empty");
        } else if (skillAll.length() < 1) {
            Toast.makeText(this, "Skills cannot be empty", Toast.LENGTH_LONG).show();
        } else if (!etNewPassword.getText().toString().matches(etConfirmPassword.getText().toString())) {
            etConfirmPassword.setError("Password did not match");
            etConfirmPassword.setText("");
        } else {
            fname = etFirstname.getText().toString();
            lname = etLastname.getText().toString();
            hometown = et_hometown.getText().toString();
            phoneno = etPhoneno.getText().toString();
            email = etEmail.getText().toString();
            currentpassword = etCurrentPassword.getText().toString();
            newpassword = etNewPassword.getText().toString();
            if(newpassword.length()<1){
                newpassword=currentpassword;
            }

            final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/external_user/updateprofile.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        Boolean success = obj.getBoolean("success");
                        if (success) {
                            Toast.makeText(EditProfile.this, "EditProfile Successfully updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(EditProfile.this, "Current Password is Invalid", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EditProfile.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("username", DataHolder.getUsername());
                    hashMap.put("currentpassword", currentpassword);
                    hashMap.put("newpassword", newpassword);
                    hashMap.put("fname", fname);
                    hashMap.put("lname", lname);
                    hashMap.put("hometown", hometown);
                    hashMap.put("telno", phoneno);
                    hashMap.put("email", email);
                    hashMap.put("skills", skillAll);
                    hashMap.put("dob",dob);
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

    public void BirthDayPick(){
        DatePickerDialog dpd = new DatePickerDialog(EditProfile.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                BirthYear = defaultBirthYear = year;
                BirthMonth = defaultBirthMonth = monthOfYear;
                BirthDay = defaultBirthDay = dayOfMonth;

                DecimalFormat df = new DecimalFormat("00");
                dob = BirthYear+df.format(BirthMonth)+df.format(BirthDay)+"";
                final EditText etDOB = (EditText) findViewById(R.id.etDOB);
                etDOB.setText(year+"-"+df.format(monthOfYear+1)+"-"+df.format(dayOfMonth));
            }
        },defaultBirthYear,defaultBirthMonth,defaultBirthDay);
        dpd.show();
    }

}
