package com.example.driveit;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PupilAdapter extends ArrayAdapter<User> {
    private Context context;
    private int resource;
    String name;
    Bitmap pupilImage;


    public PupilAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
    }

    public static class ViewHolder{
        ImageView pupilPicAdapter;
        TextView pupilNameAdapter;
        Button appointLessonBtnAdapter;
    }

    @Override
    public View getView(int position, @NonNull android.view.View convertView, @NonNull android.view.ViewGroup parent){
        name = getItem(position).getUsername();
        pupilImage = getItem(position).getImage();
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.pupilPicAdapter = convertView.findViewById(R.id.pupilPicAdapter);
            holder.pupilNameAdapter = convertView.findViewById(R.id.pupilNameAdapter);
            holder.appointLessonBtnAdapter = convertView.findViewById(R.id.appointLessonBtnAdapter);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.pupilPicAdapter.setImageBitmap(pupilImage);
        holder.pupilNameAdapter.setText(name);
        holder.appointLessonBtnAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // move the teacher to another page where he canappoint a lesson to a student
                // using putExtra, we give the activity, the user id, teacher id

            }
        });
        return convertView;
    }
}
