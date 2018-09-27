package teamproject.com.clubapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import teamproject.com.clubapplication.GroupPostModifyActivity;
import teamproject.com.clubapplication.R;
import teamproject.com.clubapplication.data.ExternalImage;
import teamproject.com.clubapplication.utils.CommonUtils;
import teamproject.com.clubapplication.utils.glide.GlideApp;

public class ModifyPostRecyclerviewAdapter extends RecyclerView.Adapter<ModifyPostRecyclerviewAdapter.Holder> {

    ArrayList<ExternalImage> externalImages;
    Context context;
    ArrayList<Integer> deleteList;

    public ArrayList<String> getDeleteList() {
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0 ; i < deleteList.size(); ++i) {
            result.add(externalImages.get(deleteList.get(i)).getRealPath());
        }
        return result;
    }

    public ModifyPostRecyclerviewAdapter(Context context, ArrayList<ExternalImage> externalImages) {
        this.context = context;
        this.externalImages = externalImages;
        deleteList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ModifyPostRecyclerviewAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_write_post, parent, false);
        ModifyPostRecyclerviewAdapter.Holder holder = new ModifyPostRecyclerviewAdapter.Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ModifyPostRecyclerviewAdapter.Holder holder, int position) {
        if(externalImages.get(position).getFileUri()!=null) {
            GlideApp.with(context).load(externalImages.get(position).getFileUri()).centerCrop().skipMemoryCache(true).into(holder.imageView);
        } else {
            GlideApp.with(context).load(CommonUtils.serverURL+CommonUtils.attachPath+externalImages.get(position).getRealPath()).centerCrop().skipMemoryCache(true).into(holder.imageView);
        }

        if(deleteList.contains(position)){
            holder.view.setVisibility(View.VISIBLE);
        } else {
            holder.view.setVisibility(View.GONE);
        }

        final int p = position;
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(externalImages.get(p).getFileUri()!=null) {
                    externalImages.remove(p);
                    ((GroupPostModifyActivity)context).removeNew(p);
                } else {
                    if(deleteList.contains(p)){
                        int index = deleteList.indexOf(p);
                        deleteList.remove(index);
                        holder.view.setVisibility(View.GONE);
                    } else {
                        deleteList.add(p);
                        holder.view.setVisibility(View.VISIBLE);
                    }
                }
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
        @BindView(R.id.writeBoard_rv_v_del)
        View view;

        public Holder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
