package cn.redsa.miraisp.entity;

import lombok.Setter;

@Setter
@Deprecated
public class LoginObject {
    // 0初始 1滑动 2安全 3成功
    private int status = 0;
    // 滑动的解决方案
    private String slideResult;
    // 滑动的网址
    private String slideUrl;
    // 安全的解决方案
    private String safeResult;

    public int getStatus() {
        return this.status;
    }

    public String getSlideResult() {
        String dd = this.slideResult+"";
        this.slideResult = null;
        return dd;
    }

    public String getSlideUrl() {
        return this.slideUrl;
    }

    public String getSafeResult() {
        String dd = this.safeResult+"";
        this.safeResult = null;
        return dd;
    }
}
