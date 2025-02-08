package com.example.driveit;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class LessonAdapter extends ArrayAdapter<Lesson> {
    private Context context;
    private int resource;
    String type, date;
    public LessonAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Lesson> objects) {
        super(context, resource, objects);
    }

    public static class ViewHolder{
        TextView typeTv;
        TextView dateTv;
        TextView timeTv;
    }

    @Override
    public View getView(int position, @NonNull android.view.View convertView, @NonNull android.view.ViewGroup parent){
        type=getItem(position).getType();
        date=getItem(position).getDate();
    }

}
