package teamproject.com.clubapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.glide.GlideApp;

public class GroupBoardImagePagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> imgList;

    public GroupBoardImagePagerAdapter(Context context, ArrayList<String> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.viewpager_image, container,
                false);

        ImageView imageView = (ImageView) viewLayout.findViewById(R.id.viewpager_imgV);

        GlideApp.with(context).load(CommonUtils.serverURL+CommonUtils.attachPath+imgList.get(position)).skipMemoryCache(true).into(imageView);
        ((ViewPager) container).addView(viewLayout);
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }
    @Override
    public float getPageWidth(int position) {
        return (0.8f);
//        return super.getPageWidth(position);
    }
}
