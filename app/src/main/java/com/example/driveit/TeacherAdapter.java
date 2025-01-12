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

public class TeacherAdapter extends ArrayAdapter<Teacher> {
    private Context context;
    private int resource;
    String name, region;
    int exp;
    Bitmap image;

    public TeacherAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Teacher> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
    }

    public static class ViewHolder{
        ImageView profilePicIv;
        TextView nameTv;
        TextView expTv;
        TextView regionTv;
        Button requestBtn;
    }
    @Override
    public View getView(int position, @NonNull android.view.View convertView, @NonNull android.view.ViewGroup parent){
        name=getItem(position).getUsername();
        exp=getItem(position).getExp();
        region=getItem(position).getRegion();
        image=getItem(position).getImage();
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView=inflater.inflate(resource, parent, false);
            holder=new ViewHolder();
            holder.profilePicIv=convertView.findViewById(R.id.profilePicIv);
            holder.nameTv=convertView.findViewById(R.id.nameTv);
            holder.expTv=convertView.findViewById(R.id.expTv);
            holder.regionTv=convertView.findViewById(R.id.regionTv);
            holder.requestBtn=convertView.findViewById(R.id.requestBtn);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        holder.profilePicIv.setImageBitmap(image);
        holder.nameTv.setText(name);
        holder.expTv.setText(exp + " years");
        holder.regionTv.setText(region);
        holder.requestBtn.setOnClickListener(null);
        return convertView;
    }
}
