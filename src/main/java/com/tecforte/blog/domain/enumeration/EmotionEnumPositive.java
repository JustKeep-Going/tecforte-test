package com.tecforte.blog.domain.enumeration;

public enum EmotionEnumPositive {
    LOVE(true),
    HAPPY(true),
    TRUST(true);

    public final boolean label;

    EmotionEnumPositive(boolean label) {
        this.label = label;
    }
}
