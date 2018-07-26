package com.jia.model.page;

/**
 * @Auther: jia
 * @Date: 2018/7/25 10:32
 * @Description: 分页
 */
public class PageQuery {

    private static final int DEFAULT_PAGE_NO = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;

    private Integer startRow;
    private Integer pageSize;

    public static PageQuery createPage(Integer pageNo, Integer pageSize) {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNoAndSize(pageNo, pageSize);
        return pageQuery;
    }

    private void setPageNoAndSize(Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageNo <=0) {
            pageNo = DEFAULT_PAGE_NO;
        }
        if (pageSize == null || pageSize <=0 || pageSize > MAX_PAGE_SIZE) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.startRow = (pageNo - 1) * pageSize;
        this.pageSize = pageSize;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
