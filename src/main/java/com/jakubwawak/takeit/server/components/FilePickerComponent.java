/**
 * @author Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.takeit.server.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;

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

    HorizontalLayout header;

    MainPage parent;

    /**
     * Constructor for the FilePickerComponent class.
     */
    public FilePickerComponent(MainPage mainPage){
        parent = mainPage;

        getStyle().set("border", "1px solid black");
        this.setSizeFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);

        FileEngine fileEngine = new FileEngine(TakeitApplication.properties.getValue("$downloadLocation"));
        fileEngine.loadFiles();
        if (fileEngine.filesCollection != null){
            currentFiles.addAll(fileEngine.filesCollection);
            createLayout();
            createVirtualList();
        }
        else{
            //TODO: show error message
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

        FlexLayout leftSide = new FlexLayout();
        leftSide.setWidth("80%");
        leftSide.setAlignItems(Alignment.START);
        leftSide.setJustifyContentMode(JustifyContentMode.START);

        leftSide.add(new H1("pick me :3"));

        FlexLayout rightSide = new FlexLayout();
        rightSide.setWidth("20%");
        rightSide.setAlignItems(Alignment.END);
        rightSide.setJustifyContentMode(JustifyContentMode.END);

        rightSide.add(lockButton);

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
        add(fileList);
    }

    /**
     * Creates the layout of the file picker component.
     */
    public void createLayout(){
        createHeader();
    }

    /**
     * Handles the lock action.
     * @param event the click event
     */
    private void lockAction(ClickEvent<Button> event){
        parent.resetPage();
    }
}
