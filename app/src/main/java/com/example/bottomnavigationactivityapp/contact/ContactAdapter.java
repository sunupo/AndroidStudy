package com.example.bottomnavigationactivityapp.contact;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bottomnavigationactivityapp.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<ContactViewModel> {

    public int resource;

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<ContactViewModel> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        ContactViewModel model = getItem(position); //
        View view;
        ViewHolder viewHolder;
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resource, parent, false);//只能为false。因为一旦View有了父布局，就不能再添加到ListView了。//https://blog.csdn.net/wzmde007/article/details/80067806
            viewHolder = new ViewHolder();
            viewHolder.setImageView(view.findViewById(R.id.contact_image));
            viewHolder.setName(view.findViewById(R.id.contact_name));
            viewHolder.setPhone(view.findViewById(R.id.contact_phone));
            view.setTag(viewHolder);

        }else{
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(R.drawable.contact_phone);
        viewHolder.imageView.setBackgroundResource(R.drawable.animation1);
        AnimationDrawable drawable = (AnimationDrawable)viewHolder.imageView.getBackground();
        viewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                drawable.start();
                return true;
            }
        });
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawable.stop();
            }
        });

        viewHolder.name.setText(model.getName());
        viewHolder.phone.setText(model.getPhone());
        return view;
    }
    static class ViewHolder{
        ImageView imageView;
        TextView name, phone;

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getPhone() {
            return phone;
        }

        public void setPhone(TextView phone) {
            this.phone = phone;
        }
    }

}
