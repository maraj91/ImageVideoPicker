package com.example.maraj.imagevideopicker.adapter;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maraj.imagevideopicker.R;
import com.example.maraj.imagevideopicker.model.VideoViewInfo;
import com.example.maraj.imagevideopicker.utility.Utility;

import java.util.ArrayList;

public class LoadGalleryVideoAdapter extends RecyclerView.Adapter<LoadGalleryVideoAdapter.MyViewHolder> {
    private LayoutInflater inflator;
    ArrayList<VideoViewInfo> mAlVideoList;
    Context context;
    Cursor cursor;
    int width;

    public LoadGalleryVideoAdapter(Context context, ArrayList<VideoViewInfo> mAlVideoList, int width, Cursor cursor) {
        inflator = LayoutInflater.from(context);
        this.context = context;
        this.mAlVideoList = mAlVideoList;
        this.width = width;
        this.cursor = cursor;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = inflator.inflate(R.layout.gallery_video_display, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, viewType);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {
            Glide.with(context).asBitmap()
                    .load((mAlVideoList.get(position).filePath))
                    .into(holder.mIvVideoThumb);
            holder.mIvVideoThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Long.valueOf(mAlVideoList.get(position).duration) < Utility.MAX_SECOND) {
                        Toast.makeText(context,"DURATION : - "+mAlVideoList.get(position).duration+"\n PATH : - "+mAlVideoList.get(position).filePath,Toast.LENGTH_SHORT).show();

                    } else {
                        Log.e("VIDEO_DURATION ", "" + mAlVideoList.get(position).duration);
                        Toast.makeText(context,"Duration not be grater then 120 seconds.",Toast.LENGTH_SHORT).show();

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mAlVideoList.size();

    }

    @Override
    public int getItemViewType(int position) {

        return position;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mIvVideoThumb;
        ImageView mIvPlay;


        public MyViewHolder(View itemView, int viewtype) {
            super(itemView);
                mIvVideoThumb = (ImageView) itemView.findViewById(R.id.img_video_thumb);
                mIvPlay = (ImageView) itemView.findViewById(R.id.img_play);
            try {
                mIvVideoThumb.getLayoutParams().width = width / 4;
                mIvPlay.getLayoutParams().width = width / 4;
                mIvVideoThumb.getLayoutParams().height = width / 4;
                mIvPlay.getLayoutParams().height = width / 4;
                mIvPlay.setPadding(width / 10, width / 10, width / 10, width / 10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}