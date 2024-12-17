package com.example.onemerge;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {

    EditText name_input,mobileno_input;
    Button update_button;
    String id,name,contactno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            name_input=findViewById(R.id.name_input2);
            mobileno_input=findViewById(R.id.mobileno_input2);
            update_button=findViewById(R.id.update_button);

            getAndSetIntentData();
            update_button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    MyDatabaseHelper myDB=new MyDatabaseHelper(UpdateActivity.this);
                    name = name_input.getText().toString().trim();
                    contactno = mobileno_input.getText().toString().trim();
                    myDB.updateData(id,name,contactno);

                }
            });
            return insets;
        });
    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("contactno")){
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            contactno =getIntent().getStringExtra("contactno");
            name_input.setText(name);
            mobileno_input.setText(contactno);
        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
}