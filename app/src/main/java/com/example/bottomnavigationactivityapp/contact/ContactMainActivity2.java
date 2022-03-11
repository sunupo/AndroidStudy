package com.example.bottomnavigationactivityapp.contact;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ContactMainActivity2 extends BaseActivity {

    private final List<LabelViewModel> labelViewModelList = new ArrayList<>();
    private final List<LabelViewModel> labelViewModelListStore = new ArrayList<>();
    LabelAdapter adapter;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    String TAG ="ContactMainActivity2";
    ListView listView;


    public ContactMainActivity2(){
        Log.i(TAG, "ContactMainActivity: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_main);
        requestPermissionLauncher=
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        loadContact();
//                    call();
                    } else {
                        Toast.makeText(this, "没有获取到权限", Toast.LENGTH_LONG).show();
                    }
                });
        initSystemContacts();
        adapter = new LabelAdapter(this, R.layout.listview_item, labelViewModelList,this);
        listView = findViewById(R.id.contact_label_view);
        listView.setAdapter(adapter);
        EditText editText = findViewById(R.id.search_content);
        Button button = findViewById(R.id.confirm);
        SideBar sideBar = findViewById(R.id.side_bar);

        getIntent().getComponent().getPackageName();


        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                scroll(s);
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

                restoreList(labelViewModelList, labelViewModelListStore);
                if(str.equals("")){
                    adapter.notifyDataSetChanged();
                    scroll("A");
                    return;
                }
                filterList(str, labelViewModelList);
                adapter.notifyDataSetChanged();
//                Toast.makeText(getApplicationContext(),contactViewModelListStore.size()+"\t"+contactViewModelList.size(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        


    }
    public void filterList(String word, @NotNull List<LabelViewModel> src){
        Iterator<LabelViewModel> iterator = src.iterator();
        while(iterator.hasNext()){
            LabelViewModel model = iterator.next();
            if(!model.label.contains(word.toUpperCase())){
                Iterator<ContactViewModel> iterator1 = model.getContactList().iterator();
                while(iterator1.hasNext()){
                    ContactViewModel model1 = iterator1.next();
                    if(!model1.getPhone().contains(word) && !model1.getName().contains(word)){
                        iterator1.remove();
                    }
                }
            }
            if(model.getContactList().size()==0){
                iterator.remove();
            }
        }
//        if(word==null || word.trim().equals("")){
//            return new ArrayList<>(src);
//        }else {
//            return src.stream().filter((model) -> model.getPhone().contains(word) || model.getName().contains(word)).collect(Collectors.toList());
//        }
    }

    public void restoreList(List<LabelViewModel> dest, List<LabelViewModel> src){
        dest.clear();
//        todo 这儿必须深拷贝。因为LabelViewModel的ContactViewModel属性是一个引用类型。
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(src);
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
//            todo 列表对象含有列表属性，类型转换怎么校验。
            dest.addAll((List<LabelViewModel>) objectInputStream.readObject());

        } catch (IOException | ClassNotFoundException e) {
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
            Log.i(TAG, "initSystemContacts: 已有权限");
            loadContact();
        } else if (shouldShowRequestPermissionRationale(requestPermission)) {
            Toast.makeText(this, "shouldShowRequestPermissionRationale", Toast.LENGTH_LONG).show();
            requestPermissionLauncher.launch(
                    requestPermission);
        } else {
            requestPermissionLauncher.launch(
                    requestPermission);
        }




    }
    public void loadContact(){
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
//                final List<ContactViewModel> tmpList = new ArrayList<>();
                final Map<String,List<ContactViewModel>> map = new HashMap<>();
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
                        Log.e(TAG, "initSystemContacts: "+model );


                        if(map.containsKey(label)){
                            map.get(label).add(model);
                        }else{
                            map.put(label, new ArrayList<>());
                            map.get(label).add(model);
                        }
                    }
                }
                for (String key:map.keySet() ) {
                    if(map.get(key).size()>0){
                        labelViewModelListStore.add(new LabelViewModel(key, map.get(key)));
                    }
                }
                cursor.close();
                Collections.sort(labelViewModelListStore);
                labelViewModelListStore.forEach((i)->{
                    Log.i(TAG, "loadContact: "+i.label);
                });
//                todo 排序方式2
//                labeltViewModelListStore.sort((o1, o2) -> {
//                    Log.i(TAG, "compare: "+SideBar.b.toString());
//                    String join = String.join("", SideBar.b);
//
//                    return join.indexOf(o1.label.toUpperCase())-join.indexOf(o2.label.toUpperCase());
//                });
                runOnUiThread(()->{
                    adapter.clear();
                    adapter.addAll(labelViewModelListStore);
                    adapter.notifyDataSetChanged();
                });


            }catch (Exception err){
                err.printStackTrace();
                Log.i(TAG, "initSystemContacts: err");
            }


        }).start();
    }



    public void scroll(String position){
////        final可以用原子Atomic类替换
//        AtomicInteger idx= new AtomicInteger(0);
//        labelViewModelList.forEach((i)->{
//            if(i.label.equals(position)){
//                listView.smoothScrollToPosition(idx.get());
////                listView.post(()->listView.smoothScrollToPosition(idx.get()));   //todo 用个线程不行。
//            }else{
//                idx.getAndIncrement();
////                idx.addAndGet(1);
//            }
//        });
        int idx= 0;
//        boolean flag=false;
        for (LabelViewModel model:labelViewModelList) {
            if(model.label.equals(position)){
                listView.smoothScrollToPosition(idx);
//                listView.post(()->listView.smoothScrollToPosition(idx)); //todo idx只能为final才能访问
//                flag=true;
                break;
            }else{
                idx++;
            }

        }
//        final int index = idx;
//        if(flag) {
//            listView.post(()->listView.smoothScrollToPosition(index)); //todo 用个线程可以。
//
//        }

        new Consumer<Integer>(){

            @Override
            public void accept(Integer integer) {

            }
        };


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