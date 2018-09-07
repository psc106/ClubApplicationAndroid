package teamproject.com.clubapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import teamproject.com.clubapplication.fragment.GroupAlbumFragment;
import teamproject.com.clubapplication.fragment.GroupBoardFragment;
import teamproject.com.clubapplication.fragment.GroupCalendarFragment;
import teamproject.com.clubapplication.fragment.GroupHomeFragment;
import teamproject.com.clubapplication.fragment.GroupManageFragment;

public class GroupViewpagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public GroupViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }

    public void addFragment(Fragment fragment, String title, int position) {
        mFragmentList.add(position, fragment);
        mFragmentTitleList.add(position, title);
    }

    public void removeFragment(Fragment fragment, int position) {
        mFragmentList.remove(position);
        mFragmentTitleList.remove(position);
    }
    public void clearFragment() {
        mFragmentList.clear();
        mFragmentTitleList.clear();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
