package com.example.application.base.ui.view.Doctor;

import com.example.application.base.ui.layout.DoctorAppLayout;
import com.example.application.domain.Notification;
import com.example.application.domain.Person;
import com.example.application.service.NotificationService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Route(value = "/doctor", layout = DoctorAppLayout.class)
@RolesAllowed("DOCTOR")
public class DoctorView extends VerticalLayout {
    private final NotificationService notificationService;

    public DoctorView(NotificationService notificationService) {
        this.notificationService = notificationService;

        setSpacing(true);
        setPadding(true);
        setWidthFull();

        H2 welcomeText = new H2("Doktor Paneli");
        add(welcomeText);

        // Bildirimler bölümü
        H3 notificationsTitle = new H3("Bildirimlerim");
        add(notificationsTitle, notificationLayout());
    }

    private VerticalLayout notificationLayout()
    {
        VerticalLayout notificationLayout = new VerticalLayout();

        List <Notification> notifications = loadNotifications();

        for (Notification notification : notifications)
        {
            VerticalLayout notificationCard = createNotificationCard(notification);
            notificationLayout.add(notificationCard);
        }
        return notificationLayout;
    }

    private VerticalLayout createNotificationCard(Notification notification) {
        VerticalLayout notificationCard = new VerticalLayout();
        notificationCard.setWidthFull();
        notificationCard.setPadding(true);
        notificationCard.setSpacing(true);
        notificationCard.setClassName("notification-card");

        if(notification.getReadStatus())
            notificationCard.getStyle().set("background-color", "#f5f5f5");
        else
            notificationCard.getStyle().set("background-color", "#e0f7fa");

        notificationCard.addClickListener(event -> {
            notificationService.markAsRead(notification.getId());
            notificationCard.getStyle().set("background-color", "#f5f5f5");
            event.getSource().getUI().ifPresent(ui -> ui.navigate("doctor/examination-detail/" + notification.getRelatedEntityId()));
        });

        H3 title = new H3(notification.getTitle());
        title.getStyle().set("margin", "0");

        Span description = new Span(notification.getMessage());
        description.getStyle().set("margin", "0");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Span date = new Span(dateFormat.format(notification.getTimestamp()));

        VerticalLayout content = new VerticalLayout();
        content.add(title, description, date);

        notificationCard.add(title, content);

        return notificationCard;
    }
    private List<Notification> loadNotifications() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Person person) {
            List<Notification> notifications = new ArrayList<>(notificationService.getNotificationsByPersonId(person.getId()));
            notifications.sort((n1, n2) -> n2.getTimestamp().compareTo(n1.getTimestamp()));
            return notifications;
        }
        return new ArrayList<>();
    }
}