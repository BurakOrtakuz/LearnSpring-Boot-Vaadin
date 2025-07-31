package com.example.application.base.ui.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.data.domain.Page;

public class PaginationComponent extends HorizontalLayout {
    private Button firstPageButton;
    private Button previousPageButton;
    private Button nextPageButton;
    private Button lastPageButton;

    private Page<?> currentPage;
}
