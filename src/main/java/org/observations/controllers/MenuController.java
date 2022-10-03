package org.observations.controllers;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuController {

    private final MainController controller;
    private final MenuBar menuBar = new MenuBar();
    private Menu fileMenu;
    private Menu viewMenu;
    private MenuItem settings;
    private MenuItem export;
    private MenuItem charts;

    public MenuController(MainController mainController) {
        this.controller = mainController;
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
