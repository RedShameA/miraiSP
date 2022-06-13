package cn.redsa.miraisp.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageBean<T> {
    /** 每页大小 */
    private Long pageSize = 10L;
    /** 当前页为第几页 */
    private Long pageNo = 1L;
    /** 总共有多少页 */
    private Long totalPages = 0L;
    /** 总共有多少条数据 */
    private Long totalCount = 0L;
    /** 数据 */
    private List<T> pageData = new ArrayList();

    public PageBean(Long totalCount, List<T> pageData) {
        this.totalCount = totalCount;
        this.pageData = pageData;
    }
}
