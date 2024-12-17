package com.example.onemerge;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onemerge.databinding.ActivityMainBinding;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int CALL_PERMISSION_CODE = 101;
    private static final int LOCATION_PERMISSION_CODE = 102;
    private static final int SMS_PERMISSION_CODE = 103;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private android.widget.Toast Toast;
    private Switch switchInternet, switchCallPhone, switchFineLocation, switchCoarseLocation, switchSendSms;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    Button sosbutton;
    MyDatabaseHelper myDB;
    ArrayList<String> contact_id,contact_name,contact_no;
    CustomAdapter customAdapter;
    TextView mobileno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            String dataReceived = bundle.getString("key");  // Retrieve the string value
//
//            String[] contactNoArray = dataReceived.replace("[", "").replace("]", "").split(", ");
//            ArrayList<String> contactNoList = new ArrayList<>(Arrays.asList(contactNoArray));
//            android.widget.Toast.makeText(this, contactNoList.get(0), Toast.LENGTH_SHORT).show();
//
//        }
//        else {
//            android.widget.Toast.makeText(this, "Data Not Receive", Toast.LENGTH_SHORT).show();
//        }

        switchInternet = findViewById(R.id.switch_internet);
        switchCallPhone = findViewById(R.id.switch_call_phone);
        switchFineLocation = findViewById(R.id.switch_fine_location);
        switchCoarseLocation = findViewById(R.id.switch_coarse_location);
        switchSendSms = findViewById(R.id.switch_send_sms);

        setupSwitch(switchInternet, Manifest.permission.INTERNET);
        setupSwitch(switchCallPhone, Manifest.permission.CALL_PHONE);
        setupSwitch(switchFineLocation, Manifest.permission.ACCESS_FINE_LOCATION);
        setupSwitch(switchCoarseLocation, Manifest.permission.ACCESS_COARSE_LOCATION);
        setupSwitch(switchSendSms, Manifest.permission.SEND_SMS);

        recyclerView=findViewById(R.id.recyclerview);
        progressBar =findViewById(R.id.progress_bar);

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar
//                        .make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .setAnchorView(R.id.fab).show();
                Intent intent =new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

//        sosbutton =findViewById(R.id.b1);

//        sosbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                animateProgressBar(progressBar, 0, 100, 5000);
////                executeWithDelay(() -> {
//                    // Task to execute after delay
//                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        // Request permission
//                        ActivityCompat.requestPermissions(MainActivity.this,
//                                new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_CODE);
//                    } else {
//                        // Permission already granted
//                        makePhoneCall();
//                    }
////                }, 5000);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


//        int item_id=item.getItemId();
        MenuItem menuItem =menu.findItem(R.id.action_settings);
        MenuItem menuItem1=menu.findItem(R.id.action_hospital);
        MenuItem menuItem2=menu.findItem(R.id.action_police);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                FirebaseAuth.getInstance().signOut(); // Example for Firebase logout

                // Redirect to login screen
                Intent intent = new Intent(getApplicationContext(),Entermobilenumberone.class); // Replace with your login activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                // Show a message
                Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        menuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                String mapUrl = "https://www.google.co.in/maps/search/hospitals+near/@24.5697823,81.2451284,12z/data=!3m1!4b1?entry=ttu&g_ep=EgoyMDI0MTIxMS4wIKXMDSoASAFQAw%3D%3D";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                intent.setPackage("com.google.android.apps.maps");

                // Check if Google Maps is installed
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                }
                return true;
            }
        });
        menuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                String mapUrl = "https://www.google.co.in/maps/search/police+station+near/@24.5697375,81.2451282,12z/data=!3m1!4b1?entry=ttu&g_ep=EgoyMDI0MTIxMS4wIKXMDSoASAFQAw%3D%3D";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                intent.setPackage("com.google.android.apps.maps");

                // Check if Google Maps is installed
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Call Permission Granted", Toast.LENGTH_SHORT).show();
                makePhoneCall();
            } else {
                Toast.makeText(this, "Call Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendLocationMessage();
            } else {
                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == SMS_PERMISSION_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendLocationMessage();
                } else {
                    Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void Emergency(View view){
    }

    private void makePhoneCall() {
        String phoneNumber = "tel:123456789"; // Replace with the desired phone number
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phoneNumber));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "Permission not granted to make calls", Toast.LENGTH_SHORT).show();
        }
    }
    public void Location(View view){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Check and request permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        } else {
            sendLocationMessage();
        }
    }

    private void sendLocationMessage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        mobileno =findViewById(R.id.contact_mobileno_txt);
                        if (location != null) {
                            Bundle bundle = getIntent().getExtras();
                            if (bundle != null) {
                                String dataReceived = bundle.getString("key");  // Retrieve the string value

                                String[] contactNoArray = dataReceived.replace("[", "").replace("]", "").split(", ");
                                ArrayList<String> contactNoList = new ArrayList<>(Arrays.asList(contactNoArray));
//                                android.widget.Toast.makeText(this, contactNoList.get(0), Toast.LENGTH_SHORT).show();
                                double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            String message = "My current location: https://maps.google.com/?q=" + latitude + "," + longitude;

                            String phoneNumber = contactNoList.get(0); // Replace with the recipient's number
                            sendSMS(phoneNumber, message);}
                        } else {
                            Toast.makeText(MainActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Location sent via SMS", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setupSwitch(Switch switchControl, String permission) {
//        switchControl.setChecked(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED);
//
//        switchControl.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
//            if (isChecked) {
//                if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, 1);
//                }
//            } else {
//                Toast.makeText(this, "Cannot revoke permissions programmatically.", Toast.LENGTH_SHORT).show();
//                switchControl.setChecked(true); // Permissions can't be revoked programmatically
//            }
//        });
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