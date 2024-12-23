package com.example.driveit;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {
    private Context context;

    private int resource;
    String username, phone, email;
    Bitmap image;

    public UserAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    private static class ViewHolder{
        TextView username;
        TextView ph;
        TextView em;
        ImageView im;
    }
    @Override
    public View getView(int position, @NonNull android.view.View convertView, @NonNull android.view.ViewGroup parent){
        username=getItem(position).getUsername();
        phone=getItem(position).getPhone();
        email=getItem(position).getEmail();
        image=getItem(position).getImage();
        ViewHolder holder;
        if(convertView==null)
        {
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(resource,parent,false);
            holder=new ViewHolder();
            holder.username=convertView.findViewById(R.id.tvUser);
            holder.ph=convertView.findViewById(R.id.tvPhone);
            holder.em=convertView.findViewById(R.id.tvMail);
            holder.im=convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder) convertView.getTag();
        }
        holder.username.setText(username);
        holder.ph.setText(phone);
        holder.em.setText(email);
        holder.im.setImageBitmap(image);
        return convertView;
    }
}
