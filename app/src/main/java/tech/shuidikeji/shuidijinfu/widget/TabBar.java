package tech.shuidikeji.shuidijinfu.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.image.ImageManager;

public class TabBar extends LinearLayout implements View.OnClickListener{
    private List<View> mTabViews;//保存TabView
    private List<Tab> mTabs;// 保存Tab
    private OnTabCheckListener mOnTabCheckListener;

    public TabBar setOnTabCheckListener(OnTabCheckListener onTabCheckListener) {
        mOnTabCheckListener = onTabCheckListener;
        return this;
    }

    public TabBar(Context context) {
        super(context);
        init();
    }

    public TabBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
}

    private void init(){
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
        mTabViews = new ArrayList<>();
        mTabs = new ArrayList<>();
    }

    /**
     * 添加Tab
     * @param tab
     */
    public TabBar addTab(Tab tab){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_tab_indicator,null);
        TextView textView = view.findViewById(R.id.custom_tab_text);
        ImageView imageView = view.findViewById(R.id.custom_tab_icon);
        if (!TextUtils.isEmpty(tab.mIconNormalResId))
            ImageManager.getInstance().loadNet(tab.mIconNormalResId,imageView);
        if (!TextUtils.isEmpty(tab.mText))
            textView.setText(tab.mText);
        if (tab.mNormalColor != -1)
            textView.setTextColor(tab.mNormalColor);

        view.setTag(mTabViews.size());
        view.setOnClickListener(this);

        mTabViews.add(view);
        mTabs.add(tab);

        addView(view);

        return this;

    }

    /**
     * 设置选中Tab
     * @param position
     */
    public TabBar setCurrentItem(int position){
        if(position>=mTabs.size() || position<0){
            position = 0;
        }

        mTabViews.get(position).performClick();

        updateState(position);

        return this;


    }

    /**
     * 更新状态
     * @param position
     */
    public void updateState(int position){
        for(int i= 0;i<mTabViews.size();i++){
            View view = mTabViews.get(i);
            TextView textView = (TextView) view.findViewById(R.id.custom_tab_text);
            ImageView imageView = (ImageView) view.findViewById(R.id.custom_tab_icon);
            if(i == position){
                if (!TextUtils.isEmpty(mTabs.get(i).mIconPressedResId) )
                    ImageManager.getInstance().loadNet(mTabs.get(i).mIconPressedResId,imageView);
                if (mTabs.get(i).mSelectColor != -1)
                    textView.setTextColor(mTabs.get(i).mSelectColor);
            }else{
                if (!TextUtils.isEmpty(mTabs.get(i).mIconNormalResId))
                    ImageManager.getInstance().loadNet(mTabs.get(i).mIconNormalResId,imageView);
                if (mTabs.get(i).mSelectColor != -1)
                    textView.setTextColor(mTabs.get(i).mNormalColor);
            }
        }
    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        updateState(position);
        if(mOnTabCheckListener!=null){
            mOnTabCheckListener.onTabSelected(v, position);
        }

    }

    public interface  OnTabCheckListener{
         void onTabSelected(View v, int position);
    }


    public static class Tab{
        private String mIconNormalResId;
        private String mIconPressedResId;
        private int mNormalColor;
        private int mSelectColor;
        private String mText;


        public Tab setText(String text){
            mText = text;
            return this;
        }

        public Tab setNormalIcon(String res){
            mIconNormalResId = res;
            return this;
        }

        public Tab setPressedIcon(String res){
            mIconPressedResId = res;
            return this;
        }

        public Tab setColor(int color){
            mNormalColor = color;
            return this;
        }

        public Tab setCheckedColor(int color){
            mSelectColor = color;
            return this;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mTabViews!=null){
            mTabViews.clear();
        }
        if(mTabs!=null){
            mTabs.clear();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 调整每个Tab的大小
        for(int i=0;i<mTabViews.size();i++){
            View view = mTabViews.get(i);
            int width = getResources().getDisplayMetrics().widthPixels / (mTabs.size());
            LayoutParams params = new LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);

            view.setLayoutParams(params);
        }

    }
}