package com.example.driveit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emry Sayada
 * class that convert all the items into UI elements
 */
public class PupilAdapter extends ArrayAdapter<User> {
    private Context context;
    private int resource;
    String name;
    Bitmap pupilImage;

    /**
     * constructor for the adapter
     * @param context
     * @param resource
     * @param objects
     */
    public PupilAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    /**
     * class that houses all the UI elements in an item
     */
    public static class ViewHolder{
        ImageView pupilPicAdapter;
        TextView pupilNameAdapter;
        Button appointLessonBtnAdapter;
    }

    /**
     * function that initializes all the UI elements and creates them
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return view
     */
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
                // move the teacher to another page where he can appoint a lesson to a student
                // using putExtra, we give the activity, the user id, teacher id.
                Intent intent = new Intent(getContext(), AppointLesson.class);
                intent.putExtra("teacherId", getItem(position).getCurrentTeacherId());
                intent.putExtra("userId", getItem(position).getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
