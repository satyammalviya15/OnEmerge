package com.example.onemerge.ui.home;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onemerge.MainActivity;
import com.example.onemerge.R;
import com.example.onemerge.databinding.FragmentHomeBinding;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Button sosbutton;
    Activity HomeFragment;
    ProgressBar progressBar;
    private static final int CALL_PERMISSION_CODE = 101;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sosbutton =root.findViewById(R.id.b1);
        progressBar =root.findViewById(R.id.progress_bar);
        sosbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateProgressBar(progressBar, 0, 100, 5000);
                executeWithDelay(() -> {
                // Task to execute after delay
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Request permission
                    ActivityCompat.requestPermissions(HomeFragment,
                            new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_CODE);
                } else {
                    // Permission already granted
                    makePhoneCall();
                }
                }, 5000);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void Emergency(View view){
    }

    private void makePhoneCall() {
        String phoneNumber = "tel:123456789"; // Replace with the desired phone number
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phoneNumber));

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
        } else {
            Toast.makeText(getContext(), "Permission not granted to make calls", Toast.LENGTH_SHORT).show();
        }
    }

    private void animateProgressBar(ProgressBar progressBar, int start, int end, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", start, end);
        animator.setDuration(duration); // Duration in milliseconds (5000 ms = 5 seconds)
        animator.start();
    }

    private void executeWithDelay(Runnable task, int delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(task, delayMillis);
    }
}
