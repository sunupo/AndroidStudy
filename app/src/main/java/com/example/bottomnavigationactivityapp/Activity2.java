package com.example.bottomnavigationactivityapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottomnavigationactivityapp.ActivityTwoFragment.AnotherRightFragment;
import com.example.bottomnavigationactivityapp.ActivityTwoFragment.LeftFragment;
import com.example.bottomnavigationactivityapp.ActivityTwoFragment.RightFragment;


public class Activity2 extends BaseActivity {

    public String TAG = "TAGActivityy2：";
    private int fragmentIndex=0;

    public Activity2(){
        super.TAG = this.TAG;
        Log.i(TAG, "Activity2: ");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_2);
    }
    public void onClick1(View view){
        startActivity(new Intent(this, Activity3.class));

    }

    public void onClick2(View view){
        startActivity(new Intent(this, Activity2.class));

    }
    public void onClick3(View view){
        startActivity(new Intent(this, MainActivity.class));

    }
    public void onClick4(View view){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.right_fragment, getFragment());
        fragmentTransaction.addToBackStack("将rightFragment添加到返回栈");
        fragmentTransaction.commit();
    }

    public BaseFragment getFragment(){
        Log.i(TAG, "getFragment: "+fragmentIndex%3);
        switch (fragmentIndex%3){
            case 0:
                fragmentIndex += fragmentIndex%3 +1;
                return LeftFragment.newInstance("left", "fragment");
            case 1:
                fragmentIndex += fragmentIndex%3 +1;
                return RightFragment.newInstance("right","fragment");
            case 2:
                fragmentIndex += fragmentIndex%3 +1;
                return AnotherRightFragment.newInstance("anotherRight", "fragment");
            default:
                fragmentIndex += fragmentIndex%3 +1;
                return AnotherRightFragment.newInstance("AnotherRight", "fragment");
        }

    }
}