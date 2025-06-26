package com.example.application.base.ui.component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;

public class FooterBar extends Div {
    public FooterBar() {
        setClassName("footer");

        // Üst mesaj
        Span mailMessage = new Span();
        mailMessage.add(
            new Text("Soru ve cevaplarınız için "),
            new Anchor("mailto:NedenOlmasin@gmail.com", "NedenOlmasin@gmail.com") {{
                setClassName("footer-mail");
            }},
            new Text(" adresine mesaj atabilirsiniz.")
        );
        mailMessage.setClassName("footer-message");

        // Alt kısım: logo ve linkler
        Div bottom = new Div();
        bottom.setClassName("footer-bottom");

        // Logo (örnek: emoji veya resim)
        Span logo = new Span("🌟");
        logo.setClassName("footer-logo");

        // Hakkında, iletişim, sosyal medya linkleri
        Anchor about = new Anchor("/about", "Hakkında");
        Anchor contact = new Anchor("mailto:NedenOlmasin@gmail.com", "İletişim");
        about.setClassName("footer-link");
        contact.setClassName("footer-link");

        Image instagramLogo = new Image("images/instagram-brands.svg", "Instagram");
        instagramLogo.setClassName("footer-logo-img");
        instagramLogo.setWidth("22px");
        instagramLogo.setHeight("22px");

        Anchor instagram = new Anchor("https://instagram.com", instagramLogo, new Span("Instagram"));
        instagram.setClassName("footer-link");

        bottom.add(logo, about, contact, instagram);

        add(mailMessage, bottom);
    }
}