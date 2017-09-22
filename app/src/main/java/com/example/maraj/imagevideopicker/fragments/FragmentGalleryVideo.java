package com.example.maraj.imagevideopicker.fragments;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.example.maraj.imagevideopicker.model.VideoViewInfo;
import com.example.maraj.imagevideopicker.adapter.LoadGalleryVideoAdapter;

import java.util.ArrayList;


/**
 * Created by igniva-android-17 on 8/9/17.
 */

public class FragmentGalleryVideo extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = "NewProfilePersonal";

    private View view;
    private Cursor cursor;
    private ProgressDialog mProgressDialog;
    private LoadGalleryVideoAdapter mloadGalleryVideoAdapter;
    private ArrayList<VideoViewInfo> mAlVideoList = new ArrayList<>();

    RecyclerView mRvGalleryImages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_custom_gallery, container, false);
        }
        mRvGalleryImages = (RecyclerView) view.findViewById(R.id.rv_gallery_images);
        GetVideosFromSdCard getImagesFromSdCard = new GetVideosFromSdCard();
        getImagesFromSdCard.execute();

        return view;
    }

    private void initXml() {
        try {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Log.d("UI thread", "I am the UI thread");

                    DisplayMetrics displaymetrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                    int height = displaymetrics.heightPixels;
                    int width = displaymetrics.widthPixels;

                    mloadGalleryVideoAdapter = new LoadGalleryVideoAdapter(getActivity(), getAllShownVideos(), width, cursor);
                    GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 4);
                    mRvGalleryImages.setLayoutManager(mGridLayoutManager);
                    mRvGalleryImages.setAdapter(mloadGalleryVideoAdapter);
                    mProgressDialog = new ProgressDialog(getActivity());
                    mProgressDialog.setMessage("Loading Videos ");
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetVideosFromSdCard extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            Utility.showProgressDialog(getActivity());
            initXml();
            Log.e(LOG_TAG, " : - onPreExecute");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.e(LOG_TAG, " : -  doInBackground");
                getAllShownVideos();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Log.e("progress started", "onPostExecute");
                mloadGalleryVideoAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
            Utility.closeProgressDialog(getActivity());
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    @Override
    public void onClick(View v) {

    }

    public ArrayList<VideoViewInfo> getAllShownVideos() {

        try {
            String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA,
                    MediaStore.Video.Thumbnails.VIDEO_ID};
//            String[] duration={MediaStore.Video.VideoColumns.DURATION};

            String[] mediaColumns = {MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.MIME_TYPE};

            cursor = getActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);

            if (cursor.moveToFirst()) {
                do {

                    VideoViewInfo mVideoViewInfo = new VideoViewInfo();
                    int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                    Cursor thumbCursor = getActivity().getContentResolver().query(
                            MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                            thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
                                    + "=" + id, null, null);
                    if (thumbCursor.moveToFirst()) {
                        mVideoViewInfo.thumbPath = thumbCursor.getString(thumbCursor
                                .getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                        Log.v("videothumba", mVideoViewInfo.thumbPath);
                    }
                    try {
                        mVideoViewInfo.duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                        Log.v("duration", mVideoViewInfo.duration);
                        mVideoViewInfo.filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                        mVideoViewInfo.mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                        mAlVideoList.add(mVideoViewInfo);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }
//            cursor.close();
        } catch (IllegalArgumentException e) {
            cursor.close();
            e.printStackTrace();
        }
        return mAlVideoList;
    }

}
