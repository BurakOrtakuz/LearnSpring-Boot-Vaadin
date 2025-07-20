package com.example.application.base.ui.layout;

import com.example.application.base.ui.component.MedicineDrawer;
import com.example.application.base.ui.component.RightDrawer;
import com.example.application.base.ui.view.Doctor.DoctorAppointmentsView;
import com.example.application.base.ui.view.Doctor.DoctorView;
import com.example.application.base.ui.view.HomeView;
import com.example.application.base.ui.view.MedicineView;
import com.example.application.service.IMedicineService;
import com.example.application.service.IUnitService;
import com.example.application.service.MedicineService;
import com.example.application.service.UnitService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class DoctorAppLayout extends AppLayout {
    private final IUnitService unitService;
    private final IMedicineService medicineService;
    private final RightDrawer medicineDrawer;

    public DoctorAppLayout(IUnitService unitService, IMedicineService medicineService, UnitService unitService1, MedicineService medicineService1) {
        this.unitService = unitService1;
        this.medicineService = medicineService1;
        VerticalLayout drawerLayout = new VerticalLayout();
        drawerLayout.setClassName("doctor-drawer");
        drawerLayout.setPadding(false);
        drawerLayout.setSpacing(false);
        drawerLayout.setWidthFull();
        drawerLayout.setHeightFull();

        medicineDrawer = new MedicineDrawer("İlaç Ekle", medicineService, unitService);

        H1 logoText = new H1("ASCHENTE");
        logoText.setClassName("doctor-logo");
        RouterLink logoLink = new RouterLink((String) null, DoctorView.class);
        logoLink.add(logoText);
        logoLink.setClassName("doctor-logo-link");

        RouterLink home = new RouterLink("Ana Sayfa", DoctorView.class);
        home.setClassName("doctor-link");
        RouterLink appointments = new RouterLink("Randevular", DoctorAppointmentsView.class);
        appointments.setClassName("doctor-link");

        RouterLink medicine = new RouterLink("İlaçlar", MedicineView.class);
        medicine.setClassName("doctor-link");

        RouterLink logout = new RouterLink("Çıkış yap", HomeView.class);
        logout.setClassName("doctor-link");
        logout.getElement().addEventListener("click", e -> {
            getUI().ifPresent(ui -> ui.getPage().setLocation("/logout"));
        });
        drawerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        drawerLayout.add(logoLink, home, appointments, medicine, logout);
        addToDrawer(drawerLayout);
    }
}
