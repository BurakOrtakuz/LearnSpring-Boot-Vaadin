package com.example.application.base.ui.component.table;

import java.io.Serializable;
import com.vaadin.flow.function.ValueProvider;
import lombok.Getter;

@Getter
public class ColumnConfig<T, V> implements Serializable {
    private final String header;
    private final ValueProvider<T, V> valueProvider;

    public ColumnConfig(String header, ValueProvider<T, V> valueProvider) {
        this.header = header;
        this.valueProvider = valueProvider;
    }

}
