package com.example.maraj.imagevideopicker.fragments;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maraj.imagevideopicker.R;
import com.example.maraj.imagevideopicker.utility.Utility;
import com.example.maraj.imagevideopicker.adapter.LoadGalleryImageAdapter;

import java.util.ArrayList;


/**
 * Created by maraj
 */

public class FragmentGalleryImages extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = "NewProfilePersonal";
    LoadGalleryImageAdapter mloadGalleryImageAdapter;
    ArrayList<Bitmap> mAlImagesThumbnails = new ArrayList<>();
    ArrayList<String> mALGalleryImagesPath = new ArrayList<>();
    RecyclerView mRvGalleryImages;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_custom_gallery, container, false);
        }
        mRvGalleryImages = (RecyclerView)view.findViewById(R.id.rv_gallery_images);
        GetImagesFromSdCard getImagesFromSdCard = new GetImagesFromSdCard();
        getImagesFromSdCard.execute();
        return view;
    }

    private void initXml() {
        try {

            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;

            mloadGalleryImageAdapter = new LoadGalleryImageAdapter(getActivity(), mALGalleryImagesPath,mAlImagesThumbnails,width);
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 4);
            mRvGalleryImages.setLayoutManager(mGridLayoutManager);
            mRvGalleryImages.setAdapter(mloadGalleryImageAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class GetImagesFromSdCard extends AsyncTask<Void, Void, Void> {
        Boolean toBeupdated;

        @Override
        protected void onPreExecute() {
            Utility.showProgressDialog(getActivity());
            initXml();
            Log.e(LOG_TAG, " : - onPreExecute");

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Log.e(LOG_TAG, " : -  doInBackground");
                getAllShownImagesPath();
//                getthumbnails();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                mloadGalleryImageAdapter.notifyDataSetChanged();
                Log.e(LOG_TAG, " : - onPostExecute");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //closeProgressDialog();
            }
            Utility.closeProgressDialog(getActivity());

        }


    }


    private ArrayList<String> getAllShownImagesPath() {
        Cursor cursor = null;
//        ArrayList<String> mAlListOfImages = null;
        try {
            Uri uri;

            int column_index_data;
//            mAlListOfImages = new ArrayList<String>();
            String absolutePathOfImage = null;
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";
            String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC",
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

            cursor = getActivity().getContentResolver().query(uri, projection, null,
                    null, orderBy);
            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);
                mALGalleryImagesPath.add(absolutePathOfImage);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(LOG_TAG, " SIZE - BITMAP "+mAlImagesThumbnails.size()+" SIZE - FILES "+mALGalleryImagesPath.size());
        return mALGalleryImagesPath;
    }

    @Override
    public void onClick(View v) {

    }
}
