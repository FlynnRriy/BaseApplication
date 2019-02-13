package com.baseapplication.utils;

import android.content.Context;
import android.widget.Toast;
import com.baseapplication.app.MyApp;

//小米5上会有问题
public class ToastUtil {

    private static Toast toast;

    public static void ShowShortToast(String text){
        if (toast == null) {
            toast=Toast.makeText(MyApp.getInstance(),null,Toast.LENGTH_SHORT);
            toast.setText(text);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            toast.setText(text);
        }
        toast.show();
    }
    public static void ShowShortToast(Context context, String text){
        if (toast == null) {
            toast=Toast.makeText(context,null,Toast.LENGTH_SHORT);
            toast.setText(text);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            toast.setText(text);
        }
        toast.show();
    }
}