package teamproject.com.clubapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamproject.com.clubapplication.R;


public class GroupBoardDetailFragment extends Fragment {

    private static GroupBoardDetailFragment curr=null;
    public static GroupBoardDetailFragment getinstance() {
        if(curr==null){
            curr=new GroupBoardDetailFragment();
        }
        return curr;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_group_board_detail,container,false);

        return view;
    }

}
