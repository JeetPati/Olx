package com.example.hackathon1.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_submit,btn_image;
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
        btn_image = (Button)findViewById(R.id.button);
        descrition = (EditText)findViewById(R.id.editText);
        price = (EditText)findViewById(R.id.editText2);
        sp_cat = (Spinner)findViewById(R.id.spinner);
        sp_loc = (Spinner)findViewById(R.id.spinner2);

        btn_submit.setOnClickListener(this);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 // Start the Intent
                startActivityForResult(galleryIntent, 1);
            }
        });

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imageView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }
}
