package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamproject.com.clubapplication.R;


public class GroupCalendarFragment extends Fragment {

    private static GroupCalendarFragment curr = null;

    @BindView(R.id.lv_group_calendar)
    ListView lvGroupCalendar;
    Unbinder unbinder;

    public static GroupCalendarFragment getInstance() {

        if (curr == null) {
            curr = new GroupCalendarFragment();
        }

        return curr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_calendar, container, false);

        unbinder = ButterKnife.bind(this, view);



        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
