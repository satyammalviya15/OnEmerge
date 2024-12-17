package com.example.onemerge.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.onemerge.ContactList;
import com.example.onemerge.CustomAdapter;
import com.example.onemerge.MainActivity;
import com.example.onemerge.MyDatabaseHelper;
import com.example.onemerge.R;
import com.example.onemerge.databinding.FragmentGalleryBinding;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<String> contact_id,contact_name,contact_no;
    CustomAdapter customAdapter;
    MyDatabaseHelper myDB;
    SwipeRefreshLayout swipeRefreshLayout;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        myDB = new MyDatabaseHelper(getContext());
        contact_id =new ArrayList<>();
        contact_name = new ArrayList<>();
        contact_no =new ArrayList<>();

        StoreDataInArrays();
        recyclerView= root.findViewById(R.id.recyclerview);
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);
        customAdapter =new CustomAdapter(getContext(),contact_id,contact_name,contact_no);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout.setOnRefreshListener(this::refreshData);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void refreshData() {
        contact_id.clear();
        contact_name.clear();
        contact_no.clear();
        StoreDataInArrays();  // Reload data from the database
        customAdapter.notifyDataSetChanged();  // Update the adapter
        swipeRefreshLayout.setRefreshing(false);  // Hide the refresh indicator
        Toast.makeText(getContext(), "Data Refreshed", Toast.LENGTH_SHORT).show();


        Bundle bundle = new Bundle();
        bundle.putString("key", String.valueOf(contact_no));

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtras(bundle);  // Put the bundle with data into the Intent
        startActivity(intent);

    }

    void StoreDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount()==0){
            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                contact_id.add(cursor.getString(0));
                contact_name.add(cursor.getString(1));
                contact_no.add(cursor.getString(2));
            }
        }
    }
}