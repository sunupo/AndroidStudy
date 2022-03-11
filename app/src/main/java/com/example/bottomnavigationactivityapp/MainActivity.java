package com.example.bottomnavigationactivityapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bottomnavigationactivityapp.Receiver.ButtonBroadcastReceiver;
import com.example.bottomnavigationactivityapp.contact.ContactMainActivity;
import com.example.bottomnavigationactivityapp.contact.ContactMainActivity2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.sql.Connection;
import java.util.HashMap;

public class MainActivity extends BaseActivity {
    private final String TAG = "TAGMainActivityy：";
    private  Button button;

    private IntentFilter intentFilter;
    CustomReceiver customReceiver;

    ButtonBroadcastReceiver buttonBroadcastReceiver;
    LocalBroadcastManager localBroadcastManager;
    private final Handler handler;

    private AppCompatEditText editText;


    /**
     * Handler处理消息
     */
    static class MyHandler extends Handler{
        private Context context;
        public MyHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    try {
                        @SuppressWarnings("unchecked")
                        HashMap<String, String> map = (HashMap<String, String>) msg.obj;
                        Toast.makeText(context, "收到messsage" + map.getOrDefault("name","default value"), Toast.LENGTH_LONG).show();
                    }
                    catch (ClassCastException e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public boolean sendMessageAtTime(@NonNull Message msg, long uptimeMillis) {
            return super.sendMessageAtTime(msg, uptimeMillis);
        }
    }

    public MainActivity(){
        super();
        super.TAG = this.TAG;
        handler = new MyHandler(this);
        Log.i(TAG, "MainActivity: ");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: ");


        button = findViewById(R.id.button1);
        editText = (AppCompatEditText)findViewById(R.id.msg_text);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG, "onFocusChange: "+hasFocus);
            }
        });

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

        /**
         * 全局广播
         */
        intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        customReceiver = new CustomReceiver();
        registerReceiver(customReceiver, intentFilter);

        /**
         * 本地广播
         */
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(getComponentName().getShortClassName());
        intentFilter1.setPriority(100);
        buttonBroadcastReceiver = new ButtonBroadcastReceiver();
        localBroadcastManager.registerReceiver(buttonBroadcastReceiver,intentFilter1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(customReceiver);
        localBroadcastManager.unregisterReceiver(buttonBroadcastReceiver);
    }

    class CustomReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info instanceof NetworkInfo && info.isAvailable()){
                Toast.makeText(context, "网络可用",Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(context, "网络不可用", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        findViewById(R.id.button3).callOnClick();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Log.i(TAG, "onResume: "+editText+button);

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
        Intent intent = new Intent(this, ContactMainActivity2.class);
        startActivity(intent);
    }

    public void onClick4(View view){

        Intent intent = new Intent();
        intent.setAction(getComponentName().getShortClassName());
        localBroadcastManager.sendBroadcast(intent);
    }

    public void onClick5(View view){
       new Thread(()->{
           Message message = new Message();
           message.what = 0;
           message.arg1 = 1;
           message.arg2=2;
           HashMap<String, String> map = new HashMap<>();
           message.obj = map;
           if (editText instanceof EditText) {
               map.put("name", editText.getText().toString());
           }
           handler.sendMessageDelayed(message, 2000);
       }).start();

    }

}