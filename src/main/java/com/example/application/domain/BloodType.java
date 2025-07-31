package com.example.application.domain;

public enum BloodType {
    A_POSITIVE("A Rh+"),
    A_NEGATIVE("A Rh-"),
    B_POSITIVE("B Rh+"),
    B_NEGATIVE("B Rh-"),
    AB_POSITIVE("AB Rh+"),
    AB_NEGATIVE("AB Rh-"),
    O_POSITIVE("O Rh+"),
    O_NEGATIVE("O Rh-");

    private final String displayName;

    BloodType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
