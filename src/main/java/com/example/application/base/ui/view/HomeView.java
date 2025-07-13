package com.example.application.base.ui.view;

import com.example.application.base.ui.component.FooterBar;
import com.example.application.base.ui.component.RightDrawer;
import com.example.application.base.ui.layout.MainNavbar;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value="/home", layout = MainNavbar.class)
public class HomeView extends VerticalLayout {

    private RightDrawer rightDrawer;

    public HomeView() {
        initializeComponents();
    }

    private void initializeComponents() {
        H1 title = new H1("Home");
        title.getStyle().set("font-size", "24px");

        rightDrawer = new RightDrawer("Drawer Başlığı");

        Text description = new Text("Welcome to the home page of the application. "
                + "This is a simple example of a Vaadin application with a home view.");

        // Test butonları
        HorizontalLayout buttons = createTestButtons();

        add(title, description, buttons);
        add(new FooterBar());
    }

    private HorizontalLayout createTestButtons() {
        Button formDrawerBtn = new Button("Form Drawer", VaadinIcon.EDIT.create());
        formDrawerBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        formDrawerBtn.addClickListener(e -> openFormDrawer());

        Button infoDrawerBtn = new Button("Bilgi Drawer", VaadinIcon.INFO_CIRCLE.create());
        infoDrawerBtn.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        infoDrawerBtn.addClickListener(e -> openInfoDrawer());

        return new HorizontalLayout(formDrawerBtn, infoDrawerBtn);
    }

    private void openFormDrawer() {
        rightDrawer.setTitle("Kullanıcı Bilgileri");

        // Örnek form
        FormLayout form = new FormLayout();
        TextField nameField = new TextField("Ad Soyad");
        TextField emailField = new TextField("E-posta");
        TextField phoneField = new TextField("Telefon");

        form.add(nameField, emailField, phoneField);

        // Kaydet butonu
        Button saveBtn = new Button("Kaydet", VaadinIcon.CHECK.create());
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveBtn.addClickListener(e -> rightDrawer.close());

        VerticalLayout content = new VerticalLayout(form, saveBtn);
        rightDrawer.setContent(content);
        rightDrawer.open();
    }

    private void openInfoDrawer() {
        rightDrawer.setTitle("Sistem Bilgileri");

        VerticalLayout info = new VerticalLayout();
        info.add(
            new Paragraph("Sistem durumu: Çalışıyor ✅"),
            new Paragraph("Kullanıcı sayısı: 128"),
            new Paragraph("Son güncelleme: Bugün")
        );

        rightDrawer.setContent(info);
        rightDrawer.open();
    }
}
