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
import teamproject.com.clubapplication.utils.RefreshData;


public class GroupCalendarFragment extends Fragment  implements RefreshData {

    private static GroupCalendarFragment curr = null;


    @BindView(R.id.lv_group_calendar)
    ListView lvGroupCalendar;
    Unbinder unbinder;

    ArrayList<?>arrayList;
    GroupCalendarListviewAdapter groupCalendarListviewAdapter;

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

    @Override
    public void refresh() {

    }
}
