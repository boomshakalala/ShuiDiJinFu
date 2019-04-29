package tech.shuidikeji.shuidijinfu.utils;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;

/**
 * 监听软键盘的弹起
 */
public class KeyboardUtils {

    public  static final void setKeyboardListener(Context context, final OnKeyboardVisibilityListener listener) {
        final View activityRootView = ((ViewGroup) ((Activity) context)
                .findViewById(android.R.id.content)).getChildAt(0);
        activityRootView.getViewTreeObserver()  .addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    private boolean wasOpened;
                    private final int DefaultKeyboardDP = 100;
                    private final int EstimatedKeyboardDP = DefaultKeyboardDP
                            + (Build.VERSION.SDK_INT >= 21 ? 48
                            : 0);
                    private final Rect r = new Rect();

                    @Override
                    public void onGlobalLayout() {
                        int estimatedKeyboardHeight = (int) TypedValue
                                .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                        EstimatedKeyboardDP, activityRootView
                                                .getResources()
                                                .getDisplayMetrics());
                        activityRootView.getWindowVisibleDisplayFrame(r);
                        int heightDiff = activityRootView.getRootView()
                                .getHeight() - (r.bottom - r.top);
                        boolean isShown = heightDiff >= estimatedKeyboardHeight;

                        if (isShown == wasOpened) {
                            return;
                        }
                        wasOpened = isShown;
                        listener.onVisibilityChanged(isShown);
                    }
                });
    }

    public static void hide(Context context, View v){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }


    /**
     * 监听软键盘的弹起
     */
    public interface OnKeyboardVisibilityListener {
        void onVisibilityChanged(boolean visible);
    }
}

