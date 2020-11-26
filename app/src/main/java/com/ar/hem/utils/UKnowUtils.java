package com.ar.hem.utils;

import android.widget.Button;
import android.widget.EditText;

public class UKnowUtils {

    public static void disableButtonView(Button button) {
        button.setEnabled(false);
        button.setClickable(false);
        button.setAlpha(0.5f);
    }

    public static void enableButtonView(Button button) {
        button.setEnabled(true);
        button.setClickable(true);
        button.setAlpha(1f);
    }

    public static boolean isNullOrEmpty(EditText editText) {
        if (editText == null || editText.getText( ).toString( ).length( ) == 0)
            return true;
        return false;
    }
}
