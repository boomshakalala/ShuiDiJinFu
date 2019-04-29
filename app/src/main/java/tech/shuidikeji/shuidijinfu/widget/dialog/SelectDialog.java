package tech.shuidikeji.shuidijinfu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.pojo.BasicInfoConfigPojo;
import tech.shuidikeji.shuidijinfu.utils.ScreenUtils;
import tech.shuidikeji.shuidijinfu.widget.SizeUtils;

/**
 * 作者: chengx
 * 日期: 2016/11/17.
 * 描述:
 */
public class SelectDialog {
    private static Context context;
    private Dialog dialog;
    private View dialogView;
    private TextView title;
    private Button cancelBtn;
    private LinearLayout containerLayout;

    private Builder builder;
    private List<BasicInfoConfigPojo.ItemPojo> data;
    private int selectPosition;

    private SelectDialog(Builder builder){
        this.builder = builder;
        dialog = new Dialog(context, R.style.BottomDialog);
        dialogView = View.inflate(context,R.layout.widget_select_dialog,null);
        dialog.setContentView(dialogView);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenUtils.getScreenWidth(context) * builder.getItemWidth());
        lp.gravity = Gravity.BOTTOM;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.BottomDialog_Animation);
        containerLayout = dialogView.findViewById(R.id.action_dialog_linearlayout);
        cancelBtn = dialogView.findViewById(R.id.action_dialog_botbtn);
        cancelBtn.setText(builder.getCancelButtonText());
        cancelBtn.setOnClickListener(v -> dialog.dismiss());
        dialog.setCanceledOnTouchOutside(builder.isTouchOutside());
    }

    private void loadItem(){
        cancelBtn.setTextColor(builder.getItemTextColor());
        cancelBtn.setTextSize(builder.getItemTextSize());
        LinearLayout.LayoutParams btnlp = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,builder.getItemHeight());
        btnlp.topMargin = 10;
        cancelBtn.setLayoutParams(btnlp);
        for (int i = 0; i < data.size(); i++) {
            TextView button = getButton(data.get(i).getText(), i);
            button.setBackgroundResource(R.drawable
                    .selector_widget_actiondialog_middle);
            containerLayout.addView(button);
        }
    }

    private TextView getButton(String text, int position){
        final TextView button = new Button(context);
        button.setText(text);
        button.setPadding(0,0,0,0);
        button.setTag(position);
        button.setGravity(Gravity.CENTER);
        button.setTextColor(builder.getItemTextColor());
        button.setTextSize(TypedValue.COMPLEX_UNIT_DIP,builder.getItemTextSize());

        button.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, builder.getItemHeight()));
        button.setOnClickListener(v -> {
            if (builder.getOnItemClickListener() != null) {
                selectPosition = Integer.parseInt(button.getTag().toString());
                builder.getOnItemClickListener().onItemClick(button, selectPosition,this);
            }
            dismiss();
        });
        return button;
    }

    public void setDataList(List<BasicInfoConfigPojo.ItemPojo> datas) {
        int count = containerLayout.getChildCount();
        if(count>1){
            containerLayout.removeAllViews();
        }
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        if (count>8){
            lp.height = ScreenUtils.getScreenHeight(dialog.getContext())/2;

        }else {
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        }
        this.data = (datas == null ? new ArrayList<>() : datas);
        loadItem();
    }

    public boolean isShowing() {

        return dialog.isShowing();
    }

    public void show() {

        dialog.show();

    }

    public void dismiss() {

        dialog.dismiss();
    }


    public static class Builder{

        private DialogOnItemClickListener onItemClickListener;
        private int itemHeight;
        private float itemWidth;
        private int itemTextColor;
        private float itemTextSize;

        private String cancelButtonText;
        private boolean isTouchOutside;

        public Builder(Context context){
            SelectDialog.context = context;

            onItemClickListener = null;
            itemHeight = SizeUtils.dp2px(context,40);
            itemWidth = 1f;
            itemTextColor = Color.parseColor("#363636");
            itemTextSize = 12;

            cancelButtonText = "取消";
            isTouchOutside = true;
        }

        public DialogOnItemClickListener getOnItemClickListener() {
            return onItemClickListener;
        }

        public Builder setOnItemClickListener(DialogOnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public int getItemHeight() {
            return itemHeight;
        }

        public Builder setItemHeight(int itemHeight) {
            this.itemHeight = itemHeight;
            return this;
        }

        public float getItemWidth() {
            return itemWidth;
        }

        public Builder setItemWidth(float itemWidth) {
            this.itemWidth = itemWidth;
            return this;
        }

        public int getItemTextColor() {
            return itemTextColor;
        }

        public Builder setItemTextColor(int itemTextColor) {
            this.itemTextColor = itemTextColor;
            return this;
        }

        public float getItemTextSize() {
            return itemTextSize;
        }

        public Builder setItemTextSize(float itemTextSize) {
            this.itemTextSize = itemTextSize;
            return this;
        }

        public String getCancelButtonText() {
            return cancelButtonText;
        }

        public Builder setCancelButtonText(String cancelButtonText) {
            this.cancelButtonText = cancelButtonText;
            return this;
        }

        public boolean isTouchOutside() {
            return isTouchOutside;
        }

        public Builder setIsTouchOutside(boolean isTouchOutside) {
            this.isTouchOutside = isTouchOutside;
            return this;
        }

        public SelectDialog build(){
            return new SelectDialog(this);
        }
    }
}
