package com.example.bottomnavigationactivityapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnavigationactivityapp.contact.ContactMainActivity;

public class Activity3 extends BaseActivity {

    private TextView mTextView;
    public String TAG = "TAGActivityy3：";
    
    public Activity3(){
        super.TAG = this.TAG ;
        Log.i(TAG, "Activity3: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        Log.i(TAG, "onCreate: ");
        mTextView = (TextView) findViewById(R.id.text);
    }
    public void onClick1(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void onClick2(View view){
        startActivity(new Intent(this, Activity3.class));
    }

    public void onClick3(View view){
        startActivity(new Intent(this, ContactMainActivity.class));
    }
}