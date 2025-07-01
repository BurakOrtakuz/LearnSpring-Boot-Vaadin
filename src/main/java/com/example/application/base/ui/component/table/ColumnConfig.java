package com.example.application.base.ui.component.table;

import java.io.Serializable;
import com.vaadin.flow.function.ValueProvider;
import lombok.Getter;

@Getter
public class ColumnConfig<T, V> implements Serializable {
    private final String header;
    private final ValueProvider<T, V> valueProvider;
    private final boolean sortable;
    private final boolean componentColumn;
    public ColumnConfig(String header, ValueProvider<T, V> valueProvider, boolean sortable) {
        this(header, valueProvider, sortable, false);
    }
    public ColumnConfig(String header, ValueProvider<T, V> valueProvider, boolean sortable, boolean componentColumn) {
        this.header = header;
        this.valueProvider = valueProvider;
        this.sortable = sortable;
        this.componentColumn = componentColumn;
    }

}
