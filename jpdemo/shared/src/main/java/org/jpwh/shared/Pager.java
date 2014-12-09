package org.jpwh.shared;

import java.io.Serializable;

public class Pager implements Serializable {

    private int pageSize = 15;
    private int page = 1;
    private long numOfRecords = 0l;

    public Pager() {
    }

    public Pager(int pageSize) {
        this.pageSize = pageSize;
    }

    public Pager(int pageSize, int page) {
        this.pageSize = pageSize;
        this.page = page;
    }

    public Pager(int pageSize, long numOfRecords) {
        this.pageSize = pageSize;
        this.numOfRecords = numOfRecords;
    }

    public Pager(int pageSize, int page, long numOfRecords) {
        this.pageSize = pageSize;
        this.page = page;
        this.numOfRecords = numOfRecords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page == 0)
            page = getFirstPage();
        this.page = page;
    }

    public long getNumOfRecords() {
        return numOfRecords;
    }

    public void setNumOfRecords(long numOfRecords) {
        this.numOfRecords = numOfRecords;
    }

    public int getNextPage() {
        return page + 1;
    }

    public int getPreviousPage() {
        return page - 1;
    }

    public int getFirstPage() {
        return 1;
    }

    public long getLastPage() {
        long lastPage = (numOfRecords / pageSize);
        if (numOfRecords % pageSize == 0) lastPage--;
        return lastPage + 1;
    }

    public long getIndexBegin() {
        return (getPage() - 1) * getPageSize();
    }

    public long getIndexEnd() {
        long firstIndex = getIndexBegin();
        long pageIndex = getPageSize() - 1;
        long lastIndex = getNumOfRecords() - 1;
        return Math.min(firstIndex + pageIndex, lastIndex);
    }

    public boolean isPreviousPageAvailable() {
        return getIndexBegin() + 1 > getPageSize();
    }

    public boolean isNextPageAvailable() {
        return numOfRecords - 1 > getIndexEnd();
    }

    public boolean isSeveralPages() {
        return getNumOfRecords() != 0 && getNumOfRecords() > getPageSize();
    }

    public String toString() {
        return "Pager - Records: " + getNumOfRecords() + ", Page size: " + getPageSize() + ", Page: " + getPage() + ", Index Range Begin: " + getIndexBegin();
    }

    /* Some quick and dirty tests
    public static void main(String[] args) {
        Pager pager = new Pager(3);
        pager.setNumOfRecords(5);

        assert pager.getNumOfRecords() == 5;

        assert pager.getPage() == 1;
        assert pager.isNextPageAvailable();
        assert !pager.isPreviousPageAvailable();
        assert pager.getNextPage() == 2;
        assert pager.getIndexBegin() == 0;
        assert pager.getIndexEnd() == 2;

        pager.setPage(pager.getNextPage());
        assert !pager.isNextPageAvailable();
        assert pager.isPreviousPageAvailable();
        assert pager.getPreviousPage() == 1;
        assert pager.getIndexBegin() == 3;
        assert pager.getIndexEnd() == 4;

    }
    */
}