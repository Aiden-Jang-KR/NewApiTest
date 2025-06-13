package com.example.NewsApiTest.web.form;

public enum Language {
    AR("ar"),
    DE("de"),
    EN("en"),
    ES("es"),
    FR("fr"),
    HE("he"),
    IT("it"),
    NL("nl"),
    NO("no"),
    PT("pt"),
    RU("ru"),
    SV("sv"),
    UD("ud"),
    ZH("zh");

    private final String label;

    Language(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Language fromString(String name){
        for (Language lang : Language.values()) {
            if (lang.name().equalsIgnoreCase(name)) {
                return lang;
            }
        }
        throw new IllegalArgumentException("Unknown name: " + name);

    }
}
