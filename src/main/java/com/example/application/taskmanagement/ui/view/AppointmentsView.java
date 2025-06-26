package com.example.application.taskmanagement.ui.view;

import com.example.application.base.ui.component.FooterBar;
import com.example.application.base.ui.component.MainNavbar;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value = "appointments", layout = MainNavbar.class)
public class AppointmentsView extends VerticalLayout {
    public AppointmentsView() {
        Text text = new Text("Appointments");
        add(text);
        FooterBar footerBar = new FooterBar();
        add(footerBar);
    }
}
