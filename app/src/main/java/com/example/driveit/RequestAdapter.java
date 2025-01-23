package com.example.driveit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class RequestAdapter extends ArrayAdapter<Request> {
    private Context context;
    private int resource;
    private int student_id;
    private User user;
    private DBHelper mydb;
    private SQLiteDatabase sqdb;

    public RequestAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Request> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    public User getUserInformation(int id){
        User user = null;
        sqdb = mydb.getWritableDatabase();
        user = mydb.getUserById(id);
        sqdb.close();
        return user;
    }

    public static class ViewHolder{
        ImageView studentPic;
        TextView studentName;
        Button callBtn;
    }

    @Override
    public View getView(int position, @NonNull android.view.View convertView, @NonNull android.view.ViewGroup parent){
        student_id = getItem(position).getStudent_id();
        user = getUserInformation(student_id);
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView=inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
        }

    }
}
