package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.utils.bus.BusProvider;

public class MyContentPostFragment extends Fragment {

    private static MyContentPostFragment fragment = null;
    public static MyContentPostFragment getInstance() {
        if(fragment==null) {
            fragment = new MyContentPostFragment();
        }
        return fragment;
    }

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
