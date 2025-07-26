package com.example.application.domain;

public enum Unit {
    MG("mg"),
    ML("ml"),
    TABLET("tablet"),
    CAPSULE("capsule"),
    DROP("drop"),
    TUBE("tube"),
    GRAM("gram"),
    LITER("liter");

    private final String displayName;

    Unit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
