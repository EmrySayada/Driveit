package com.example.driveit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class LessonAdapter extends ArrayAdapter<Lesson> {
    private Context context;
    private int resource;
    String type;
    public LessonAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Lesson> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }
    protected String[] formatDate(String date){
        String[] res= date.split(" ");
        // date comes in as DD/MM/YYYY HH:mm
        return res;
    }

    public static class ViewHolder{
        TextView typeTv;
        TextView dateTv;
        TextView timeTv;
    }

    @Override
    public View getView(int position, @NonNull android.view.View convertView, @NonNull android.view.ViewGroup parent){
        type=getItem(position).getType();
        String[] date = formatDate(getItem(position).getTimestamp());
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.typeTv = convertView.findViewById(R.id.typeTv);
            holder.dateTv = convertView.findViewById(R.id.dateTv);
            holder.timeTv = convertView.findViewById(R.id.timeTv);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.typeTv.setText(type);
        holder.dateTv.setText(date[0]);
        holder.timeTv.setText(date[1]);
        return convertView;
    }

}
