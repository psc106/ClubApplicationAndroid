package teamproject.com.clubapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamproject.com.clubapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupHomeFragment extends Fragment {
    private static GroupHomeFragment curr = null;

    public static GroupHomeFragment getInstance() {
        if (curr == null) {
            curr = new GroupHomeFragment();
        }

        return curr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_home, container, false);

        return view;
        
    }
}
