package com.example.application.base.ui.component.table;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import java.util.List;

public class GenericTable<T> extends Grid<T> {
    public GenericTable(List<ColumnConfig<T, ?>> columns, List<T> items) {
        super();
        for (ColumnConfig<T, ?> col : columns) {
            if (col.isComponentColumn()) {
                addComponentColumn(item -> (Component) col.getValueProvider().apply(item)).setHeader(col.getHeader()).setSortable(col.isSortable());
            } else {
                addColumn(col.getValueProvider()).setHeader(col.getHeader()).setSortable(col.isSortable());
            }
        }
        setItems(items == null ? List.of() : items);
        addClassName("generic-table");
    }
}
