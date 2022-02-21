package com.example.bottomnavigationactivityapp.contact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationactivityapp.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter2 extends RecyclerView.Adapter<ContactAdapter2.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.contact_image);
            name = itemView.findViewById(R.id.contact_name);
            phone = itemView.findViewById(R.id.contact_phone);
        }
    }

    private List<ContactViewModel> list = new ArrayList<>();

    public ContactAdapter2(List<ContactViewModel> modelList) {
        this.list = modelList;  // 无法通过getItem(position)获取当前的model，所以需要传递list。
    }

    public List<ContactViewModel> getList() {
        return list;
    }

    public void setList(List<ContactViewModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactViewModel model = list.get(position);
//        holder.imageView.setImageResource(R.drawable.add_contact);
        holder.imageView.setImageResource(model.getImageId());
        holder.name.setText(model.getName());
        holder.phone.setText(model.getPhone());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
