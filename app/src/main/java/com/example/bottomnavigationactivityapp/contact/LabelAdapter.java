package com.example.bottomnavigationactivityapp.contact;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.bottomnavigationactivityapp.R;

import java.util.List;

public class LabelAdapter extends ArrayAdapter<LabelViewModel> {

    Context context;
    public int resource;
    List<LabelViewModel> list;
    AppCompatActivity activity;
    public String TAG ="TAGLabelAdapter";

    public ContactAdapter contactAdapter;

    private ActivityResultLauncher<String> requestPermissionLauncher;


    public LabelAdapter(@NonNull Context context, int resource, @NonNull List<LabelViewModel> objects, AppCompatActivity activity) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
        this.activity = activity;
        this.requestPermissionLauncher =
                activity.registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Log.i(TAG, "LabelAdapter: isGranted");
//                    call();
                    } else {
                        Toast.makeText(activity, "没有拨打电话权限", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        Log.i(TAG, "getView: "+position);
        LabelViewModel model = getItem(position); //
        Log.e(TAG, "getView: "+model.toString() );
        View view;
        ViewHolder viewHolder;
        if(convertView==null) {
            Log.i(TAG, "getView: covert");
            view = LayoutInflater.from(getContext()).inflate(resource, parent, false);//只能为false。因为一旦View有了父布局，就不能再添加到ListView了。//https://blog.csdn.net/wzmde007/article/details/80067806
            viewHolder = new ViewHolder();
            viewHolder.setLabel(view.findViewById(R.id.label));
            viewHolder.setListView(view.findViewById(R.id.contact_list_view));
            view.setTag(viewHolder);

        }else{
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Log.i(TAG, "getView: "+viewHolder.label);
        viewHolder.label.setText(model.getLabel());
        contactAdapter = new ContactAdapter(context, R.layout.contact_item, model.contactList);
        viewHolder.getListView().setAdapter(contactAdapter);
        setListViewHeightBasedOnChildren(viewHolder.getListView());
        viewHolder.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position2, long id) {
                String requestPermission= Manifest.permission.CALL_PHONE;

                if (ContextCompat.checkSelfPermission(
                        context, requestPermission) ==
                        PackageManager.PERMISSION_GRANTED) {
                    call(position,position2);
                } else if (activity.shouldShowRequestPermissionRationale(requestPermission)) {
                    Toast.makeText(activity, "shouldShowRequestPermissionRationale", Toast.LENGTH_LONG).show();
                    requestPermissionLauncher.launch(
                            requestPermission);
                } else {
                    requestPermissionLauncher.launch(
                            requestPermission);
                }
            }
        });
//        viewHolder.setListView(view.findViewById(R.id.contact_list_view));

        return view;
    }

    public void call(int position1,int position2){
        try {
            ContactViewModel model = list.get(position1).contactList.get(position2);
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + model.getPhone()));
            activity.startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    static class ViewHolder{
        TextView label;
        ListView listView;

        public TextView getLabel() {
            return label;
        }

        public void setLabel(TextView label) {
            this.label = label;
        }

        public ListView getListView() {
            return listView;
        }

        public void setListView(ListView listView) {
            this.listView = listView;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < Math.min(999,listAdapter.getCount()); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }



}
