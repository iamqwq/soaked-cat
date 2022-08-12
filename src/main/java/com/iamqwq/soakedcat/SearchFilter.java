package com.iamqwq.soakedcat;

import com.iamqwq.soakedcat.enumeration.EnumImageType;
import com.iamqwq.soakedcat.enumeration.EnumSearchMode;

import java.util.Objects;

public class SearchFilter {

    private String keyword;
    private EnumImageType imageType;
    private EnumSearchMode searchMode;
    private int bookmarkAmount;
    private int maxPageAmount;
    private String language;

    public SearchFilter() {
        this("", EnumImageType.ALL, EnumSearchMode.SAFE, 0, 0);
    }

    public SearchFilter(String keyword, EnumImageType imageType, EnumSearchMode searchMode, int bookmarkAmount, int maxPageAmount) {
        this(keyword, imageType, searchMode, bookmarkAmount, maxPageAmount, "zh");
    }

    private SearchFilter(String keyword, EnumImageType imageType, EnumSearchMode searchMode, int bookmarkAmount, int maxPageAmount, String language) {
        this.keyword = Objects.requireNonNullElse(keyword, "");
        this.imageType = imageType;
        this.searchMode = searchMode;
        if(bookmarkAmount < 0) {
            this.bookmarkAmount = 0;
        } else {
            this.bookmarkAmount = bookmarkAmount;
        }
        if(maxPageAmount < 0) {
            this.maxPageAmount = 0;
        } else {
            this.maxPageAmount = maxPageAmount;
        }
        this.language = language;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public EnumImageType getImageType() {
        return imageType;
    }

    public void setImageType(EnumImageType imageType) {
        this.imageType = imageType;
    }

    public EnumSearchMode getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(EnumSearchMode searchMode) {
        this.searchMode = searchMode;
    }

    public int getBookmarkAmount() {
        return bookmarkAmount;
    }

    public void setBookmarkAmount(int bookmarkAmount) {
        this.bookmarkAmount = bookmarkAmount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getMaxPageAmount() {
        return maxPageAmount;
    }

    public void setMaxPageAmount(int maxPageAmount) {
        this.maxPageAmount = maxPageAmount;
    }
}
