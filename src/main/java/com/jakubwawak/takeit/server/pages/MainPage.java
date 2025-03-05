package com.jakubwawak.takeit.server.pages;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.jakubwawak.takeit.TakeitApplication;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.jakubwawak.takeit.server.components.FilePickerComponent;

/**
 * Main page of the application.
 */
@Route(value = "/takeit")
@RouteAlias(value = "/")
public class MainPage extends VerticalLayout {

    PasswordField passwordField;
    Button unlockButton;

    FilePickerComponent filePickerComponent;

    /**
     * Constructor for the MainPage class.
     */
    public MainPage(){
        this.setSizeFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        createLayout();
    }

    /**
     * Prepares the components of the main page.
     */
    void prepareComponents(){
        passwordField = new PasswordField("Password");
        passwordField.setWidth("200px");
        passwordField.setHeight("50px");
        passwordField.setPlaceholder("Enter password");
        unlockButton = new Button("Unlock", VaadinIcon.KEY.create(), this::unlockAction);
        unlockButton.setWidth("200px");
        unlockButton.setHeight("50px");
        unlockButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);

        filePickerComponent = new FilePickerComponent(this);
    }

    /**
     * Creates the layout of the main page.
     */
    public void createLayout(){
        prepareComponents();
        add(new HorizontalLayout(new H1("take it"), VaadinIcon.HAND.create()));
        add(passwordField);
        add(unlockButton);
    }

    /**
     * Handles the unlock action.
     * @param event the click event
     */
    private void unlockAction(ClickEvent<Button> event){
        if (passwordField.getValue().equals(TakeitApplication.properties.getValue("$password"))){
            removeAll();
            add(new HorizontalLayout(new H1("take it"), VaadinIcon.HAND.create()));
            add(filePickerComponent);
            passwordField.setValue("");
        }
        else{
            Notification.show("Wrong password!", 3000, Notification.Position.BOTTOM_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
            passwordField.setValue("");
        }
    }

    /**
     * Resets the page to the initial state.
     */
    public void resetPage(){
        removeAll();
        add(new HorizontalLayout(new H1("take it"), VaadinIcon.HAND.create()));
        add(passwordField);
        add(unlockButton);
    }
}
