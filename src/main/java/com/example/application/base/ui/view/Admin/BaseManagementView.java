package com.example.application.base.ui.view.Admin;

import com.example.application.base.ui.component.FilterBar;
import com.example.application.base.ui.component.table.ColumnConfig;
import com.example.application.base.ui.component.table.GenericTable;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseManagementView<T> extends VerticalLayout {
    protected GenericTable<T> table;
    protected List<T> data;
    protected FilterBar filterBar;

    public BaseManagementView() {
    }

    protected void init() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setPadding(false);

        table = new GenericTable<>(getColumnConfigs(), new ArrayList<>());
        table.setItems(loadData());
        table.setSizeFull();

        filterBar = new FilterBar(table);
        filterBar.setSizeFull();
        createFilterFields();

        verticalLayout.add(filterBar);
        add(verticalLayout);
    }
    public abstract List<ColumnConfig<T,?>> getColumnConfigs();
    public abstract List<T> loadData();
    protected abstract void createFilterFields();
    protected abstract void applyFilters();
    protected abstract String getTitle();
    protected abstract void onAddNew();
    protected abstract void onEdit(T item);
    protected abstract void onDelete(T item);
    protected abstract boolean matchesFilters(T item, Map<String, Object> filters);
}
