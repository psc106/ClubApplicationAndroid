package teamproject.com.clubapplication.test;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.ListView;

import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.utils.CommonUtils;

public class testBehavior extends CoordinatorLayout.Behavior<View> {
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();


    int mDySinceDirectionChange;
    private boolean mIsShowing;
    private boolean mIsHiding;

    public testBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {

        return axes == ViewCompat.SCROLL_AXIS_HORIZONTAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

        View views = coordinatorLayout.findViewById(R.id.inner);

        if (mDySinceDirectionChange < 0) {
            mDySinceDirectionChange = 0;
            return;
        }
        if (mDySinceDirectionChange > ((RecyclerView) target).computeHorizontalScrollRange()) {
            mDySinceDirectionChange = ((RecyclerView) target).computeHorizontalScrollRange();
            return;
        }

        mDySinceDirectionChange += dx;

        if (mDySinceDirectionChange > 200
                && views.getVisibility() == View.VISIBLE
                && !mIsHiding) {
            hideView(views);
        } else if (mDySinceDirectionChange <= 200
                && views.getVisibility() == View.GONE
                && !mIsShowing) {
            showView(views);
        }

    }

    private void hideView(final View view) {
        mIsHiding = true;
        ViewPropertyAnimator animator = view.animate().translationY(view.getHeight()).setInterpolator(INTERPOLATOR).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsHiding = false;
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) { // 취소되면 다시 보여줌
                mIsHiding = false;
                if (!mIsShowing) {
                    showView(view);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animator.start();
    }

    /**
     * View를 보여준다. * <p/> * 아래서 위로 슬라이딩 애니메이션. * 애니메이션을 시작하기전 View를 보여준다. * * @param view The quick return view
     */
    private void showView(final View view) {
        mIsShowing = true;
        ViewPropertyAnimator animator = view.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsShowing = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) { // 취소되면 다시 숨김
                mIsShowing = false;
                if (!mIsHiding) {
                    hideView(view);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animator.start();
    }

}
