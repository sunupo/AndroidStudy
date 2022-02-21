package com.example.bottomnavigationactivityapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.example.bottomnavigationactivityapp.utils.ActivityCollector;

public class BaseActivity extends AppCompatActivity {

    public String TAG = "TAGBaseActivityy";

    public BaseActivity(){
        super();
        Log.i(TAG+"\t"+this.toString().split("@")[1], "BaseActivity: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.add(this);
        Log.i("taskId="+getTaskId()+"\t"+TAG+"\t"+this.toString().split("@")[1], "onCreate: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putInt("a",1);
        Log.i("taskId="+getTaskId()+"\t"+TAG+"\t"+this.toString().split("@")[1], "onSaveInstanceState");

        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }


    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Log.i("taskId="+getTaskId()+"\t"+TAG+"\t"+this.toString().split("@")[1], "onRestoreInstanceState"+savedInstanceState.getInt("a", 2));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("taskId="+getTaskId()+"\t"+TAG+"\t"+this.toString().split("@")[1],"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("taskId="+getTaskId()+"\t"+TAG+"\t"+this.toString().split("@")[1], "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("taskId="+getTaskId()+"\t"+TAG+"\t"+this.toString().split("@")[1],"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("taskId="+getTaskId()+"\t"+TAG+"\t"+this.toString().split("@")[1],"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("taskId="+getTaskId()+"\t"+TAG+"\t"+this.toString().split("@")[1], "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.remove(this);
        Log.i("taskId="+getTaskId()+"\t"+TAG+"\t"+this.toString().split("@")[1], "onDestroy: ");
    }
}