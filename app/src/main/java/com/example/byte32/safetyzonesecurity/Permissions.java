package com.example.byte32.safetyzonesecurity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Permissions extends AppCompatActivity {

    Button permission;
    int flag = 0;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        checkRunTimePermissions();

        permission = (Button) findViewById(R.id.permissions);

        permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkRunTimePermissions();
                goToMainActivity();
            }
        });
    }
    private void checkRunTimePermissions() {

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void goToMainActivity() {

        if(hasPermissions(this,PERMISSIONS)){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
        else {
            Toast.makeText(this,"Please Provide Us The Necessary Permissions",Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {

                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */

                @Override
                public void run() {
                    checkRunTimePermissions();
                }
            }, 1500);
        }
    }

}
