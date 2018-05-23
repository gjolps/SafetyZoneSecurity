package com.example.byte32.safetyzonesecurity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CallOwner extends AppCompatActivity {

    Button btn_call,homeButton;

    EditText dial_num1;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_owner);

        btn_call = (Button)findViewById(R.id.call_btn);
        homeButton = (Button)findViewById(R.id.home);
        dial_num1 = (EditText)findViewById(R.id.num_dial);

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    String num = dial_num1.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+num));
                    flag = 1;
                    startActivity(intent);


                }catch (Exception e){
                    if(ActivityCompat.checkSelfPermission(CallOwner.this, Manifest.permission.CALL_PHONE)!=
                            PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(CallOwner.this,new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                }

            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1){
                    Intent intent = new Intent(CallOwner.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(CallOwner.this,"Call Flat Owner",Toast.LENGTH_LONG).show();
            }
        });
    }

}
