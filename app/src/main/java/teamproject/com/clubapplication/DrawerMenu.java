package teamproject.com.clubapplication;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import teamproject.com.clubapplication.fragment.MenuFragment;

/*
*  사용법:
*  ( ID 명명 규칙 )
*  frame 레이아웃 id는 activity에서 앞에 사용하는 이름+ "_menu"      ex) main_menu
*  drawer 레이아웃 id는 activity에서 앞에 사용하는 이름+ "_drawer"   ex) main_drawer
*
*  1) 메뉴를 사용할 activity의 레이아웃 xml에 아래의 소스를 추가합니다.
*
*      1-1. 레이아웃의 두번째 줄에 아래의 소스를 추가합니다.
*      <?xml version="1.0" encoding="utf-8"?> <!--제일 첫줄-->
*      <android.support.v4.widget.DrawerLayout
*           android:id="drawer레이아웃 id"
*           xmlns:android="http://schemas.android.com/apk/res/android"
*           android:layout_width="match_parent"
*           android:layout_height="match_parent">
*      ...(원본 코드)...
*
*      1-2. 레이아웃의 제일 아래에 아래의 소스를 추가합니다.*
*      ...(원본 코드)...
*       <FrameLayout
*           android:id="frame레이아웃 id"
*           xmlns:android="http://schemas.android.com/apk/res/android"
*           android:layout_width="240dp"
*           android:layout_height="match_parent"
*           android:layout_gravity="left"
*           android:gravity="center">
*       </FrameLayout>
*       </android.support.v4.widget.DrawerLayout>
*
*
*  2) 메뉴를 사용할 activity에 아래의 소스를 추가합니다.
*
*      2-1. 아래의 멤버변수를 추가합니다.
*         private DrawerMenu drawerMenu;
*
*      2-2. onResume메소드에 아래 코드를 추가합니다.
*        ....
*        if (drawerMenu == null) {
*            drawerMenu = DrawerMenu.addMenu(this, R.id."frame레이아웃 id", R.id."drawer레이아웃 id");
*        } else {
*            drawerMenu.restartMenu(this, R.id."frame레이아웃 id", R.id."drawer레이아웃 id");
*        }
*       ....
*
* */

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
