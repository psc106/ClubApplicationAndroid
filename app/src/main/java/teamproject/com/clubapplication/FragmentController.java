package teamproject.com.clubapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import teamproject.com.clubapplication.fragment.MenuFragment;

public class FragmentController {
    private static FragmentController fragmentController;
    public static FragmentController getInstance() {
        if(fragmentController==null) {
            fragmentController = new FragmentController();
        }
        return fragmentController;
    }
    private FragmentController(){/*empty*/}

    public static void replaceFragment(FragmentManager fragmentManager, int replaceLayoutId) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(replaceLayoutId, new MenuFragment());
        ft.commit();
    }

}
