package teamproject.com.clubapplication.utils.customView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class XYFitImageView extends ImageView {
    public XYFitImageView(Context context) {
        super(context);
    }

    public XYFitImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XYFitImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec)
    {
        final int height = getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
        setMeasuredDimension(height, height);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh)
    {
        super.onSizeChanged(w, w, oldw, oldh);
    }

}
