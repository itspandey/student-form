package com.example.studentportal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddressDetailStuActivity extends AppCompatActivity {
    EditText edrAddress,edrVillage,edrCity,edstResAdd,edstResVillage,edstResCity;
    TextView btn_new;
    String rAddress,rVillage,rCity,stResAdd,stResVillage,stResCity,surname,name,
            fathername,mobilenumber,alternatemobilenumber,gender,schoolname,
            medium,group,mathmark,sciencemark,totalmark,url;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail_stu);

        edrAddress= findViewById(R.id.resAddress);
        edrVillage= findViewById(R.id.resVillage);
        edrCity= findViewById(R.id.resCity);
        edstResAdd= findViewById(R.id.schoolAdd);
        edstResVillage= findViewById(R.id.schoolVillage);
        edstResCity = findViewById(R.id.schoolcity);
        btn_new= findViewById(R.id.btnAddressSave);

        progressDialog = new ProgressDialog(this);

        checkdata();

    }

    private void checkdata() {

        SharedPreferences sp = getSharedPreferences("FILE_NAME",MODE_PRIVATE);

        rAddress = sp.getString("rAddress",String.valueOf(-1));
        rVillage = sp.getString("rVillage",String.valueOf(-1));
        rCity = sp.getString("rCity",String.valueOf(-1));
        stResAdd = sp.getString("stResAdd",String.valueOf(-1));
        stResVillage = sp.getString("stResVillage",String.valueOf(-1));
        stResCity = sp.getString("stResCity",String.valueOf(-1));

        if(rAddress == String.valueOf(-1) ||
                rVillage  == String.valueOf(-1) ||
                rCity == String.valueOf(-1) ||
                stResAdd == String.valueOf(-1) ||
                stResVillage == String.valueOf(-1) ||
                stResCity == String.valueOf(-1)
        )
        {
            waitforreponse();
        }
        else
        {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void getOldData() {

        SharedPreferences sp = getSharedPreferences("FILE_NAME",MODE_PRIVATE);

        surname = sp.getString("surname", String.valueOf(-1));
        name = sp.getString("name", String.valueOf(-1));
        fathername = sp.getString("fathername", String.valueOf(-1));
        mobilenumber = sp.getString("mobilenumber", String.valueOf(-1));
        alternatemobilenumber = sp.getString("alternatemobilenumber", String.valueOf(-1));
        gender = sp.getString("gender", String.valueOf(-1));

        schoolname = sp.getString("schoolname", String.valueOf(-1));
        mathmark = sp.getString("mathmark", String.valueOf(-1));
        sciencemark = sp.getString("sciencemark", String.valueOf(-1));
        totalmark = sp.getString("totalmark", String.valueOf(-1));
        medium = sp.getString("medium", String.valueOf(-1));
        group= sp.getString("group", String.valueOf(-1));

        dataupload();

    }

    private void upload() {

        if (surname==String.valueOf(-1) ||
                name==String.valueOf(-1)|| fathername==String.valueOf(-1)||
                mobilenumber==String.valueOf(-1) ||
                gender==String.valueOf(-1) ||
                schoolname == String.valueOf(-1) ||
                mathmark == String.valueOf(-1) || sciencemark == String.valueOf(-1) ||
                medium == String.valueOf(-1) ||
                group == String.valueOf(-1) ){

            Toast.makeText(this, "Enter your details", Toast.LENGTH_SHORT).show();

        }
        else {


//           Toast.makeText(this, "data is ready to inserted", Toast.LENGTH_SHORT).show();
            getOldData();
        }


    }

    private void waitforreponse() {

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getdata();

            }
        });
    }

    private void getdata(){

        rAddress = edrAddress.getText().toString();
        rVillage = edrVillage.getText().toString();
        rCity = edrCity.getText().toString();
        stResAdd = edstResAdd.getText().toString();
        stResVillage = edstResVillage.getText().toString();
        stResCity = edstResAdd.getText().toString();

        if (rAddress.isEmpty() || rVillage.isEmpty() || rCity.isEmpty() ||
                stResAdd.isEmpty() || stResVillage.isEmpty() || stResCity.isEmpty())
        {
            Toast.makeText(this, "Enter values", Toast.LENGTH_SHORT).show();
        }
        else {
            setdata();



        }
    }

    private void setdata() {
        SharedPreferences sp = getSharedPreferences("FILE_NAME",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();


        editor.putString("rAddress",rAddress);
        editor.putString("rVillage",rVillage);
        editor.putString("rCity",rCity);
        editor.putString("stResAdd",stResAdd);
        editor.putString("stResVillage",stResVillage);
        editor.putString("stResCity",stResCity);

        editor.apply();
        upload();

//        startActivity(new Intent( this,MainActivity.class));
//        finish();


    }

    private void dataupload() {

        progressDialog.setMessage("please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demovisitor.000webhostapp.com/demo.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        Toast.makeText(AddressDetailStuActivity.this, response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(AddressDetailStuActivity.this, "something went wrong" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("surname", surname);
                params.put("name",name);
                params.put("fathername",fathername);
                params.put("mobilenumber",mobilenumber);
                params.put("alternatemobilenumber",alternatemobilenumber);
                params.put("gender",gender);
                params.put("schoolname",schoolname);
                params.put("medium",medium);
                params.put("groupx",group);
                params.put("mathmark",mathmark);
                params.put("sciencemark",sciencemark);
                params.put("totalmark",totalmark);
                params.put("raddress",rAddress);
                params.put("rvillage",rVillage);
                params.put("rcity",rCity);
                params.put("stresadd",stResAdd);
                params.put("stresvillage",stResVillage);
                params.put("strescity",stResCity);



                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    
    // hvfhdv fdhc dsh
    // new changes


}
