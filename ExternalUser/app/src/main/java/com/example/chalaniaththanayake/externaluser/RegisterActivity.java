package com.example.chalaniaththanayake.externaluser;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    String username="", password="", confirmPassword="", hometown="", email="", fname="", lname="";
    int dob, telno;
    static String skill="", skillAll="";
    TextView tvSkill;
    int defaultBirthYear=1990, defaultBirthMonth=0, defaultBirthDay=01;
    int BirthYear, BirthMonth, BirthDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvSkill = (TextView) findViewById(R.id.tvSkills); //

        final EditText etDOB = (EditText) findViewById(R.id.etDOB);
        etDOB.setFocusable(false);
        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BirthDayPick();
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

        final Button btnAddSkill = (Button) findViewById(R.id.btnAddSkill);
        btnAddSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skillAll.contains(skill)) {
                    Toast.makeText(RegisterActivity.this, skill + " Already Added", Toast.LENGTH_SHORT).show();
                } else {
                    skillAll = skillAll + skill + ", ";
                    tvSkill.setText(skillAll);
                }
            }

        });

        final Button btnRemoveSkill = (Button) findViewById(R.id.btnRemoveSkill);
        btnRemoveSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skillAll.contains(skill)) {
                    skillAll = skillAll.replace(skill + ", ", "");
                    tvSkill.setText(skillAll);
                    Toast.makeText(RegisterActivity.this, skill + " Removed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    public void Register() {
        final EditText etFName = (EditText) findViewById(R.id.etFName);
        final EditText etLName = (EditText) findViewById(R.id.etLName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        final EditText et_Hometown = (EditText) findViewById(R.id.et_hometown);
        final EditText et_Phoneno = (EditText) findViewById(R.id.etPhoneno);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etDOB = (EditText) findViewById(R.id.etDOB);

        etFName.setError(null);
        etLName.setError(null);
        etUsername.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);
        et_Hometown.setError(null);
        et_Phoneno.setError(null);
        etEmail.setError(null);
        etDOB.setError(null);

        if (etFName.getText().toString().length() < 1) {
            etFName.setError("First Name cannot be empty");
        } else if (etLName.getText().toString().length() < 1) {
            etLName.setError("Last Name cannot be empty");
        } else if (etUsername.getText().toString().length() < 1) {
            etUsername.setError("Username cannot be empty");
        } else if (etPassword.getText().toString().length() < 1) {
            etPassword.setError("Password cannot be empty");
        } else if (etConfirmPassword.getText().toString().length() < 1) {
            etConfirmPassword.setError("Confirm Password cannot be empty");
        } else if (et_Hometown.getText().toString().length() < 1) {
            et_Hometown.setError("Hometown cannot be empty");
        } else if (et_Phoneno.getText().toString().length() < 1) {
            et_Phoneno.setError("Phone No cannot be empty");
        } else if (etEmail.getText().toString().length() < 1) {
            etEmail.setError("Email cannot be empty");
        } else if (skillAll.length() < 1) {
            Toast.makeText(this, "Skills cannot be empty", Toast.LENGTH_LONG).show();
        } else if (!etPassword.getText().toString().matches(etConfirmPassword.getText().toString())) {
            etConfirmPassword.setError("Password did not match");
            etConfirmPassword.setText("");
        }else if (etDOB.getText().toString().length() < 1) {
            Toast.makeText(this, "Date of Birth cannot be empty", Toast.LENGTH_LONG).show();
        } else {
            fname = etFName.getText().toString();
            lname = etLName.getText().toString();
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();
            confirmPassword = etConfirmPassword.getText().toString();
            hometown = et_Hometown.getText().toString();
            telno = Integer.parseInt(et_Phoneno.getText().toString());
            email = etEmail.getText().toString();
            DecimalFormat df = new DecimalFormat("00");
            dob = Integer.parseInt(BirthYear+df.format(BirthMonth+1)+df.format(BirthDay));

            final String URL = "https://hypognathous-pea.000webhostapp.com/eruby/external_user/registerNew.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            etFName.setText("");
                            etLName.setText("");
                            etUsername.setText("");
                            etPassword.setText("");
                            etConfirmPassword.setText("");
                            et_Hometown.setText("");
                            et_Phoneno.setText("");
                            etEmail.setText("");
                            tvSkill.setText("");
                            skillAll="";
                            etDOB.setText("");
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("Registration Success!").setNegativeButton("OK", null).create().show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("Registration Failed!").setNegativeButton("Retry", null).create().show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("fname", fname);
                    hashMap.put("lname", lname);
                    hashMap.put("username", username);
                    hashMap.put("password", password);
                    hashMap.put("hometown", hometown);
                    hashMap.put("telno", telno+"");
                    hashMap.put("email", email);
                    hashMap.put("dob", dob+"");
                    hashMap.put("skills", skillAll);
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
        // final Calendar c = Calendar.getInstance();
        // int mYear = c.get(Calendar.YEAR); // current year
        // int mMonth = c.get(Calendar.MONTH); // current month
        // int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        DatePickerDialog dpd = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                BirthYear = defaultBirthYear = year;
                BirthMonth = defaultBirthMonth = monthOfYear;
                BirthDay = defaultBirthDay = dayOfMonth;

                DecimalFormat df = new DecimalFormat("00");
                final EditText etDOB = (EditText) findViewById(R.id.etDOB);
                etDOB.setText(year+"-"+df.format(monthOfYear+1)+"-"+df.format(dayOfMonth));
            }
        },defaultBirthYear,defaultBirthMonth,defaultBirthDay);
        dpd.show();
    }
}