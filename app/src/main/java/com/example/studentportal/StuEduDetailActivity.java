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

public class StuEduDetailActivity extends AppCompatActivity {
    EditText edschoolname,edmathmark,edsciencemark;
    TextView button,edtotalmarks;
    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    RadioButton radioButton;
    String schoolname,medium,group,mathmark,sciencemark,totalmark;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_edu_detail);
        edschoolname = findViewById(R.id.schoolName);
        radioGroup1 = findViewById(R.id.b1);
        radioGroup2 = findViewById(R.id.b2);
        edmathmark = findViewById(R.id.mathMark);
        edsciencemark = findViewById(R.id.scienceMark);
        edtotalmarks = findViewById(R.id.totalMark);

        button = findViewById(R.id.btnEduSave);

        edtotalmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mathmark = edmathmark.getText().toString();
                sciencemark = edsciencemark.getText().toString();

                totalmark= String.valueOf(Integer.parseInt(mathmark)+Integer.parseInt(sciencemark));

               edtotalmarks.setText(totalmark);

            }
        });

        Checkinfo();

    }

    private void Checkinfo() {
        String schoolname,medium,group,mathmark,sciencemark,totalmark;

        SharedPreferences sp = getSharedPreferences("FILE_NAME",MODE_PRIVATE);

        schoolname = sp.getString("schoolname", String.valueOf(-1));
        mathmark = sp.getString("mathmark", String.valueOf(-1));
        sciencemark = sp.getString("sciencemark", String.valueOf(-1));
        totalmark = sp.getString("totalmark", String.valueOf(-1));
        medium = sp.getString("medium", String.valueOf(-1));
        group= sp.getString("group", String.valueOf(-1));

        if(schoolname == String.valueOf(-1) ||
                mathmark == String.valueOf(-1) || sciencemark == String.valueOf(-1) ||
                medium == String.valueOf(-1) ||
                group == String.valueOf(-1)
        )
        {
            waitforresponse();
        }
        else {
            startActivity(new Intent(this,AddressDetailStuActivity.class));
            finish();
        }
    }

    private void waitforresponse() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

    }

    private void getData() {
        schoolname = edschoolname.getText().toString();
        mathmark = edmathmark.getText().toString();
        sciencemark = edsciencemark.getText().toString();
        schoolname = edschoolname.getText().toString();


        totalmark= String.valueOf(Integer.parseInt(mathmark)+Integer.parseInt(sciencemark));

        if (schoolname.isEmpty() || mathmark.isEmpty() || sciencemark.isEmpty())
        {
            Toast.makeText(this,"fill up everydetail",Toast.LENGTH_SHORT).show();
        }
        else
        {
            int ID1 = radioGroup1.getCheckedRadioButtonId();
            radioButton = findViewById(ID1);
            medium = radioButton.getText().toString();
            int ID2 = radioGroup2.getCheckedRadioButtonId();
            radioButton = findViewById(ID2);
            group = radioButton.getText().toString();
            setData();
        }
    }

    private void setData() {
        SharedPreferences sp = getSharedPreferences("FILE_NAME",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("schoolname", schoolname);
        editor.putString("medium", medium);
        editor.putString("group", group);
        editor.putString("mathmark", mathmark);
        editor.putString("sciencemark", sciencemark);
        editor.putString("totalmark",totalmark);
        editor.apply();


        startActivity(new Intent(this, AddressDetailStuActivity.class));
        finish();
    }
}