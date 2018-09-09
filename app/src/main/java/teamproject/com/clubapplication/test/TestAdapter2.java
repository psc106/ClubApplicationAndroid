package teamproject.com.clubapplication.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.ExternalImage;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.glide.GlideApp;

public class TestAdapter2 extends RecyclerView.Adapter<TestAdapter2.Holder> {

    private String serverURL = CommonUtils.serverURL;
    ArrayList<String> filePath;
    Context context;

    public TestAdapter2(Context context, ArrayList<String> filePath) {
        this.context = context;
        this.filePath = filePath;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_test2, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        GlideApp.with(context).load(serverURL+filePath.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return filePath.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.test_listV_img_Get)
        ImageView imageView;
        public Holder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
