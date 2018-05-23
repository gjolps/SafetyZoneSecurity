package com.example.byte32.safetyzonesecurity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseCRUD extends SQLiteOpenHelper {

    private static final String DB_NAME = "visitor.db";
    private static final String DB_TABLE = "VisitorDetails";
    private static final String DETAILS_SR_NO = "Sr_No";
    private static final String DETAILS_FNAME = "First_Name";
    private static final String DETAILS_LNAME = "Last_Name";
    private static final String DETAILS_PHONENUMBER = "Phone_Number";
    private static final String DETAILS_CITY = "City";
    private static final String DETAILS_AREA = "Area";
    private static final String DETAILS_PIN = "Pin";
    private static final String DETAILS_OWNER = "Flat_Owner";
    private static final String DETAILS_FLATNUMBER = "Flat_Number";
    private static final String DETAILS_PURPOSE = "Purpose";
    private static final String DETAILS_VEH_NUMBER = "Vehicle_Number";
    private static final String DETAILS_IMAGE_NAME = "Image_Name";

    DatabaseCRUD(Context context){
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE "+DB_TABLE+" ("+ DETAILS_SR_NO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DETAILS_FNAME+" TEXT, "+DETAILS_LNAME+" TEXT, "+DETAILS_PHONENUMBER+" TEXT, "+DETAILS_AREA+" TEXT, " +
                DETAILS_CITY+" TEXT, "+DETAILS_PIN+" TEXT, "+DETAILS_OWNER+" TEXT, "+ DETAILS_FLATNUMBER+" TEXT, "+
                DETAILS_PURPOSE+" TEXT, " +DETAILS_VEH_NUMBER+" TEXT, "+DETAILS_IMAGE_NAME+" TEXT) ";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS "+DB_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insert(String fname,String lname,String phoneNumber, String city,String area,String pin,String owner,String fno, String purpose,String vehNo,String imageName){
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues.put(DETAILS_FNAME,fname);
        contentValues.put(DETAILS_LNAME,lname);
        contentValues.put(DETAILS_PHONENUMBER,phoneNumber);
        contentValues.put(DETAILS_AREA,area);
        contentValues.put(DETAILS_CITY,city);
        contentValues.put(DETAILS_PIN,pin);
        contentValues.put(DETAILS_OWNER,owner);
        contentValues.put(DETAILS_FLATNUMBER,fno);
        contentValues.put(DETAILS_PURPOSE,purpose);
        contentValues.put(DETAILS_VEH_NUMBER,vehNo);
        contentValues.put(DETAILS_IMAGE_NAME,imageName);
        long result = db.insert(DB_TABLE,null,contentValues);
        if(result == -1){
            return (false);
        }
        else {
            return (true);
        }
    }

    public Cursor getDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("Select "+DETAILS_FNAME +","+DETAILS_LNAME+" from "+DB_TABLE,null);
        return data;
    }
}
