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
    public GroupHomeViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return GroupHomeFragment.getInstance();
        }else if(position==1){
            return GroupBoardFragment.getInstance();
        }else if(position==2){
            return GroupCalendarFragment.getInstance();
        }else if(position==3){
            return GroupAlbumFragment.getInstance();
        }else if(position==4){
            return GroupManageFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
