package com.example.bottomnavigationactivityapp;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

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

    public void testLoader(){
        Loader<String> loader = new Loader<>(this);
        LoaderManager.LoaderCallbacks callbacks = new LoaderManager.LoaderCallbacks() {
            @NonNull
            @Override
            public Loader onCreateLoader(int id, @Nullable Bundle args) {
                return null;
            }

            @Override
            public void onLoadFinished(@NonNull Loader loader, Object data) {

            }

            @Override
            public void onLoaderReset(@NonNull Loader loader) {

            }
        };
        LoaderManager loaderManager = LoaderManager.getInstance(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_2);
        ComponentName component = getIntent().getComponent();
        Log.i(TAG, "onCreate: "+component.getPackageName()+"\t"+component.getClassName()+"\t"+component.getShortClassName());
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