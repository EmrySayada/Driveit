package com.example.driveit;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class RequestAdapter extends ArrayAdapter<Request> {
    private Context context;
    private int resource;
    String student_name, phone_number;
    Bitmap image;


    public RequestAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Request> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    public static class ViewHolder{
        ImageView studentPic;
        TextView studentName;
        Button callBtn;
        ImageButton rejectBtn, approveBtn;
    }

    @Override
    public View getView(int position, @NonNull android.view.View convertView, @NonNull android.view.ViewGroup parent){
        student_name = getItem(position).getUserUsername();
        phone_number = getItem(position).getUserPhoneNumber();
        image = getItem(position).getUserImage();
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView=inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.studentPic = convertView.findViewById(R.id.studentPic);
            holder.studentName = convertView.findViewById(R.id.studentName);
            holder.callBtn = convertView.findViewById(R.id.callBtn);
            holder.rejectBtn = convertView.findViewById(R.id.rejectBtn);
            holder.approveBtn = convertView.findViewById(R.id.approveBtn);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        holder.studentPic.setImageBitmap(image);
        holder.studentName.setText(student_name);
        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = "tel:" + phone_number;
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(tel));
                context.startActivity(intent);
            }
        });
        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItem(position).acceptRequest();
            }
        });
        holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItem(position).rejectRequest();

            }
        });
        return convertView;

    }
}
