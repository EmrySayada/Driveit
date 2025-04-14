package com.example.driveit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emry Sayada
 * class that orgenizes all the lessons into UI elements
 */
public class LessonAdapter extends ArrayAdapter<Lesson> {
    private Context context;
    private int resource;
    String type;

    /**
     * constructor for the adapter
     * @param context
     * @param resource
     * @param objects
     */
    public LessonAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Lesson> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    /**
     * function that formats the timestamp of a lesson into an array [date, time]
     * @param date
     * @return date array
     */
    protected String[] formatDate(String date){
        String[] res= date.split(" ");
        // date comes in as DD/MM/YYYY HH:mm
        return res;
    }

    /**
     * class that houses all the UI elements of an item
     */
    public static class ViewHolder{
        TextView typeTv;
        TextView dateTv;
        TextView timeTv;
        ImageButton moreInfoButton;
    }

    /**
     * function that initializes all the elements and sets the settings for them
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return an item
     */
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
            holder.moreInfoButton = convertView.findViewById(R.id.moreInfoButton);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.typeTv.setText(type);
        holder.dateTv.setText(date[0]);
        holder.timeTv.setText(date[1]);
        holder.moreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(getContext(), LessonSummeryActivity.class);
                go.putExtra("lessonId", getItem(position).getLessonId());
                context.startActivity(go);
            }
        });
        if(getItem(position).getStatus().equals(Lesson.LESSON_PENDING) || getItem(position).getStatus().equals(Lesson.LESSON_ONGOING)){
            holder.moreInfoButton.setEnabled(false);
        }
        return convertView;
    }

}
