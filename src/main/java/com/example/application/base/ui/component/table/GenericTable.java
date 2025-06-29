package com.example.application.base.ui.component.table;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import java.util.List;

public class GenericTable<T> extends Grid<T> {
    public GenericTable(List<ColumnConfig<T, ?>> columns, List<T> items) {
        super();
        for (ColumnConfig<T, ?> col : columns) {
            if (com.vaadin.flow.component.Component.class.isAssignableFrom(col.getValueProvider().apply(items.isEmpty() ? null : items.get(0)).getClass())) {
                addComponentColumn(item -> (Component) col.getValueProvider().apply(item)).setHeader(col.getHeader());
            } else {
                addColumn(col.getValueProvider()).setHeader(col.getHeader());
            }
        }
        setItems(items);
        addClassName("generic-table");
    }
}
