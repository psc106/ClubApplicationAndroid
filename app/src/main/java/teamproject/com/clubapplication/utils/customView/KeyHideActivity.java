package teamproject.com.clubapplication.utils.customView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyHideActivity extends AppCompatActivity {

    float firstX;
    float firstY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            firstY = ev.getY();
            firstX = ev.getX();
        }
        if(ev.getAction() == MotionEvent.ACTION_UP) {
            float lastY = ev.getY();
            float lastX = ev.getX();
            if(Math.abs(firstY-lastY)>70 && Math.abs(firstY-lastY)>Math.abs(firstX-lastX)+10){
                return super.dispatchTouchEvent(ev);
            } else {
                final View view = getCurrentFocus();

                if (view != null) {
                    final boolean consumed = super.dispatchTouchEvent(ev);

                    final View viewTmp = getCurrentFocus();
                    final View viewNew = viewTmp != null ? viewTmp : view;

                    if (viewNew.equals(view)) {
                        final Rect rect = new Rect();
                        final int[] coordinates = new int[2];

                        view.getLocationOnScreen(coordinates);

                        rect.set(coordinates[0], coordinates[1], coordinates[0] + view.getWidth(), coordinates[1] + view.getHeight());

                        final int x = (int) ev.getX();
                        final int y = (int) ev.getY();

                        if (rect.contains(x, y)) {
                            return consumed;
                        }
                    } else if (viewNew instanceof EditText) {
                        return consumed;
                    }

                    final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputMethodManager.hideSoftInputFromWindow(viewNew.getWindowToken(), 0);

                    viewNew.clearFocus();
                    viewNew.requestFocus();

                    return consumed;
                }
            }
        }


        return super.dispatchTouchEvent(ev);
    }



}
