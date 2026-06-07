package com.example.demo.stock.domain;

import java.util.function.Consumer;

public class NullableField<T> {

    public NullableField(T value) {
        this.value = value;
        this.isPresent = true;
    }

    public NullableField() {
    }

    private T value;
    private boolean isPresent;

    public T getValue() {
        return value;
    }

    public void applyIfPresent(Consumer<T> consumer) {
        if (isPresent) {
            consumer.accept(value);
        }
    }

    public void setValue(T value) {
        this.value = value;
        this.isPresent = true;
    }
}
