package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.adapter.GroupAlbumGridviewAdapter;
import teamproject.com.clubapplication.utils.RefreshData;


public class GroupAlbumFragment extends Fragment implements RefreshData {


    private static GroupAlbumFragment curr = null;
    @BindView(R.id.group_album_gv)
    GridView groupAlbumGv;
    Unbinder unbinder;

    ArrayList<?>arrayList;
    GroupAlbumGridviewAdapter groupAlbumGridviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_album, container, false);

        unbinder = ButterKnife.bind(this, view);

        arrayList=new ArrayList<>();
        groupAlbumGridviewAdapter=new GroupAlbumGridviewAdapter(arrayList);
        groupAlbumGv.setAdapter(groupAlbumGridviewAdapter);


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
