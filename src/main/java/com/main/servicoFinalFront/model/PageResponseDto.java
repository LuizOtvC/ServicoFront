package com.main.servicoFinalFront.model;

import java.util.List;

public class PageResponseDto<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int number;
    private boolean last;


    public List<T> getContent() { return content; }
    public void setContent(List<T> content) { this.content = content; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public long getTotalElements() { return totalElements; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public boolean isLast() { return last; }
    public void setLast(boolean last) { this.last = last; }
}