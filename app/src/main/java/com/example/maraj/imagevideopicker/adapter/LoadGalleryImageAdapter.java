package com.example.maraj.imagevideopicker.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maraj.imagevideopicker.R;

import java.util.ArrayList;

/**
 * Created by maraj
 */

public class LoadGalleryImageAdapter extends RecyclerView.Adapter<LoadGalleryImageAdapter.MyViewHolder> {
    LayoutInflater inflator;

    ArrayList<Bitmap> mAlThumbnails;
    ArrayList<String> mAlGalleryImages;
    Context context;
    int width;

    public LoadGalleryImageAdapter(Context context, ArrayList<String> mAlGalleryImages,ArrayList<Bitmap> mAlThumbnails,int width) {
        inflator = LayoutInflater.from(context);
        this.context = context;
        this.mAlThumbnails = mAlThumbnails;
        this.width = width;
        this.mAlGalleryImages = mAlGalleryImages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = inflator.inflate(R.layout.gallery_image_display, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, viewType);
        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        try {
//            Log.e("session", mAlThumbnails.get(position) + "");
            Log.e("session", mAlGalleryImages.get(position) + "");

            Glide.with(context)
                    .asBitmap()
                    .load(mAlGalleryImages.get(position))
                    .into(holder.mIvGalleryImage);
            holder.mIvGalleryImage.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"PATH : - "+mAlGalleryImages.get(position), Toast.LENGTH_SHORT).show();
                    Log.e("PATH "," IMAGE :- "+mAlGalleryImages.get(position));
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mAlGalleryImages.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mIvGalleryImage;

        public MyViewHolder(View itemView, int viewtype) {
            super(itemView);
            mIvGalleryImage = (ImageView)itemView.findViewById(R.id.img_click);
//            mIvGalleryImage=(ImageView)itemView.findViewById(R.id.img_click);
            mIvGalleryImage.getLayoutParams().height = width / 4;

        }
    }
}