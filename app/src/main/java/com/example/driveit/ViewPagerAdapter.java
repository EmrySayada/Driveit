package com.example.driveit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * @author Emry Sayada
 * class that holds the adapter of the fragments
 */
public class ViewPagerAdapter extends FragmentStateAdapter {
    /**
     * constructor
     * @param fragmentActivity
     */
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity){super(fragmentActivity);}

    /**
     * function that creates the fragment and shows it on the UI
     * @param position
     * @return login/register fragment
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            return new LoginFragment();
        }else{
            return new RegisterFragment();
        }
    }

    /**
     * function that gets the amount of fragments
     * @return item count
     */
    @Override
    public int getItemCount() {
        return 2;
    }
}
