package com.example.application.base.ui.component.table;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;
import java.util.function.Consumer;

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
    public GenericTable(List<ColumnConfig<T, ?>> columns,
                        DataProvider<T, ?> dataProvider) {
        super();
        for (ColumnConfig<T, ?> col : columns) {
            if (col.isComponentColumn()) {
                addComponentColumn(item -> (Component) col.getValueProvider().apply(item))
                        .setHeader(col.getHeader())
                        .setSortable(col.isSortable());
            } else {
                addColumn(col.getValueProvider())
                        .setHeader(col.getHeader())
                        .setSortable(col.isSortable());
            }
        }
        setDataProvider(dataProvider);
        addClassName("generic-table");
    }
    public void addDataFilterHeader(ColumnConfig<T, ?> col, Grid.Column<T> gridColumn, Consumer<String> filterConsumer) {
        NativeLabel label = new NativeLabel(col.getHeader());
        label.getStyle()
                .set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");

        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(e -> filterConsumer.accept(e.getValue()));

        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().add("spacing-xs");

        HeaderRow headerRow;
        if (getHeaderRows().isEmpty()) {
            headerRow = appendHeaderRow();
        } else {
            headerRow = getHeaderRows().getLast();
        }

        headerRow.getCell(gridColumn).setComponent(layout);
    }
    public void setAutoWidth(boolean autoWidth) {
        if (autoWidth) {
            getColumns().forEach(column -> column.setAutoWidth(true));
        } else {
            getColumns().forEach(column -> column.setAutoWidth(false));
        }
    }
}
