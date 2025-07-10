package com.example.application.base.ui.view;

import com.example.application.base.ui.component.FooterBar;
import com.example.application.base.ui.layout.MainNavbar;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value="/home", layout = MainNavbar.class)
public class HomeView extends VerticalLayout {
    public HomeView() {
        H1 title = new H1("Home");
        title.getStyle().set("font-size", "24px");
        Text description = new Text("Welcome to the home page of the application. "
                + "This is a simple example of a Vaadin application with a home view.");
        add(title, description);

        add(new FooterBar());
    }
}
