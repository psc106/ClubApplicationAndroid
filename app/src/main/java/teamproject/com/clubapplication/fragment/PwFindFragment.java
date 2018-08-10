package teamproject.com.clubapplication.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamproject.com.clubapplication.R;

public class PwFindFragment extends Fragment {
    private static PwFindFragment curr = null;
    public static PwFindFragment getInstance() {
        if (curr == null) {
            curr = new PwFindFragment();
        }

        return curr;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pw_find_fragment, container, false);

        return view;
    }


}