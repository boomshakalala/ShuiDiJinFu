package tech.shuidikeji.shuidijinfu.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import tech.shuidikeji.shuidijinfu.R;
import tech.shuidikeji.shuidijinfu.image.ImageManager;
import tech.shuidikeji.shuidijinfu.pojo.AuthSection;


public class AuthenticationAdapter extends BaseSectionQuickAdapter<AuthSection, BaseViewHolder> {

    public AuthenticationAdapter() {
        super(R.layout.item_auth_list, R.layout.item_auth_header, null);
    }


    @Override
    protected void convertHead(BaseViewHolder helper, AuthSection item) {
        switch (item.header){
            case "必测项目":
                helper.setGone(R.id.v_divider,false);
                helper.setGone(R.id.tv_refresh_header,true);
                break;
            case "选测项目":
                helper.setGone(R.id.v_divider,true);
                helper.setGone(R.id.tv_refresh_header,false);
                break;
        }
        helper.setText(R.id.tv_project_type,item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuthSection item) {
        ImageView imageView = helper.getView(R.id.iv_auth);
        helper.setText(R.id.tv_auth_name,item.t.getName());
        if (TextUtils.isEmpty(item.t.getValid_date())){
            helper.setGone(R.id.tv_auth_desc,false);
        }else {
            helper.setGone(R.id.tv_auth_desc,true);
            helper.setText(R.id.tv_auth_desc,item.t.getValid_date());
        }
        if (item.t.getValid_status() == 20){
            ImageManager.getInstance().loadNet(item.t.getSuccess_img_url(),imageView);
            helper.setTextColor(R.id.tv_auth_status,mContext.getResources().getColor(R.color.color_text_light));
            helper.setBackgroundRes(R.id.tv_auth_status,R.drawable.bg_auth_status_success);
            helper.setText(R.id.tv_auth_status,"已认证");
        }else {
            ImageManager.getInstance().loadNet(item.t.getFailure_img_url(),imageView);
            helper.setTextColor(R.id.tv_auth_status,mContext.getResources().getColor(R.color.color_text_gray));
            helper.setBackgroundRes(R.id.tv_auth_status,R.drawable.bg_auth_status_failure);
            switch (item.t.getValid_status()){
                case 10:
                    helper.setText(R.id.tv_auth_status,"去认证");
                    break;
                case 11:
                    helper.setText(R.id.tv_auth_status,"认证中");
                    break;
                case 30:
                    helper.setText(R.id.tv_auth_status,"认证驳回");
                    break;
                case 40:
                    helper.setText(R.id.tv_auth_status,"认证失败");
                    break;
            }
        }
    }
}
