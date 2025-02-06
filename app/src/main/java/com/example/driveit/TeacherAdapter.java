package com.example.driveit;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Emry Sayada
 * class that holds the code for the teacher adapter
 */
public class TeacherAdapter extends ArrayAdapter<Teacher> {
    private Context context;
    private int resource;
    String name, region;
    int exp;
    Bitmap image;
    int studentId;

    /**
     * constructor for the adapter
     * @param context
     * @param resource
     * @param objects
     */
    public TeacherAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Teacher> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
    }

    /**
     * function that gets the student id (used to create a request)
     * @param studentId
     */
    public void setStudentId(int studentId){
        this.studentId = studentId;
    }

    /**
     * class that holds all the elements in the UI
     */
    public static class ViewHolder{
        ImageView profilePicIv;
        TextView nameTv;
        TextView expTv;
        TextView regionTv;
        Button requestBtn;
    }

    /**
     * function that organizes all the information on a teacher in the UI
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return
     */
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
        holder.requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request r = new Request(studentId, getItem(position).getId());
                r.setMydb(context);
                DBHelper mydb = new DBHelper(context);
                SQLiteDatabase sqdb = mydb.getWritableDatabase();
                mydb.insertRequest(r);
                sqdb.close();
                Toast.makeText(context, "Sent Request!", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
