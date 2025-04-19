package com.example.moviemania.utility;

import android.content.Context;
import android.widget.Toast;

public class Utility {

    private static Toast mToast = null;

    public static void showToast(Context context, String message){
        try {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            mToast.show();
        }
        catch (Exception ignored){}
    }

}
