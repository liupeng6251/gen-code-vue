package ${classInfo.packagePath}.core.page;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
* Created by liupeng6251@163.com on '${.now?string('yyyy-MM-dd HH:mm:ss')}'.
 */

public class PageRequest implements Serializable {

    private Page page = new Page();


    private int start;
    private int length;
    private int searchType = 1;
    private int draw = 1;

    private String orderByClause;

    private boolean distinct;

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public Page getPage() {
        return new Page((start + length) / length, length,searchType,draw);

    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @JSONField(serialize = false)
    public int getOffset() {
        return start;
    }

    @JSONField(serialize = false)
    public int getLimit() {
        return length;
    }

    public static class Page {
        private static final long serialVersionUID = -9116229816861557536L;
        int pageSize;
        int current;
        int searchType;
        int draw;

        public Page() {
            this.pageSize = 15;
            this.current = 1;
        }

        public Page(int size, int current, int searchType, int draw) {
            this.pageSize = size;
            this.current = current;
            this.searchType = searchType;
            this.draw = draw;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Page page = (Page) o;

            if (pageSize != page.pageSize) return false;
            return current == page.current;
        }

        @Override
        public int hashCode() {
            int result = pageSize;
            result = 31 * result + current;
            return result;
        }
    }
}
