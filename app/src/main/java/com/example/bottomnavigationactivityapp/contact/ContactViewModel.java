package com.example.bottomnavigationactivityapp.contact;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ContactViewModel implements Cloneable, Serializable {
    public int imageId;
    public String name;
    public String phone;
    public String sortKey;
    public String label;

    public ContactViewModel(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public ContactViewModel() {
    }

    @Override
    public String toString() {
        return "ContactViewModel{" +
                "imageId=" + imageId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", sortKey='" + sortKey + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

    @NonNull
    @Override
    public ContactViewModel clone() {
        try {
            return (ContactViewModel) super.clone();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
