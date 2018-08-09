package teamproject.com.clubapplication;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MenuController {
    private static MenuController menuController;
    public static MenuController getInstance() {
        if(menuController==null) {
            menuController = new MenuController();
        }
        return menuController;
    }
    private MenuController(){/*empty*/}
    public static void addMenu(FragmentManager fragmentManager, int containLayoutId) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(containLayoutId, MenuFragment.getInstance());
        ft.commit();
    }
}
