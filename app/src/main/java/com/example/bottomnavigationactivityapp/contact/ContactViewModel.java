package com.example.bottomnavigationactivityapp.contact;

import android.graphics.drawable.Drawable;

public class ContactViewModel {
    public int imageId;
    public String name;
    public String phone;
    public String sortKey;
    public String label;


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
