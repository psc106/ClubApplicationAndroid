package teamproject.com.clubapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import teamproject.com.clubapplication.fragment.GroupAlbumFragment;
import teamproject.com.clubapplication.fragment.GroupBoardFragment;
import teamproject.com.clubapplication.fragment.GroupCalendarFragment;
import teamproject.com.clubapplication.fragment.GroupHomeFragment;
import teamproject.com.clubapplication.fragment.GroupManageFragment;

public class GroupHomeViewpagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    public GroupHomeViewpagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;

    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new GroupHomeFragment();
        }else if(position==1){
            return new GroupBoardFragment();
        }else if(position==2){
            return new GroupAlbumFragment();
        }else if(position==3){
            return new GroupCalendarFragment();
        }else if(position==4){
            return new GroupManageFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public void setTabCount(int tabCount) {
        this.tabCount = tabCount;
    }
}
