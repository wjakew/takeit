/**
 * @author Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.takeit.server.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.component.Component;

import java.util.ArrayList;

import com.jakubwawak.takeit.TakeitApplication;
import com.jakubwawak.takeit.entity.TakeItFile;
import com.jakubwawak.takeit.file_engine.FileEngine;
import com.jakubwawak.takeit.server.pages.MainPage;

/**
 * File picker component.
 */
public class FilePickerComponent extends VerticalLayout {

    VirtualList<TakeItFile> fileList;
    ArrayList<TakeItFile> currentFiles;

    Button lockButton;
    Button refreshButton;

    HorizontalLayout header;

    MainPage parent;

    /**
     * Constructor for the FilePickerComponent class.
     */
    public FilePickerComponent(MainPage mainPage){
        parent = mainPage;
        currentFiles = new ArrayList<>();

        getStyle().set("border", "1px solid black");
        this.setSizeFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);

        FileEngine fileEngine = new FileEngine(TakeitApplication.properties.getValue("$downloadLocation"));
        fileEngine.loadFiles();
        if (fileEngine.verifyPath()){
            currentFiles.addAll(fileEngine.filesCollection);
            createLayout();
        }
        else{
            //TODO: show error message
            createEmptyLayout();
        }
    }

    /**
     * Refreshes the files in the file picker component.
     */
    public void refreshFiles(){
        FileEngine fileEngine = new FileEngine(TakeitApplication.properties.getValue("$downloadLocation"));
        fileEngine.loadFiles();
        if (fileEngine.filesCollection != null){
            currentFiles.clear();
            currentFiles.addAll(fileEngine.filesCollection);
            fileList.getDataProvider().refreshAll();
        }
    }

    /**
     * Creates the header of the file picker component.
     */
    void createHeader(){
        header = new HorizontalLayout();
        header.setWidth("100%");
        header.setAlignItems(Alignment.CENTER);
        header.setJustifyContentMode(JustifyContentMode.CENTER);

        lockButton = new Button("lock again", VaadinIcon.STOP.create(), this::lockAction);
        lockButton.getStyle().set("margin-right", "10px");
        lockButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);

        refreshButton = new Button("refresh", VaadinIcon.REFRESH.create(), this::refreshAction);
        refreshButton.getStyle().set("margin-right", "10px");
        refreshButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        FlexLayout leftSide = new FlexLayout();
        leftSide.setWidth("80%");
        leftSide.setAlignItems(Alignment.START);
        leftSide.setJustifyContentMode(JustifyContentMode.START);

        leftSide.add(new H1("pick me :3"));

        FlexLayout rightSide = new FlexLayout();
        rightSide.setWidth("20%");
        rightSide.setAlignItems(Alignment.END);
        rightSide.setJustifyContentMode(JustifyContentMode.END);

        rightSide.add(refreshButton,lockButton);

        header.add(leftSide);
        header.add(rightSide);

        add(header);
    }

    /**
     * Creates the virtual list of the file picker component.
     */
    void createVirtualList(){
        fileList = new VirtualList<>();
        fileList.setSizeFull();
        
        fileList.setItems(currentFiles);
        fileList.setRenderer(noteCardRenderer);

        add(fileList);
    }

    /**
     * Creates the renderer for the file cards.
     */
    private ComponentRenderer<Component, TakeItFile> noteCardRenderer = new ComponentRenderer<>(note ->{
        return new FileCard(note);
    });

    /**
     * Creates the layout of the file picker component.
     */
    public void createLayout(){
        createHeader();
        createVirtualList();
        
        // Add footer with version information
        addFooter();
    }

    /**
     * Creates the footer of the file picker component.
     */
    private void addFooter() {
        add(new H6("version: " + TakeitApplication.version));
    }

    /**
     * Creates the empty layout of the file picker component.
     */
    public void createEmptyLayout(){
        createHeader();
        VerticalLayout emptyLayout = new VerticalLayout();
        emptyLayout.setSizeFull();
        emptyLayout.setAlignItems(Alignment.CENTER);
        emptyLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        emptyLayout.add(new H1("no files found / no files to download"));
        add(emptyLayout);
    }

    /**
     * Handles the lock action.
     * @param event the click event
     */
    private void lockAction(ClickEvent<Button> event){
        parent.resetPage();
        Notification.show("Files locked!", 3000, Notification.Position.BOTTOM_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    /**
     * Handles the refresh action.
     * @param event the click event
     */
    private void refreshAction(ClickEvent<Button> event){
        refreshFiles();
        Notification.show("Files refreshed!", 3000, Notification.Position.BOTTOM_CENTER).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
