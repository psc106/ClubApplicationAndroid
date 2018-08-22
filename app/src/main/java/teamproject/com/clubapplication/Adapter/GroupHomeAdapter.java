package teamproject.com.clubapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import teamproject.com.clubapplication.fragment.GroupAlbum;
import teamproject.com.clubapplication.fragment.GroupBoardFragment;
import teamproject.com.clubapplication.fragment.GroupCalendar;
import teamproject.com.clubapplication.fragment.GroupHomeFragment;
import teamproject.com.clubapplication.fragment.GroupManage;

public class GroupHomeAdapter extends FragmentStatePagerAdapter {
    public GroupHomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return GroupHomeFragment.getInstance();
        }else if(position==1){
            return GroupBoardFragment.getInstance();
        }else if(position==2){
            return GroupCalendar.getInstance();
        }else if(position==3){
            return GroupAlbum.getInstance();
        }else if(position==4){
            return GroupManage.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
