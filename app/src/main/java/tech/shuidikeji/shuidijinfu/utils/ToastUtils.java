package tech.shuidikeji.shuidijinfu.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 作者: chengx
 * 日期: 2016/9/28.
 * 描述: taost封装工具类
 */
public class ToastUtils {

    private static Toast toast;

    /**
     * 非阻塞试显示Toast,防止出现连续点击Toast时的显示问题
     * @param context 上下文
     * @param text 内容
     * @param duration 显示时间
     */
    public static void showToast(Context context, CharSequence text, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, text, duration);
        } else {
            toast.setText(text);
            toast.setDuration(duration);
        }
        toast.show();
    }

    /**
     * 显示toast
     * @param context 上下文
     * @param text 内容
     */
    public static void showToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

}
