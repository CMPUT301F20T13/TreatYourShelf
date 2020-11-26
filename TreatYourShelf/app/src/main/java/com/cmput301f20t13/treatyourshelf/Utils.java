package com.cmput301f20t13.treatyourshelf;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

public class Utils {
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static CircularProgressDrawable circularProgressDrawableFactory(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorSchemeColors(context.getResources().getColor(R.color.colorPrimary, null));
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

    public static String capitalizeString(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
