package com.example.application.base.ui.view;

import com.example.application.base.ui.layout.MainNavbar;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value = "about", layout = MainNavbar.class)
public class AboutView extends VerticalLayout {
    public AboutView() {
        Text text = new Text("About");
        add(text);
    }
}
