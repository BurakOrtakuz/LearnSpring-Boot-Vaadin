package com.example.application.base.ui.view.Admin;

import com.example.application.base.ui.layout.AdminLayout;
import com.example.application.service.IPatientService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "/admin/user-management", layout = AdminLayout.class)
@RolesAllowed("ADMIN")
public class AdminUserManagementView extends VerticalLayout {

    private final IPatientService patientService;
    public AdminUserManagementView(IPatientService patientService) {
        this.patientService = patientService;
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // TabSheet kullanarak daha kolay yönetim
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);
        tabSheet.add("Hastalar", new PatientManagementView(patientService));
        tabSheet.add("Doktorlar", createDoctorView());
        tabSheet.add("Yöneticiler", createAdminView());

        add(tabSheet);
    }

    private VerticalLayout createDoctorView() {
        VerticalLayout doctorLayout = new VerticalLayout();
        doctorLayout.setSizeFull();
        doctorLayout.setPadding(true);

        H2 title = new H2("Doktor Yönetimi");
        Span description = new Span("Doktor kayıtlarını görüntüleyebilir, düzenleyebilir ve yeni doktor ekleyebilirsiniz.");

        Div doctorTable = new Div();
        doctorTable.setText("Doktor tablosu burada gösterilecek...");
        doctorTable.getStyle().set("border", "1px solid #ccc")
                            .set("padding", "20px")
                            .set("margin-top", "20px")
                            .set("border-radius", "8px");

        doctorLayout.add(title, description, doctorTable);
        return doctorLayout;
    }

    private VerticalLayout createAdminView() {
        VerticalLayout adminLayout = new VerticalLayout();
        adminLayout.setSizeFull();
        adminLayout.setPadding(true);

        H2 title = new H2("Yönetici Yönetimi");
        Span description = new Span("Yönetici kayıtlarını görüntüleyebilir, düzenleyebilir ve yeni yönetici ekleyebilirsiniz.");

        Div adminTable = new Div();
        adminTable.setText("Yönetici tablosu burada gösterilecek...");
        adminTable.getStyle().set("border", "1px solid #ccc")
                            .set("padding", "20px")
                            .set("margin-top", "20px")
                            .set("border-radius", "8px");

        adminLayout.add(title, description, adminTable);
        return adminLayout;
    }
}
