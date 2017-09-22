package com.example.maraj.imagevideopicker.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by maraj on 22/9/17.
 */

public class Utility {

    //Time In Miliseconds
    public static long MAX_SECOND = 121000;
    public static ProgressDialog mProgressDialog;

    public static void showProgressDialog(Activity mContext) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(mContext, "", "Loading Images...");

        }
    }


    public static void closeProgressDialog(Context mContext) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
            mProgressDialog.dismiss();
            mProgressDialog = null;
        } else {
            Toast.makeText(mContext, "closing dialog error", Toast.LENGTH_SHORT).show();
        }
    }
}
