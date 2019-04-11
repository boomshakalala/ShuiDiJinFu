package tech.shuidikeji.shuidijinfu.pojo;

import com.chad.library.adapter.base.entity.SectionEntity;

public class AuthSection extends SectionEntity<AuthListPojo> {
    public AuthSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public AuthSection(AuthListPojo authListPojo) {
        super(authListPojo);
    }
}
