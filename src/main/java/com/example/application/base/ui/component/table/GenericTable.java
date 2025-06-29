package com.example.application.base.ui.component.table;

import com.vaadin.flow.component.grid.Grid;
import java.util.List;

public class GenericTable<T> extends Grid<T> {
    public GenericTable(List<ColumnConfig<T, ?>> columns, List<T> items) {
        super();
        for (ColumnConfig<T, ?> col : columns) {
            addColumn(col.getValueProvider()).setHeader(col.getHeader());
        }
        setItems(items);
        addClassName("generic-table");
    }
}
