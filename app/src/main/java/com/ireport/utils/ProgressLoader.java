package com.ireport.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.ireport.R;

/**
 * Created by Manoj on 11/26/2016.
 */

public class ProgressLoader {

    Dialog pDialog;
    public void showProgress(Context context) {
        // TODO Auto-generated method stub
        pDialog = new Dialog(context, android.R.style.Theme_Translucent);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //here we set layout of progress dialog
        pDialog.setContentView(R.layout.activity_indicator_layout);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void DismissProgress(){
        pDialog.dismiss();
    }

}
