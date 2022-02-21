package com.example.bottomnavigationactivityapp.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bottomnavigationactivityapp.R;

import java.util.List;

public class LabelAdapter extends ArrayAdapter<LabelViewModel> {

    public int resource;

    public LabelAdapter(@NonNull Context context, int resource, @NonNull List<LabelViewModel> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        LabelViewModel model = getItem(position); //
        View view;
        ViewHolder viewHolder;
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resource, parent, false);//只能为false。因为一旦View有了父布局，就不能再添加到ListView了。//https://blog.csdn.net/wzmde007/article/details/80067806
            viewHolder = new ViewHolder();
            viewHolder.setLabel(view.findViewById(R.id.label));
            viewHolder.setListView(view.findViewById(R.id.contact_list_view));
            view.setTag(viewHolder);

        }else{
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.label.setText(model.getLabel());
//        viewHolder.setListView(view.findViewById(R.id.contact_list_view));

        return view;
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

}
