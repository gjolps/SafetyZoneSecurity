package com.example.byte32.safetyzonesecurity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddVisitor extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
    EditText PhoneNumber;
    EditText AddressCity;
    EditText HomeAddressArea;
    EditText homeAddressPinCode;
    EditText personToVisit;
    EditText FlatNumber;
    EditText purpose;
    EditText vehNum;
    final int CAMERA_REQUEST_IMAGE = 100;
    String imagePath;
    String imgName="";

    int flag;
    String message;

    final String myTag = "DocsUpload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visitor);

        Log.i(myTag, "OnCreate()");

        final DatabaseCRUD databaseCRUD = new DatabaseCRUD(this);
        FirstName = (EditText) findViewById(R.id.firstName);
        LastName = (EditText) findViewById(R.id.lastName);
        PhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        AddressCity = (EditText) findViewById(R.id.homeAddressCity);
        HomeAddressArea = (EditText) findViewById(R.id.homeAddressArea);
        homeAddressPinCode = (EditText) findViewById(R.id.PinCOde);
        personToVisit = (EditText) findViewById(R.id.person_to_visit);
        FlatNumber = (EditText) findViewById(R.id.FlatNumber);
        purpose = (EditText) findViewById(R.id.PurposeOfVisit);
        vehNum = (EditText) findViewById(R.id.VehicleNumber);



        Button captureImage = (Button)findViewById(R.id.captureImage);
        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePhoto();
            }
        });

        // Get the Intent that started this activity and extract the string

        // Capture the layout's TextView and set the string as its text
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                boolean addData = databaseCRUD.insert(FirstName.getText().toString(),LastName.getText().toString(),
                        PhoneNumber.getText().toString(),AddressCity.getText().toString(),HomeAddressArea.getText().toString(),
                        homeAddressPinCode.getText().toString(),personToVisit.getText().toString(),FlatNumber.getText().toString(),
                        purpose.getText().toString(), vehNum.getText().toString(),imgName);
                if(!addData){
                    Toast.makeText(AddVisitor.this,"Error, SQLite DB!!",Toast.LENGTH_SHORT).show();
                }


                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        postData();

                    }
                });
                t.start();

                Intent intent = new Intent(AddVisitor.this, CallOwner.class);
                Toast welcomeToast = Toast.makeText(AddVisitor.this, "Uploaded to DB!", Toast.LENGTH_LONG);
                welcomeToast.setGravity(Gravity.CENTER, 0, 0);                                        //make a Toast object, then show
                welcomeToast.show();
                startActivity(intent);

            }

        });

        Button vehicNum = (Button) findViewById(R.id.getVehNumber);
        vehicNum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AddVisitor.this, GetVehicleNumber.class);
                startActivity(intent);

            }

        });
        try {
            Intent intent = this.getIntent();
            message = intent.getExtras().getString("veh");
            vehNum.setText(message);
        }catch (Exception e){

        }
    }




    public void postData() {

        String fullUrl = "https://docs.google.com/forms/d/e/1FAIpQLScY4d4VCNkw4mGsN-wbpJ-Lr1yYBvcyo8c-EAiCcEWW3PHKMg/formResponse";
        HttpRequest mReq = new HttpRequest();
        String col1 = FirstName.getText().toString();
        String col2 = LastName.getText().toString();
        String col3 = PhoneNumber.getText().toString();
        String col4 = personToVisit.getText().toString();
        String col5 = FlatNumber.getText().toString();
        String col6 = HomeAddressArea.getText().toString();
        String col7 = AddressCity.getText().toString();
        String col8 = homeAddressPinCode.getText().toString();
        String col9 = purpose.getText().toString();
        String col10 = vehNum.getText().toString();
        String col11 = imgName;

        if(purpose.getText().toString()!="") {
            try {
                String data = "entry.369091130=" + URLEncoder.encode(col1, "UTF-8") + "&" +
                        "entry.1971916399=" + URLEncoder.encode(col2, "UTF-8") + "&" +
                        "entry.918913570=" + URLEncoder.encode(col3, "UTF-8") + "&" +
                        "entry.1023048760=" + URLEncoder.encode(col4, "UTF-8") + "&" +
                        "entry.1822589659=" + URLEncoder.encode(col5, "UTF-8") + "&" +
                        "entry.1413926774=" + URLEncoder.encode(col6, "UTF-8") + "&" +
                        "entry.1678241915=" + URLEncoder.encode(col7, "UTF-8") + "&" +
                        "entry.1925970222=" + URLEncoder.encode(col8, "UTF-8") + "&" +
                        "entry.201622387=" + URLEncoder.encode(col9, "UTF-8") + "&" +
                        "entry.327618494=" + URLEncoder.encode(col10, "UTF-8")+ "&" +
                        "entry.825654432=" + URLEncoder.encode(col11, "UTF-8");;


                String response = mReq.sendPost(fullUrl, data);
                Log.i(myTag, response);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

    }




    private void capturePhoto(){
        requestRuntimePermission();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File imgDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Test Dir");
            if (!imgDir.exists()) {
                imgDir.mkdirs();
            }
            imgName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
            File imgPath = new File(imgDir, imgName);
            imagePath = imgPath.toString();
            Uri imgUri = FileProvider.getUriForFile(AddVisitor.this, "com.example.nitish.nit", imgPath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            startActivityForResult(intent, CAMERA_REQUEST_IMAGE);
        } else {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
        }
    }

    public void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                Toast.makeText(getApplicationContext(), "Image Captured Successfully", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

}
