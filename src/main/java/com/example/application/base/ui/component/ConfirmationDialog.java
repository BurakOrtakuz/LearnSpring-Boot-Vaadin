package com.example.application.base.ui.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConfirmationDialog extends Dialog {

    public static void show(String title, String message, Runnable onConfirm) {
        show(title, message, onConfirm, null);
    }

    public static void show(String title, String message, Runnable onConfirm, Runnable onCancel) {
        show(title, message, "Evet", "İptal", onConfirm, onCancel);
    }

    public static void show(String title, String message, String confirmText, String cancelText,
                           Runnable onConfirm, Runnable onCancel) {
        ConfirmationDialog dialog = new ConfirmationDialog(title, message, confirmText, cancelText, onConfirm, onCancel);
        dialog.open();
    }

    public static void showDelete(String itemName, Runnable onConfirm) {
        show(
            "Silme Onayı",
            itemName + " silmek istediğinize emin misiniz? Bu işlem geri alınamaz.",
            "Sil",
            "İptal",
            onConfirm,
            null
        );
    }

    private ConfirmationDialog(String title, String message, String confirmText, String cancelText,
                              Runnable onConfirm, Runnable onCancel) {
        setModal(true);
        setDraggable(false);
        setResizable(false);
        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);

        // Header
        H3 dialogTitle = new H3(title);
        dialogTitle.getStyle()
            .set("margin", "0")
            .set("color", "var(--lumo-primary-text-color)");

        // Message
        Span messageSpan = new Span(message);
        messageSpan.getStyle()
            .set("color", "var(--lumo-body-text-color)")
            .set("line-height", "1.5");

        // Buttons
        Button confirmButton = new Button(confirmText);
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        if (confirmText.equals("Sil")) {
            confirmButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        }
        confirmButton.addClickListener(e -> {
            close();
            if (onConfirm != null) {
                onConfirm.run();
            }
        });

        Button cancelButton = new Button(cancelText);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.addClickListener(e -> {
            close();
            if (onCancel != null) {
                onCancel.run();
            }
        });

        // Button layout
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        buttonLayout.add(cancelButton, confirmButton);

        // Main layout
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setPadding(true);
        mainLayout.setSpacing(true);
        mainLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        mainLayout.getStyle().set("min-width", "300px").set("max-width", "500px");

        mainLayout.add(dialogTitle, messageSpan, buttonLayout);

        add(mainLayout);

        // Focus on cancel button by default
        addOpenedChangeListener(e -> {
            if (e.isOpened()) {
                cancelButton.focus();
            }
        });

        // ESC key closes dialog
        addDialogCloseActionListener(e -> {
            if (onCancel != null) {
                onCancel.run();
            }
        });
    }
}
