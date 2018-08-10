package teamproject.com.clubapplication.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamproject.com.clubapplication.R;

public class IdFindFragment extends Fragment {
    private static IdFindFragment curr = null;
    public static IdFindFragment getInstance() {
        if (curr == null) {
            curr = new IdFindFragment();
        }

        return curr;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.id_find_fragment, container, false);

        return view;
    }


}
