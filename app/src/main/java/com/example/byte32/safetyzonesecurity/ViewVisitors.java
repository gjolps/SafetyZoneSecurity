package com.example.byte32.safetyzonesecurity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewVisitors extends AppCompatActivity {

    DatabaseCRUD databaseCRUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visitors);

        ListView listView = (ListView)findViewById(R.id.listview);
        databaseCRUD = new DatabaseCRUD(ViewVisitors.this);

        ArrayList<String> list = new ArrayList<>();
        Cursor data = databaseCRUD.getDatabase();

        if(data.getCount()==0){
            Toast.makeText(this,"No Data Available",Toast.LENGTH_LONG).show();
        }
        else {
            while(data.moveToNext()){
                String name = data.getString(0) + " " + data.getString(1);
                list.add(name);
                ListAdapter listAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,list);
                listView.setAdapter(listAdapter);

            }
        }
    }

}
