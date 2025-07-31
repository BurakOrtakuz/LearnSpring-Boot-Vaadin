package com.example.application.base.ui.view.Admin;

import com.example.application.base.ui.component.FilterBar;
import com.example.application.base.ui.component.PaginationComponent;
import com.example.application.base.ui.component.table.ColumnConfig;
import com.example.application.base.ui.component.table.GenericTable;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseManagementView<T> extends VerticalLayout {
    protected GenericTable<T> table;
    protected List<T> data;
    protected FilterBar filterBar;
    protected PaginationComponent pagination;

    // Pagination state
    protected int currentPage = 0;
    protected int pageSize = 10;

    public BaseManagementView() {
    }

    protected void init() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setPadding(false);

        // Initialize table
        table = new GenericTable<>(getColumnConfigs(), new ArrayList<>());
        table.setSizeFull();

        // Initialize pagination
        pagination = new PaginationComponent();
        pagination.setOnPageChange(this::onPageChange);
        pagination.setOnPageSizeChange(this::onPageSizeChange);

        // Create filter bar with table + pagination
        VerticalLayout tableContainer = new VerticalLayout();
        tableContainer.setPadding(false);
        tableContainer.setSpacing(true);
        tableContainer.add(table, pagination);

        filterBar = new FilterBar(tableContainer);
        filterBar.setSizeFull();
        createFilterFields();

        // Load initial data
        loadData(currentPage, pageSize);

        verticalLayout.add(filterBar);
        add(verticalLayout);
    }

    // New abstract methods for pagination
    public abstract List<ColumnConfig<T,?>> getColumnConfigs();
    public abstract Page<T> loadData(int page, int size);
    protected abstract void createFilterFields();
    protected abstract void applyFilters();
    protected abstract String getTitle();
    protected abstract void onAddNew();
    protected abstract void onEdit(T item);
    protected abstract void onDelete(T item);
    protected abstract boolean matchesFilters(T item, Map<String, Object> filters);

    // Pagination event handlers
    protected void onPageChange(int newPage) {
        this.currentPage = newPage;
        loadData(currentPage, pageSize);
    }

    protected void onPageSizeChange(int newPageSize) {
        this.pageSize = newPageSize;
        this.currentPage = 0; // Reset to first page
        loadData(currentPage, pageSize);
    }

    protected void updateTableData(Page<T> page) {
        table.setItems(page.getContent());
        pagination.updatePage(page);
    }

    protected void refreshCurrentPage() {
        loadData(currentPage, pageSize);
    }

    protected void resetToFirstPage() {
        currentPage = 0;
        loadData(currentPage, pageSize);
    }
}
