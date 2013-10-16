package com.base.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 比较复杂的 值对象
 *
 * 这里用到 3 个 内部类: Result, Book, BookCount
 *
 * 这 4 个类 均为 标准的 JavaBean
 *
 * 可以把 3 个 内部类移出去. 这里为了方便, 放在了一个 Java 文件里面
 */
public class JsonExampleVo {

    private int code;
    private String message;
    private Map<String, Object> header; //也可以 用 Object 类型
    private Result result;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Override
    public boolean equals(Object paramObject) {
        return EqualsBuilder.reflectionEquals(this, paramObject);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    //----------------------------------------------  内部类: Result
    public static class Result {
        private int totalCount;
        private int pageNum;
        private int pageSize;
        private List<Book> bookList;
        private List<Map<String, Object>> otherList; //也可以 用 List<Object> 类型
        private List<BookCount> bookCountList;

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }

        @Override
        public boolean equals(Object paramObject) {
            return EqualsBuilder.reflectionEquals(this, paramObject);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        public List<BookCount> getBookCountList() {
            return bookCountList;
        }

        public void setBookCountList(List<BookCount> bookCountList) {
            this.bookCountList = bookCountList;
        }

        public List<Book> getBookList() {
            return bookList;
        }

        public void setBookList(List<Book> bookList) {
            this.bookList = bookList;
        }

        public List<Map<String, Object>> getOtherList() {
            return otherList;
        }

        public void setOtherList(List<Map<String, Object>> otherList) {
            this.otherList = otherList;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }

    //----------------------------------------------  内部类: Book
    public static class Book {
        private String name;
        private String author;
        private int page;
        private double price;

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }

        @Override
        public boolean equals(Object paramObject) {
            return EqualsBuilder.reflectionEquals(this, paramObject);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    //----------------------------------------------  内部类: BookCount (统计数据)
    public static class BookCount {
        private int categoryId;
        private int bookCount;
        private String categoryName;

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }

        @Override
        public boolean equals(Object paramObject) {
            return EqualsBuilder.reflectionEquals(this, paramObject);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
    }
    
}
