package com.example.studentportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BasicStuInfoActivity extends AppCompatActivity {

    EditText edSurname, edName, edFathername, edMobileNo, edAlternatemn;
    TextView btn_insert;

    RadioGroup radioGroup;
    RadioButton radioButton;

    String surname,name,fathername,mobilenumber,alternatemobilenumber,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_stu_info);
        edSurname = findViewById(R.id.surname);
        edName = findViewById(R.id.name);
        edFathername = findViewById(R.id.fname);
        edMobileNo = findViewById(R.id.mobnumber);
        edAlternatemn = findViewById(R.id.altnumber);
        radioGroup = findViewById(R.id.rdgmedium);



        btn_insert = findViewById(R.id.btnbasicSave);

        checkData();

        WaitforResponse();





    }

    private void checkData() {
        String surname,name,fathername,mobilenumber,alternatemobilenumber,gender;

        SharedPreferences sp = getSharedPreferences("FILE_NAME",MODE_PRIVATE);

        surname = sp.getString("surname", String.valueOf(-1));
        name = sp.getString("name", String.valueOf(-1));
        fathername = sp.getString("fathername", String.valueOf(-1));
        mobilenumber = sp.getString("mobilenumber", String.valueOf(-1));
        alternatemobilenumber = sp.getString("alternatemobilenumber", String.valueOf(-1));
        gender = sp.getString("gender", String.valueOf(-1));
        if(surname==String.valueOf(-1) ||
                name==String.valueOf(-1)|| fathername==String.valueOf(-1)||
                mobilenumber==String.valueOf(-1) ||
                gender==String.valueOf(-1))
        {
            WaitforResponse();
        }
        else {
            startActivity(new Intent(this,StuEduDetailActivity.class));
            finish();
        }
    }

    private void WaitforResponse() {

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

    }

    private void getData() {
        surname = edSurname.getText().toString();
        name = edName.getText().toString();
        fathername = edFathername.getText().toString();
        mobilenumber = edMobileNo.getText().toString();
        alternatemobilenumber = edAlternatemn.getText().toString();



        if (surname.isEmpty() ||
                name.isEmpty() ||
                fathername.isEmpty()
                || mobilenumber.isEmpty()
        ){

            Toast.makeText(this, "fill up every detail", Toast.LENGTH_SHORT).show();

        }
        else {
            int ID = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(ID);
            gender = radioButton.getText().toString();
            setData();
        }
    }

    private void setData() {

        SharedPreferences sp = getSharedPreferences("FILE_NAME", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("surname", surname);
        editor.putString("name", name);
        editor.putString("fathername", fathername);
        editor.putString("mobilenumber",mobilenumber );
        editor.putString("alternatemobilenumber", alternatemobilenumber);
        editor.putString("gender",gender);
        editor.apply();

        startActivity(new Intent(this, StuEduDetailActivity.class));
        finish();
    }
}