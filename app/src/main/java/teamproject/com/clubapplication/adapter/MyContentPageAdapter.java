package teamproject.com.clubapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import teamproject.com.clubapplication.fragment.MyContentPostFragment;

public class MyContentPageAdapter extends FragmentStatePagerAdapter {
    final int MY_CONTENT_PAGE_COUNT = 2;

    public MyContentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position==0) {
            fragment = MyContentPostFragment.getInstance();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return this.MY_CONTENT_PAGE_COUNT;
    }
}
