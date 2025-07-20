package com.example.application.base.ui.view.Doctor;

import com.example.application.base.ui.layout.DoctorAppLayout;
import com.example.application.domain.Notification;
import com.example.application.domain.Person;
import com.example.application.service.NotificationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.util.List;

@Route(value = "/doctor", layout = DoctorAppLayout.class)
@RolesAllowed("DOCTOR")
public class DoctorView extends VerticalLayout {
    private final NotificationService notificationService;
    private Grid<Notification> notificationGrid;

    public DoctorView(NotificationService notificationService) {
        this.notificationService = notificationService;

        setSpacing(true);
        setPadding(true);
        setWidthFull();

        H2 welcomeText = new H2("Doktor Paneli");
        add(welcomeText);

        // Bildirimler bölümü
        H3 notificationsTitle = new H3("Bildirimlerim");
        add(notificationsTitle);

        createNotificationGrid();
        loadNotifications();

        add(notificationGrid);
    }

    private void createNotificationGrid() {
        notificationGrid = new Grid<>(Notification.class, false);
        notificationGrid.setWidthFull();
        notificationGrid.setHeight("400px");

        notificationGrid.addColumn(notification -> notification.getTitle())
                .setHeader("Başlık")
                .setAutoWidth(true)
                .setFlexGrow(1);

        notificationGrid.addColumn(notification -> notification.getMessage())
                .setHeader("Mesaj")
                .setAutoWidth(true)
                .setFlexGrow(2);

        notificationGrid.addColumn(notification -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            return formatter.format(notification.getTimestamp());
        })
                .setHeader("Tarih")
                .setAutoWidth(true);

        notificationGrid.addColumn(notification -> notification.getReadStatus() ? "Okundu" : "Okunmadı")
                .setHeader("Durum")
                .setAutoWidth(true);

        notificationGrid.addColumn(new ComponentRenderer<>(notification -> {
            HorizontalLayout actions = new HorizontalLayout();

            if (!notification.getReadStatus()) {
                Button markAsReadBtn = new Button("Okundu İşaretle", new Icon(VaadinIcon.CHECK));
                markAsReadBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
                markAsReadBtn.addClickListener(e -> {
                    notificationService.markAsRead(notification.getId());
                    loadNotifications(); // Tabloyu yenile
                    com.vaadin.flow.component.notification.Notification.show("Bildirim okundu olarak işaretlendi", 2000, com.vaadin.flow.component.notification.Notification.Position.TOP_CENTER);
                });
                actions.add(markAsReadBtn);
            }

            Button deleteBtn = new Button("Sil", new Icon(VaadinIcon.TRASH));
            deleteBtn.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            deleteBtn.addClickListener(e -> {
                notificationService.deleteNotification(notification.getId());
                loadNotifications(); // Tabloyu yenile
                com.vaadin.flow.component.notification.Notification.show("Bildirim silindi", 2000, com.vaadin.flow.component.notification.Notification.Position.TOP_CENTER);
            });
            actions.add(deleteBtn);

            return actions;
        })).setHeader("İşlemler").setAutoWidth(true);
    }

    private void loadNotifications() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Person person) {
            List<Notification> notifications = notificationService.getNotificationsByPersonId(person.getId());
            notificationGrid.setItems(notifications);
        }
    }
}