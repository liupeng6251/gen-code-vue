package ${classInfo.packagePath}.core.page;

import java.io.Serializable;
import java.util.List;
/**
 *
* Created by liupeng6251@163.com on '${.now?string('yyyy-MM-dd HH:mm:ss')}'.
 */

public class Pager<T> implements Serializable {
    private int page;
    private int total;
    private List<T> rows;
    private Long records;
    private int draw;
    private int searchType = 1;

    private Pager() {

    }
    public static <T> Pager.Builder<T> builder(List<T> data) {
        return new Pager.Builder().data(data);
    }

    public Pager(int page, int total, List<T> rows, Long records) {
        this.page = page;
        this.total = total;
        this.rows = rows;
        this.records = records;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public static class Builder<T> {
        private List<T> data;
        private PageRequest.Page page;
        private long totalSize;

        private Builder() {
            this.totalSize = 0;
        }

        public Pager.Builder<T> current(PageRequest.Page page) {
            this.page = page;
            return this;
        }

        public Pager.Builder<T> total(long totalSize) {
            this.totalSize = totalSize;
            return this;
        }

        public Pager.Builder<T> data(List<T> data) {
            this.data = data;
            return this;
        }

        public Pager<T> create() {
            int pageSize = page.getPageSize();
            Pager<T> pager = new  Pager<T>(page.getCurrent(), (int) (totalSize + pageSize - 1) / pageSize, data, totalSize);
            pager.setDraw(page.draw+1);
            pager.setSearchType(page.searchType);
            return pager;
        }
    }
}