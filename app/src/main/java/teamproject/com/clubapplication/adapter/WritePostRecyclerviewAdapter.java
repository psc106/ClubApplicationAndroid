package teamproject.com.clubapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import teamproject.com.clubapplication.test.TestAdapter;
import teamproject.com.clubapplication.utils.customView.XYFitImageView;
import teamproject.com.clubapplication.utils.glide.GlideApp;

public class WritePostRecyclerviewAdapter extends RecyclerView.Adapter<WritePostRecyclerviewAdapter.Holder> {

    ArrayList<ExternalImage> externalImages;
    Context context;

    public WritePostRecyclerviewAdapter(Context context, ArrayList<ExternalImage> externalImages) {
        this.context = context;
        this.externalImages = externalImages;
    }

    @NonNull
    @Override
    public WritePostRecyclerviewAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_write_post, parent, false);
        WritePostRecyclerviewAdapter.Holder holder = new WritePostRecyclerviewAdapter.Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WritePostRecyclerviewAdapter.Holder holder, int position) {
        GlideApp.with(context).load(externalImages.get(position).getFileUri()).centerCrop().skipMemoryCache(true).into(holder.imageView);

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

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.writeBoard_rv_btn_del)
        Button btn;
        @BindView(R.id.writeBoard_rv_img)
        ImageView imageView;

        public Holder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
