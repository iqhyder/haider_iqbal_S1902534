//Iqbal_Haider_S1902534
package org.mpd.hyder_iqbal_S1902534.adapter;

import android.content.Context;

import org.mpd.hyder_iqbal_S1902534.fragments.CurrentFragment;
import org.mpd.hyder_iqbal_S1902534.fragments.PlannedFragment;
import org.mpd.hyder_iqbal_S1902534.fragments.RoadWorksFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public FragmentAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CurrentFragment();
            case 1:
                return new RoadWorksFragment();
            case 2:
                return new PlannedFragment();
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}