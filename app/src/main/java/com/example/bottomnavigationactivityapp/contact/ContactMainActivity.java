package com.example.bottomnavigationactivityapp.contact;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.bottomnavigationactivityapp.BaseActivity;
import com.example.bottomnavigationactivityapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactMainActivity extends BaseActivity {

    private List<ContactViewModel> contactViewModelList = new ArrayList<>();
    ContactAdapter adapter;
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
//                    call();
                } else {
                    Toast.makeText(this, "没有获取到权限", Toast.LENGTH_LONG).show();
                }
            });
    
    public ContactMainActivity(){
        Log.i(TAG, "ContactMainActivity: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_main);
        initSystemContacts();
        adapter = new ContactAdapter(this, R.layout.contact_item, contactViewModelList);
        ListView listView = findViewById(R.id.contact_label_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String requestPermission=Manifest.permission.CALL_PHONE;

            if (ContextCompat.checkSelfPermission(
                    this, requestPermission) ==
                    PackageManager.PERMISSION_GRANTED) {
                call(position);
            } else if (shouldShowRequestPermissionRationale(requestPermission)) {
                Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_LONG).show();
            } else {
                requestPermissionLauncher.launch(
                        requestPermission);
            }

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                String[] requestPermissions={Manifest.permission.CALL_PHONE};
                requestPermissions(requestPermissions, 1);
            }else {
                call(position);
            }

        });
    }
    public void call(int position){
        try {
            ContactViewModel model = contactViewModelList.get(position);
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + model.getPhone()));
            Bundle bundle = new Bundle();
            bundle.putInt("age", 20);
            bundle.putInt("size", 100);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0){
            switch (requestCode){
                case 1:
                    if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                        call();  怎么获取position
                    }else{
                        Toast.makeText(this, "没有获取到权限", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    public void initSystemContacts(){
        new Thread(() -> {
            try{
                Log.i(TAG, "initSystemContacts: initSystemContacts1");
                String queryType[] = {Phone.DISPLAY_NAME,Phone.NUMBER, "sort_key", "phonebook_label",
                        Phone.PHOTO_ID};
                Log.i(TAG, "initSystemContacts: initSystemContacts4");

                ContentResolver resolver = getContentResolver(); //getApplicationContext().getContentResolver();
                Cursor cursor = resolver.query(Phone.CONTENT_URI, queryType, null, null, "sort_key COLLATE LOCALIZED ASC");
                Log.i(TAG, "initSystemContacts: initSystemContacts5");

                if(cursor==null || cursor.getCount()==0){
                    Log.i(TAG, "initSystemContacts: initSystemContacts3");

                    Toast.makeText(this,"获取系统通讯录失败", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.i(TAG, "initSystemContacts: initSystemContacts2");

                int nameIndex = cursor.getColumnIndex(queryType[0]);
                int phoneIndex = cursor.getColumnIndex(queryType[1]);
                int imageIndex = cursor.getColumnIndex(queryType[4]);
                int sortKeyIndex = cursor.getColumnIndex(queryType[2]);
                int labelIndex = cursor.getColumnIndex(queryType[3]);
                Log.i(TAG, "initSystemContacts: "+nameIndex+"\t"+phoneIndex+"\t"+imageIndex+"\t"+sortKeyIndex+"\t"+labelIndex+"\t"+cursor);
                final List<ContactViewModel> tmpList = new ArrayList<>();;
                if(cursor!=null){
                    Log.i(TAG, "initSystemContacts: cursor");
                    while(cursor.moveToNext()){
                        Log.i(TAG, "initSystemContacts: while");
                        String name = cursor.getString(nameIndex);
                        String phone = cursor.getString(phoneIndex);
                        int image = cursor.getInt(imageIndex);
                        String sortKey = cursor.getString(sortKeyIndex);
                        String label = cursor.getString(labelIndex);

                        ContactViewModel model = new ContactViewModel();
                        model.setImageId(image);
                        model.setName(name);
                        model.setPhone(phone);
                        model.setSortKey(sortKey);
                        if (label == null || label.equals("#") || label.equals("")) {
                            label = "#";
                        }
                        model.setLabel(label);
                        tmpList.add(model);
                        Log.e(TAG, "initSystemContacts: "+model );
                    }
                }
                cursor.close();
                runOnUiThread(()->{
                    contactViewModelList.addAll(tmpList);
                    adapter.notifyDataSetChanged();
                });


            }catch (Exception err){
                err.printStackTrace();
                Log.i(TAG, "initSystemContacts: err");
            }


        }).start();



    }
//    String chReg = "[\\u4E00-\\u9FA5]+";// 中文字符串匹配
//
//    // String chReg="[^\\u4E00-\\u9FA5]";//除中文外的字符匹配
//    /**
//     * 解析sort_key,封装简拼,全拼
//     *
//     * @param sortKey
//     * @return
//     */
//    public SortToken parseSortKey(String sortKey) {
//        SortToken token = new SortToken();
//        if (sortKey != null && sortKey.length() > 0) {
//            // 其中包含的中文字符
//            String[] enStrs = sortKey.replace(" ", "").split(chReg);
//            for (int i = 0, length = enStrs.length; i < length; i++) {
//                if (enStrs[i].length() > 0) {
//                    // 拼接简拼
//                    token.simpleSpell += enStrs[i].charAt(0);
//                    token.wholeSpell += enStrs[i];
//                }
//            }
//        }
//        return token;
//    }

}