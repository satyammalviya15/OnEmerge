package com.example.onemerge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME="Contact.db";
    private static final int DATABASE_VERSION=3;

    private static final String TABLE_NAME="my_contacts";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_NAME="contact_name";
    private static final String COLUMN_NO="contact_no";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_NO +" TEXT); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    void addContact(String name ,String mobileno){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_NO,mobileno);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context, "Failed to INSERT", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added Sucessfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(){
        String query ="SELECT * FROM " +TABLE_NAME;
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor =null;
        if(db!=null){
            cursor =db.rawQuery(query,null);
        }
        return cursor;
    }
    void updateData(String row_id, String name,String mobileno){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_NO,mobileno);

        long result = db.update(TABLE_NAME,cv,"_id=?",new String[]{row_id});
        if(result==-1){
            Toast.makeText(context, "Failed Update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Sucessfully Updated", Toast.LENGTH_SHORT).show();
        }
    }
}