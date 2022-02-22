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
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.bottomnavigationactivityapp.BaseActivity;
import com.example.bottomnavigationactivityapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactMainActivity extends BaseActivity {

    private List<ContactViewModel> contactViewModelList = new ArrayList<>();
    private List<ContactViewModel> contactViewModelListStore = new ArrayList<>();
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

    public void customClone(List<ContactViewModel> a, List<ContactViewModel> b) {
        a.clear();
        for (ContactViewModel model: b) {
            a.add(model.clone());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_main);
        initSystemContacts();
        adapter = new ContactAdapter(this, R.layout.contact_item, contactViewModelList);
        EditText editText = findViewById(R.id.search_content);
        Button button = findViewById(R.id.confirm);
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
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString().trim();
//                只能更新contactViewModelList，不能创建新的contactViewModelList。因为新的contactViewModelList引用没有传递到adapter。
//                adapter有没有替换数据源的引用的方法。

                restoreList(contactViewModelList,contactViewModelListStore);
                filterList(str, contactViewModelList);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),contactViewModelListStore.size()+"\t"+contactViewModelList.size(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    public void filterList(String word, @NotNull List<ContactViewModel> src){
        Iterator<ContactViewModel> iterator = src.iterator();
        while(iterator.hasNext()){
            ContactViewModel model = iterator.next();
            if(!model.getPhone().contains(word) && !model.getName().contains(word)){
                iterator.remove();
            }
        }
//        if(word==null || word.trim().equals("")){
//            return new ArrayList<>(src);
//        }else {
//            return src.stream().filter((model) -> model.getPhone().contains(word) || model.getName().contains(word)).collect(Collectors.toList());
//        }
    }

    public void restoreList(List<ContactViewModel> dest, List<ContactViewModel> src){
        dest.clear();
        dest.addAll(src);
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
        String requestPermission=Manifest.permission.READ_CONTACTS;
        if (ContextCompat.checkSelfPermission(
                this, requestPermission) ==
                PackageManager.PERMISSION_GRANTED) {
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
//                final List<ContactViewModel> tmpList = new ArrayList<>();;
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
                            contactViewModelListStore.add(model);
                            Log.e(TAG, "initSystemContacts: "+model );
                        }
                    }
                    cursor.close();
                    runOnUiThread(()->{
                        adapter.clear();
                        adapter.addAll(contactViewModelListStore);
                        adapter.notifyDataSetChanged();
                    });

                }catch (Exception err){
                    err.printStackTrace();
                    Log.i(TAG, "initSystemContacts: err");
                }

            }).start();

        } else if (shouldShowRequestPermissionRationale(requestPermission)) {
            Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_LONG).show();
        } else {
            requestPermissionLauncher.launch(
                    requestPermission);
        }




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