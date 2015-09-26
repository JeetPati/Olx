package com.example.hackathon1.myapplication;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_submit;
    private EditText descrition;
    private EditText price;
    private Spinner sp_loc;
    private Spinner sp_cat;

    private String[] category= { "Apple","Samsung" ,"HTC"};
    private String[] location_arr= { "New Delhi","Mumbai" ,"Kolkata"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_submit = (Button)findViewById(R.id.sub_btn);
        descrition = (EditText)findViewById(R.id.editText);
        price = (EditText)findViewById(R.id.editText2);
        sp_cat = (Spinner)findViewById(R.id.spinner);
        sp_loc = (Spinner)findViewById(R.id.spinner2);

        btn_submit.setOnClickListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, category);

        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        sp_cat.setAdapter(aa);

        ArrayAdapter loc = new ArrayAdapter(this,android.R.layout.simple_spinner_item, location_arr);

        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        sp_loc.setAdapter(loc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        ContentValues values = new ContentValues();

        if (descrition != null && !descrition.getText().toString().isEmpty()){

            values.put(MySQLiteHelper.COLUMN_DESRCPTION, descrition.getText().toString());

        }
        if (price != null && !price.getText().toString().isEmpty()){

            values.put(MySQLiteHelper.COLUMN_PRICE, price.getText().toString());

        }
        if (sp_loc != null && !sp_loc.getSelectedItem().toString().isEmpty()){

            values.put(MySQLiteHelper.COLUMN_LOC, sp_loc.getSelectedItem().toString());

        }
        if (sp_cat != null && !sp_cat.getSelectedItem().toString().isEmpty()){

            values.put(MySQLiteHelper.COLUMN_CATEG, sp_cat.getSelectedItem().toString());

        }

        ProcessDataSource pds = new ProcessDataSource(this);
        try {
            pds.open();
        }catch (SQLException e){
            e.getStackTrace();
        }

        pds.insertData(values);



    }
}
