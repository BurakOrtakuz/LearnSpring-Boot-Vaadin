package com.example.application.base.ui.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PaginationComponent extends VerticalLayout {

    // Navigation buttons
    private Button firstPageButton;
    private Button previousPageButton;
    private Button nextPageButton;
    private Button lastPageButton;

    // Page number buttons container
    private HorizontalLayout pageNumbersLayout;
    private List<Button> pageNumberButtons;
    @Getter
    private int currentPage;
    @Getter
    private int pageSize;
    @Getter
    private long totalElements;
    @Getter
    private int totalPages;
    @Setter
    private Consumer<Integer> onPageChange;
    @Setter
    private Consumer<Integer> onPageSizeChange;
    private static final int MAX_VISIBLE_PAGES = 5;

    public PaginationComponent() {
        this.pageNumberButtons = new ArrayList<>();
        this.currentPage = 0;
        this.pageSize = 10;
        this.totalElements = 0;
        this.totalPages = 0;
        initializeComponents();
        buildLayout();
        updateState();
    }
    public PaginationComponent(int pageSize) {
        this();
        this.pageSize = pageSize;
    }

    private void initializeComponents() {
        firstPageButton = new Button(VaadinIcon.ANGLE_DOUBLE_LEFT.create());
        firstPageButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        firstPageButton.addClickListener(e -> navigateToPage(0));

        previousPageButton = new Button(VaadinIcon.ANGLE_LEFT.create());
        previousPageButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        previousPageButton.addClickListener(e -> navigateToPage(currentPage - 1));

        nextPageButton = new Button(VaadinIcon.ANGLE_RIGHT.create());
        nextPageButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        nextPageButton.addClickListener(e -> navigateToPage(currentPage + 1));

        lastPageButton = new Button(VaadinIcon.ANGLE_DOUBLE_RIGHT.create());
        lastPageButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        lastPageButton.addClickListener(e -> navigateToPage(totalPages - 1));

        pageNumbersLayout = new HorizontalLayout();
        pageNumbersLayout.setSpacing(false);
    }

    private void buildLayout() {
        setPadding(false);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);

        HorizontalLayout navigationLayout = new HorizontalLayout();
        navigationLayout.setSpacing(true);
        navigationLayout.setAlignItems(Alignment.CENTER);
        navigationLayout.add(
            firstPageButton,
            previousPageButton,
            pageNumbersLayout,
            nextPageButton,
            lastPageButton
        );

        HorizontalLayout infoLayout = new HorizontalLayout();
        infoLayout.setSpacing(true);
        infoLayout.setAlignItems(Alignment.CENTER);
        infoLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        infoLayout.setWidthFull();

        add(navigationLayout, infoLayout);
    }

    public void updatePage(Page<?> page) {
        updateState(
            page.getNumber(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.getSize()
        );
    }

    public void updateState(int currentPage, long totalElements, int totalPages, int pageSize) {
        this.currentPage = currentPage;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.pageSize = pageSize;

        updateNavigationButtons();
        updatePageNumbers();
    }

    private void updateState() {
        updateState(0, 0, 0, pageSize);
    }

    private void updateNavigationButtons() {
        firstPageButton.setEnabled(currentPage > 0 && totalPages > 0);
        previousPageButton.setEnabled(currentPage > 0);
        nextPageButton.setEnabled(currentPage < totalPages - 1);
        lastPageButton.setEnabled(currentPage < totalPages - 1 && totalPages > 0);
    }

    private void updatePageNumbers() {
        pageNumbersLayout.removeAll();
        pageNumberButtons.clear();

        if (totalPages <= 1) {
            return;
        }

        int startPage = Math.max(0, currentPage - MAX_VISIBLE_PAGES / 2);
        int endPage = Math.min(totalPages - 1, startPage + MAX_VISIBLE_PAGES - 1);

        if (endPage - startPage < MAX_VISIBLE_PAGES - 1) {
            startPage = Math.max(0, endPage - MAX_VISIBLE_PAGES + 1);
        }

        if (startPage > 0) {
            Button firstButton = createPageButton(0);
            pageNumbersLayout.add(firstButton);

            if (startPage > 1) {
                Span ellipsis = new Span("...");
                ellipsis.getStyle().set("padding", "var(--lumo-space-xs)");
                pageNumbersLayout.add(ellipsis);
            }
        }

        for (int i = startPage; i <= endPage; i++) {
            Button pageButton = createPageButton(i);
            pageNumbersLayout.add(pageButton);
        }

        if (endPage < totalPages - 1) {
            if (endPage < totalPages - 2) {
                Span ellipsis = new Span("...");
                ellipsis.getStyle().set("padding", "var(--lumo-space-xs)");
                pageNumbersLayout.add(ellipsis);
            }

            Button lastButton = createPageButton(totalPages - 1);
            pageNumbersLayout.add(lastButton);
        }
    }

    private Button createPageButton(int pageNumber) {
        Button button = new Button(String.valueOf(pageNumber + 1)); // Display 1-based
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        if (pageNumber == currentPage) {
            button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            button.getStyle().set("minWidth", "32px").set("width", "32px").set("maxWidth", "32px");

        }

        button.addClickListener(e -> navigateToPage(pageNumber));
        pageNumberButtons.add(button);

        return button;
    }

    private void navigateToPage(int pageNumber) {
        if (pageNumber >= 0 && pageNumber < totalPages && pageNumber != currentPage) {
            onPageChange.accept(pageNumber);
        }
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible && totalPages > 1);
    }
}
