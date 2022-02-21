package com.example.bottomnavigationactivityapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bottomnavigationactivityapp.contact.ContactMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity {
    String TAG = "TAGMainActivityy：";
    Button button;
    
    public MainActivity(){
        super();
        super.TAG = this.TAG;
        Log.i(TAG, "MainActivity: ");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i(TAG, "onCreate: ");


        button = findViewById(R.id.button1);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
        Toast.makeText(this, "ffdfdfd", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.button3).callOnClick();
    }

    public void onClick1(View view){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);

    }

    public void onClick2(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void onClick3(View view){
        Intent intent = new Intent(this, ContactMainActivity.class);
        startActivity(intent);
    }


    
}