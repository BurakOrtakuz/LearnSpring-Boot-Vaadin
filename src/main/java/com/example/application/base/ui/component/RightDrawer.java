package com.example.application.base.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RightDrawer extends Dialog {

    private final VerticalLayout content;
    private  H3 titleLabel;
    private  Button closeButton;
    private final Div contentArea;

    public RightDrawer() {
        super();
        this.setClassName("rightDrawer");
        this.setSizeFull();
        this.setDraggable(false);
        this.setResizable(false);

        // Ana container
        content = new VerticalLayout();
        content.setClassName("drawer-Vertical-content");
        content.setPadding(false);
        content.setSpacing(false);
        content.setSizeFull();

        // Header kısmı
        HorizontalLayout header = createHeader();

        // Content alanı
        contentArea = new Div();
        contentArea.setClassName("drawer-content");
        contentArea.setSizeFull();

        content.add(header, contentArea);
        content.setFlexGrow(0, header);
        content.setFlexGrow(1, contentArea);

        super.add(content);

        closeButton.addClickListener(e -> this.close());
        this.addDialogCloseActionListener(e -> this.close());
        this.setCloseOnOutsideClick(true);
    }

    public RightDrawer(String title) {
        this();
        setTitle(title);
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setPadding(true);
        header.setSpacing(true);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        titleLabel = new H3();
        titleLabel.getStyle().set("margin", "0");

        closeButton = new Button(VaadinIcon.CLOSE.create());
        closeButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
        closeButton.setClassName("drawer-close-btn");
        closeButton.getElement().setAttribute("aria-label", "Kapat");

        header.add(titleLabel, closeButton);
        return header;
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setContent(Component... components) {
        contentArea.removeAll();
        contentArea.add(components);
    }

    @Override
    public void add(Component... components) {
        contentArea.add(components);
    }

    public void clearContent() {
        contentArea.removeAll();
    }

    @Override
    public void open() {
        super.open();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public boolean isOpened() {
        return super.isOpened();
    }
}
