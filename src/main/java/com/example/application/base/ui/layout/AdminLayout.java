package com.example.application.base.ui.layout;

import com.example.application.base.ui.view.Admin.AdminUserManagementView;
import com.example.application.base.ui.view.Admin.AdminView;
import com.example.application.base.ui.view.HomeView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class AdminLayout extends AppLayout {

    public AdminLayout() {
        VerticalLayout drawerLayout = new VerticalLayout();
        drawerLayout.setClassName("admin-drawer");
        drawerLayout.setPadding(false);
        drawerLayout.setSpacing(false);
        drawerLayout.setWidthFull();
        drawerLayout.setHeightFull();

        H1 logoText = new H1("ASCHENTE");
        logoText.setClassName("admin-logo");
        RouterLink logoLink = new RouterLink((String) null, AdminView.class);
        logoLink.add(logoText);
        logoLink.setClassName("admin-logo-link");

        RouterLink dashboard = new RouterLink("Admin Paneli", AdminView.class);
        dashboard.setClassName("admin-link");

        RouterLink userManagement = new RouterLink("Kullanıcı Yönetimi", AdminUserManagementView.class);
        userManagement.setClassName("admin-link");

        RouterLink reports = new RouterLink("Raporlar", HomeView.class);
        reports.setClassName("admin-link");

        RouterLink settings = new RouterLink("Sistem Ayarları", HomeView.class);
        settings.setClassName("admin-link");

        RouterLink logout = new RouterLink("Çıkış yap", HomeView.class);
        logout.setClassName("admin-link");
        logout.getElement().addEventListener("click", e ->
                getUI().ifPresent(ui -> ui.getPage().setLocation("/logout")));

        drawerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        drawerLayout.add(logoLink, dashboard, userManagement, reports, settings, logout);
        addToDrawer(drawerLayout);
    }
}
