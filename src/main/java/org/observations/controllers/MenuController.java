package org.observations.controllers;

import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuController {

    private final MainWindowController controller;
    private final MenuBar menuBar = new MenuBar();
    private final Menu fileMenu;
    private final Menu viewMenu;
    private final MenuItem settings;
    private final MenuItem export;
    private final MenuItem charts;

    public MenuController(MainWindowController mainWindowController) {
        this.controller = mainWindowController;
        settings = new MenuItem("Opzioni");
        export = new MenuItem("Esporta");

        fileMenu = new Menu("File");
        fileMenu.getItems().addAll(settings, export);


        charts = new MenuItem("Grafici");

        viewMenu = new Menu("View");
        viewMenu.getItems().addAll(charts);

        menuBar.getMenus().addAll(fileMenu, viewMenu);
    }

    public Node getView() {
        return this.menuBar;
    }
}
