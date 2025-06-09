package com.example.NewsApiTest.web.form;

public enum SortBy {
    PUB("publishedAt"), REL("Relevancy"), PUP("Popularity");

    private final String label;

    SortBy(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
