package teamproject.com.clubapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.PostView;
import teamproject.com.clubapplication.fragment.GroupBoardDetailFragment;
import teamproject.com.clubapplication.fragment.GroupBoardLoadingFragment;

public class GroupPostDetailPageAdapter extends FragmentStatePagerAdapter {
    PostView postView;

    public GroupPostDetailPageAdapter(FragmentManager fm, PostView postView) {
        super(fm);
        this.postView = postView;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return GroupBoardDetailFragment.newInstance(postView);
            case 2:
                return GroupBoardDetailFragment.newInstance(postView);
            case 4:
                return GroupBoardDetailFragment.newInstance(postView);
            case 1:
                return GroupBoardLoadingFragment.newInstance();
            case 3:
                return GroupBoardLoadingFragment.newInstance();
        }
        return null;

    }


    @Override
    public int getCount() {
        return 5;
    }
}
