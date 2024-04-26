package org.example;

import lombok.experimental.UtilityClass;

import javax.swing.*;

/**
 * The type Menu bar tool.
 */
@UtilityClass
public class MenuBarTool {
    public static JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();

        // Add menus to menu bar
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(createHelpMenu());

        return menuBar;
    }

    private static JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        // Create menu items
        final JMenuItem newItem = new JMenuItem("New");
        final JMenuItem openItem = new JMenuItem("Open");
        final JMenuItem saveItem = new JMenuItem("Save");
        final JMenuItem configItem = new JMenuItem("Configuration");
        final JMenuItem closeApp = new JMenuItem("Close");

        // Add action listeners to menu items
        newItem.addActionListener(e -> App.updateStatusLabel("Status: New File Created!"));
        openItem.addActionListener(e -> App.updateStatusLabel("Status: File Opened!"));
        saveItem.addActionListener(e -> App.updateStatusLabel("Status: File Saved!"));
        closeApp.addActionListener(e -> App.showCloseConfirmDialog());
        configItem.addActionListener(e -> ConfigFrameTool.showConfigFrame());

        // Add menu items to menus
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(configItem);
        fileMenu.addSeparator();
        fileMenu.add(closeApp);

        return fileMenu;
    }

    private static JMenu createEditMenu() {
        final JMenu editMenu = new JMenu("Edit");

        // Create menu items
        final JMenuItem cutItem = new JMenuItem("Cut");
        final JMenuItem copyItem = new JMenuItem("Copy");
        final JMenuItem pasteItem = new JMenuItem("Paste");

        // Add action listeners
        cutItem.addActionListener(e -> App.updateStatusLabel("Status: Cut!"));
        copyItem.addActionListener(e -> App.updateStatusLabel("Status: Copy!"));
        pasteItem.addActionListener(e -> App.updateStatusLabel("Status: Paste!"));

        // Add to menu
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        return editMenu;
    }

    private static JMenu createHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");

        // Create menu items
        final JMenuItem userGuideItem = new JMenuItem("User Guide");
        final JMenuItem aboutItem = new JMenuItem("About");

        // Add action listeners
        userGuideItem.addActionListener(e -> App.showUserGuide());
        aboutItem.addActionListener(e -> App.showAboutDialog());

        // Add to menu
        helpMenu.add(userGuideItem);
        helpMenu.add(aboutItem);

        return helpMenu;
    }
}
