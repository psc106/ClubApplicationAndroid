package teamproject.com.clubapplication;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import teamproject.com.clubapplication.fragment.MenuFragment;

public class DrawerMenu {
    //멤버 변수
    private MenuFragment menuContent;
    private DrawerLayout menuDrawer;
    private static DrawerMenu drawerMenu;

    //static 메소드
    /*
    *   @METHOD DrawerMenu : 생성자
    *   @PARAM  activity : 이 메뉴를 가지는 activity
    *   @PARAM  containerLayoutId : menuFragment를 가지고 있게될 Layout뷰의 id
    *                            이 Layout뷰는 DrawerLayout뷰의 drawerMenu부분에 속한다.
    *   @PARAM  drawerLayoutId : DrawerLayout뷰 의 id
    */
    private DrawerMenu(Activity activity, int containerLayoutId, int drawerLayoutId) {
        setMenu(activity, containerLayoutId, drawerLayoutId);
    }

    /*
    *   @METHOD addMenu : retrun DrawerMenu, 액티비티의 resume단에서 실행되는 메소드.
    *                     activity가 실행(onresume) 될 때, activity에 맞게 설정된 DrawerMenu 객체를 준다.
    *                     이 때 DrawerMenu 객체는 재생성 되는것이 아니라 이전에 만들어진 객체의 내용만 바꿔서 준다.
    *   @PARAM  activity : 이 메뉴를 가지는 activity
    *   @PARAM  containerLayoutId : menuFragment를 가지고 있게될 Layout뷰의 id
    *                            이 Layout뷰는 DrawerLayout뷰의 drawerMenu부분에 속한다.
    *   @PARAM  drawerLayoutId : DrawerLayout뷰 의 id
    * */
    public static DrawerMenu addMenu(Activity activity, int containerLayoutId, int drawerLayoutId) {
        if(drawerMenu == null) {
            new DrawerMenu(activity, containerLayoutId, drawerLayoutId);
        } else {
            drawerMenu.setMenu(activity, containerLayoutId, drawerLayoutId);
        }
        return drawerMenu;
    }

    //외부서 사용 가능한 메소드
    /*
     *   @METHOD getMenuDrawer : retrun DrawerLayout, DrawerMenu에 저장되있는 DrawerLayout을 get하는 메소드.
     * */
    public DrawerLayout getMenuDrawer() {
        return menuDrawer;
    }

    /*
     *   @METHOD getMenuContent : retrun MenuFragment, DrawerMenu에 저장되있는 MenuFragment를 get하는 메소드.
     * */
    public MenuFragment getMenuContent() {
        return menuContent;
    }

    /*
     *   @METHOD restartMenu : 액티비티의 resume단에서 실행되는 메소드.
     *                          이미 DrawerMenu가 할당되 있을 경우에 실행된다.
     * */
    public void restartMenu(Activity activity, int containerLayoutId, int drawerLayoutId) {
        setMenu(activity, containerLayoutId, drawerLayoutId);
    }

    //내부 메소드
    /*
     *   @METHOD setMenu : 현재 화면에 뛰워진 activity에 맞게 DrawerMenu의 내용을 바꾼다.
     * */
    private void setMenu(Activity activity, int containerLayoutId, int drawerLayoutId) {
        DrawerLayout drawerLayout = activity.findViewById(drawerLayoutId);
        menuDrawer = drawerLayout;
        menuContent = new MenuFragment(this);

        FragmentTransaction ft = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        ft.replace(containerLayoutId, menuContent);
        ft.commit();
    }
}
