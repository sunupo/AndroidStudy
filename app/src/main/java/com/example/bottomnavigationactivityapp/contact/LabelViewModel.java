package com.example.bottomnavigationactivityapp.contact;

import android.util.Log;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

public class LabelViewModel implements Serializable, Comparable<LabelViewModel> {

    public String label;
    public List<ContactViewModel> contactList;
    String TAG = "LabelViewModel";

    @Override
    public int compareTo(LabelViewModel o) {
        String join = String.join("", SideBar.b);
        int idx1 = Math.max(0, join.indexOf(label.toUpperCase()));
        int idx2 = Math.max(0, join.indexOf(o.label.toUpperCase()));
        Log.i(TAG, "compareTo: "+idx1+idx2+label+o.label);
        return idx1-idx2;
    }

    public LabelViewModel(String label, List<ContactViewModel> contactList) {
        this.label = label;
        this.contactList = contactList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ContactViewModel> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactViewModel> contactList) {
        this.contactList = contactList;
    }

    @Override
    public String toString() {
        return "LabelViewModel{" +
                "label='" + label + '\'' +
                ", contactList=" + contactList.toString() +
                '}';
    }

    public String getList(){
        StringBuilder builder = new StringBuilder();
        for (ContactViewModel model: contactList) {
            builder.append(model.toString());
        }
        return builder.toString();
    }
}
