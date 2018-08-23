package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.adapter.GroupCalendarListviewAdapter;


public class GroupCalendarFragment extends Fragment {

    private static GroupCalendarFragment curr = null;


    @BindView(R.id.lv_group_calendar)
    ListView lvGroupCalendar;
    Unbinder unbinder;

    ArrayList<?>arrayList;
    GroupCalendarListviewAdapter groupCalendarListviewAdapter;

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

        arrayList=new ArrayList<>();
        groupCalendarListviewAdapter =new GroupCalendarListviewAdapter(arrayList);
        lvGroupCalendar.setAdapter(groupCalendarListviewAdapter);



        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
