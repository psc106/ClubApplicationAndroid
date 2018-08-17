package teamproject.com.clubapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamproject.com.clubapplication.R;


public class GroupBoardWriteBoardFragment extends Fragment {

    private static GroupBoardWriteBoardFragment curr = null;
    public static GroupBoardWriteBoardFragment getInstance() {

        if (curr == null) {
            curr = new GroupBoardWriteBoardFragment();
        }

        return curr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_board_write_board, container, false);

        return view;
    }


}
