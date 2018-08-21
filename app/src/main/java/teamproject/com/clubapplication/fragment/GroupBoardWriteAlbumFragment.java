package teamproject.com.clubapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import teamproject.com.clubapplication.R;


public class GroupBoardWriteAlbumFragment extends Fragment {

    private static GroupBoardWriteAlbumFragment curr = null;
    @BindView(R.id.group_board_write_album_img1)
    ImageView groupBoardWriteAlbumImg1;
    @BindView(R.id.group_board_write_check)
    CheckBox groupBoardWriteCheck;
    @BindView(R.id.group_board_write_album_edt)
    EditText groupBoardWriteAlbumEdt;
    @BindView(R.id.group_board_write_album_img2)
    ImageView groupBoardWriteAlbumImg2;
    @BindView(R.id.group_board_write_album_img3)
    ImageView groupBoardWriteAlbumImg3;
    @BindView(R.id.group_board_write_album_img4)
    ImageView groupBoardWriteAlbumImg4;
    Unbinder unbinder;

    public static GroupBoardWriteAlbumFragment getinstance() {
        if (curr == null) {
            curr = new GroupBoardWriteAlbumFragment();
        }

        return curr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_board_write_album, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
