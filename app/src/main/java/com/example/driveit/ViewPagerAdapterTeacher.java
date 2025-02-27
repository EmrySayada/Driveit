package com.example.driveit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapterTeacher extends FragmentStateAdapter {

    public ViewPagerAdapterTeacher(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            return new LessonFragment();
        }else if(position == 1){
            return new RequestFragment();
        }else{
            return new PupilsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
