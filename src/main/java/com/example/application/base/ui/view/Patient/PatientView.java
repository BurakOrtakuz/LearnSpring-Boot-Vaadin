package com.example.application.base.ui.view.Patient;

import com.example.application.base.ui.component.MainNavbar;
import com.example.application.domain.Person;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "/patient", layout = MainNavbar.class)
@RolesAllowed("PATIENT")
public class PatientView extends VerticalLayout {

    public PatientView() {
        System.out.println("PatientView initialized");
        setSpacing(true);
        setPadding(true);

        // Giriş yapan kullanıcıyı al
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Principal: " + principal);
        if (principal instanceof Person person) {
            add(new Text("Ad: " + person.getFirstName()));
            add(new Text("Soyad: " + person.getLastName()));
            add(new Text("E-posta: " + person.getEmail()));
            add(new Text("Doğum Tarihi: " + (person.getBirthDate() != null ? person.getBirthDate().toString() : "-")));
            add(new Text("Cinsiyet: " + (person.getGender() != null ? person.getGender() : "-")));
            add(new Text("Telefon: " + (person.getPhoneNumber() != null ? person.getPhoneNumber() : "-")));
            add(new Text("Adres: " + (person.getAddress() != null ? person.getAddress() : "-")));
        } else {
            add(new Text("Kullanıcı bilgileri alınamadı."));
        }
    }
}