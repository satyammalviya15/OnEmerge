package com.example.onemerge;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> contact_id,contact_name,contact_no;
    CustomAdapter customAdapter;
    MyDatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            myDB = new MyDatabaseHelper(ContactList.this);
            contact_id =new ArrayList<>();
            contact_name = new ArrayList<>();
            contact_no =new ArrayList<>();

            StoreDataInArrays();
            recyclerView=findViewById(R.id.recyclerview);
//            Log.d("DEBUG", "Contact ID: " + contact_id);
//            Log.d("DEBUG", "Contact Name: " + contact_name);
//            Log.d("DEBUG", "Contact No: " + contact_no);
            customAdapter =new CustomAdapter(ContactList.this,contact_id,contact_name,contact_no);
            recyclerView.setAdapter(customAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(ContactList.this));
            return insets;
        });
    }
    void StoreDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount()==0){
            android.widget.Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                contact_id.add(cursor.getString(0));
                contact_name.add(cursor.getString(1));
                contact_no.add(cursor.getString(2));
            }
        }
    }
}