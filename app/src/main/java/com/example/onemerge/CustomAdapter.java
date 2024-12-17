package com.example.onemerge;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList contact_id,contact_name,contact_no;
    Activity activity;

    public CustomAdapter(Context context, ArrayList contact_id, ArrayList contact_name, ArrayList contact_no){
        this.context=context;
        this.contact_id=contact_id;
        this.contact_name=contact_name;
        this.contact_no=contact_no;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.contact_id_txt.setText(String.valueOf(contact_id.get(position)));
        holder.contact_name_txt.setText(String.valueOf(contact_name.get(position)));
        holder.contact_mobileno_txt.setText(String.valueOf(contact_no.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("id",String.valueOf(contact_id.get(position)));
                intent.putExtra("name",String.valueOf(contact_name.get(position)));
                intent.putExtra("contactno",String.valueOf(contact_no.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contact_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView contact_id_txt,contact_name_txt,contact_mobileno_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contact_id_txt=itemView.findViewById(R.id.contact_id_txt);
            contact_name_txt=itemView.findViewById(R.id.contact_name_txt);
            contact_mobileno_txt=itemView.findViewById(R.id.contact_mobileno_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
