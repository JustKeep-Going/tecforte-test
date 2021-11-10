package com.tecforte.blog.domain.enumeration;

public enum EmotionEnumNegative {
    SAD(false),
    FEAR(false),
    LONELY(false);

    public final boolean label;

    EmotionEnumNegative(boolean label) {
        this.label = label;
    }
}
