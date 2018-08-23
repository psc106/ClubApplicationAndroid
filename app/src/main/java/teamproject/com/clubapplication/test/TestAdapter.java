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

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.ExternalImage;
import teamproject.com.clubapplication.utils.glide.GlideApp;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.Horder> {

    ArrayList<ExternalImage> externalImages;
    Context context;

    public TestAdapter(Context context, ArrayList<ExternalImage> externalImages) {
        this.context = context;
        this.externalImages = externalImages;
    }

    @NonNull
    @Override
    public Horder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_test, parent, false);
        Horder horder = new Horder(view);
        return horder;
    }

    @Override
    public void onBindViewHolder(@NonNull Horder holder, int position) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), externalImages.get(position).getFileUri());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GlideApp.with(context).load(externalImages.get(position).getFileUri()).into(holder.imageView1);
        if(bitmap!=null) {
            holder.imageView2.setImageBitmap(bitmap);
        }
        final int p = position;
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                externalImages.remove(p);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return externalImages.size();
    }

    public class Horder extends RecyclerView.ViewHolder {
        @BindView(R.id.test_listV_img_Bitmap)
        ImageView imageView1;
        @BindView(R.id.test_listV_img_Uri)
        ImageView imageView2;
        @BindView(R.id.test_listV_btn_Del)
        Button btn;

        public Horder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
