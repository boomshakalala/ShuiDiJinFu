package tech.shuidikeji.shuidijinfu.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.utils.ScreenUtils;


public class ProgressDialog {
    private static Context mContext;
    private Dialog mDialog;
    private View mDialogView;
    private TextView mMessageTv;

    private Builder mBuilder;

    public ProgressDialog(Builder builder){
        mBuilder = builder;
        mDialog = new Dialog(mContext, R.style.ProgressDialogTheme);
        mDialogView = View.inflate(mContext,R.layout.dialog_progress,null);
        mMessageTv = mDialogView.findViewById(R.id.tv_progress_message);
        mDialogView.setMinimumHeight((int) (ScreenUtils.getScreenWidth(mContext) * mBuilder.getHeight()));
        mDialogView.setMinimumWidth((int) (ScreenUtils.getScreenWidth(mContext) * mBuilder.getWidth()));
        mDialog.setContentView(mDialogView);
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = (int) (ScreenUtils.getScreenWidth(mContext) * mBuilder.getWidth());
        layoutParams.height = (int) (ScreenUtils.getScreenWidth(mContext) * mBuilder.getHeight());
        layoutParams.gravity = Gravity.CENTER;
        mDialog.setCancelable(mBuilder.isCancelable());
        mDialog.setCanceledOnTouchOutside(false);
        mMessageTv.setText(mBuilder.getMessage());
    }



    public void show(){
        mDialog.show();
    }

    public void dismiss(){
        mDialog.dismiss();
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener){
        mDialog.setOnDismissListener(listener);
    }

    public boolean isShowing(){
        return mDialog.isShowing();
    }

    public static class Builder {

        private final String DEFAULT_LABEL = "请稍后...";
        private final float DEFAULT_WIDTH = 0.26f;

        private String mMessage;
        private boolean mCancelable;
        private float height;
        private float width;

        public Builder(Context context){
            mContext = context;
            mMessage = DEFAULT_LABEL;
            mCancelable = false;
            height = DEFAULT_WIDTH;
            width = DEFAULT_WIDTH;
        }

        public String getMessage() {
            return mMessage;
        }

        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        public boolean isCancelable() {
            return mCancelable;
        }

        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public float getHeight() {
            return height;
        }

        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }

        public float getWidth() {
            return width;
        }

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        public ProgressDialog build(){
            return new ProgressDialog(this);
        }



    }





}
