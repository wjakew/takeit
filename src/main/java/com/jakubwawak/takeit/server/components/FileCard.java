/**
 * @author Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.takeit.server.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.io.File;

import com.jakubwawak.takeit.entity.TakeItFile;
/**
 * File card component - for showing file information in the file picker component.
 */
public class FileCard extends HorizontalLayout{

    TakeItFile file;

    Button downloadButton;
    
    /**
     * Constructor for the FileCard class.
     */
    public FileCard(TakeItFile file){
        super();
        setWidth("100%");
        setHeight("200px");
        getStyle().set("border", "1px solid black");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        this.file = file;   
        createLayout();
    }

    /**
     * Creates the layout of the file card.
     */
    public void createLayout(){

        downloadButton = new Button("Download", VaadinIcon.DOWNLOAD.create(), this::downloadAction);
        downloadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        
        FlexLayout leftLayout = new FlexLayout();
        leftLayout.setWidth("100%");
        leftLayout.setAlignItems(Alignment.START);
        leftLayout.setJustifyContentMode(JustifyContentMode.START);

        leftLayout.add(new VerticalLayout(new H6(file.fileCreationDate), new H6(file.fileExtension)));

        FlexLayout centerLayout = new FlexLayout();
        centerLayout.setWidth("100%");
        centerLayout.setAlignItems(Alignment.CENTER);
        centerLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        centerLayout.add(new H6(file.fileName));

        FlexLayout rightLayout = new FlexLayout();
        rightLayout.setWidth("100%");
        rightLayout.setAlignItems(Alignment.END);
        rightLayout.setJustifyContentMode(JustifyContentMode.END);
        rightLayout.getStyle().set("margin-right", "10px");

        rightLayout.add(downloadButton);

        add(leftLayout);
        add(centerLayout);
        add(rightLayout);
    }

    /**
     * Action for the download button.
     */
    private void downloadAction(ClickEvent<Button> event){
        File fileToDownload = new File(file.fileAbsolutePath);
        if ( fileToDownload.exists() ){
            FileDownloaderComponent fileDownloaderComponent = new FileDownloaderComponent(fileToDownload);
            add(fileDownloaderComponent.dialog);
            fileDownloaderComponent.dialog.open();
        }
        else{
            Notification.show("File does not exist", 3000, Notification.Position.MIDDLE);
        }
    }
        
}
