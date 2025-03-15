package com.example.driveit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * @author Emry Sayada
 * class that holds the logic for the fragment transition in the teacher Activity
 */
public class ViewPagerAdapterTeacher extends FragmentStateAdapter {
    /**
     * constructor
     * @param fragmentActivity
     */
    public ViewPagerAdapterTeacher(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     * function that enforces the order of the fragments
     * @param position
     * @return fragment
     */
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

    /**
     * function that holds the amount of fragments
     * @return amount of fragments
     */
    @Override
    public int getItemCount() {
        return 3;
    }
}
