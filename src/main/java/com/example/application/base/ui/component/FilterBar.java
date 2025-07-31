package com.example.application.base.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Setter
public class FilterBar extends SplitLayout {

    private final VerticalLayout filterBar = new VerticalLayout();
    private final Component mainContent;

    private final List<FilterField<?>> filterFields = new ArrayList<>();
    @Setter
    private Runnable reloadCallback;

    public FilterBar(Component mainContent) {
        this.mainContent = mainContent;
        filterBar.setSizeFull();

        filterBar.getStyle().set("gap", "1em");

        Button closeButton = new Button(VaadinIcon.CLOSE.create(), e -> filterBar.setVisible(false));
        closeButton.getElement().getStyle().set("margin-left", "auto");
        HorizontalLayout header = new HorizontalLayout(closeButton);
        header.setWidthFull();
        filterBar.add(header);
        filterBar.setMinWidth("200px");
        filterBar.setMaxWidth("300px");
        addSplitterDragendListener(event ->
        {
            if(!filterBar.isVisible())
                filterBar.setVisible(true);
            else if (getSplitterPosition() <= 200) {
                filterBar.setVisible(false);
            }
        });
        setOrientation(Orientation.HORIZONTAL);
        setSizeFull();
        setSplitterPosition(20);

        addToPrimary(filterBar);
        addToSecondary(mainContent);
    }

    public <T> void addFilterComponent(Component field, Supplier<T> valueSupplier, String key) {
        filterBar.add(field);
        FilterField<T> filter = new FilterField<>(key, valueSupplier);
        filterFields.add(filter);

        if (field instanceof HasValue<?, ?> hv) {
            hv.addValueChangeListener(e -> {
                reloadCallback.run(); // Lazy reload
            });
        }
    }

    public Map<String, Object> getFilterValues() {
        return filterFields.stream()
                .filter(field -> field.key() != null)
                .collect(Collectors.toMap(
                    FilterField::key,
                    field -> {
                        Object value = field.getValue();
                        return value != null ? value : "";
                    }
                ));
    }
    private record FilterField<T>(String key, Supplier<T> valueSupplier) {
        public Object getValue() {
            return valueSupplier.get();
        }
    }
}
