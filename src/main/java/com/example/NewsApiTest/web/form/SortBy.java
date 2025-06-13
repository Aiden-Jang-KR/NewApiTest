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

    public static SortBy fromString(String name){
        for (SortBy sortBy : SortBy.values()) {
            if (sortBy.name().equalsIgnoreCase(name)) {
                return sortBy;
            }
        }
        throw new IllegalArgumentException("Unknown name: " + name);

    }
}
