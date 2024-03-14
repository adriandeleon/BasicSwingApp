package org.example;

import lombok.experimental.UtilityClass;

import javax.swing.*;

import static org.example.App.*;
import static org.example.ConfigurationFrameTool.showConfigFrame;

/**
 * The type Menu bar tool.
 */
@UtilityClass
public class MenuBarTool {
    /**
     * Create menu bar j menu bar.
     *
     * @return the j menu bar
     */
    public static JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();

        // Create menus
        final JMenu fileMenu = new JMenu("File");
        final JMenu editMenu = new JMenu("Edit");
        final JMenu helpMenu = new JMenu("Help");

        // Create menu items
        final JMenuItem newItem = new JMenuItem("New");
        final JMenuItem openItem = new JMenuItem("Open");
        final JMenuItem saveItem = new JMenuItem("Save");
        final JMenuItem configItem = new JMenuItem("Configuration");
        final JMenuItem closeApp = new JMenuItem("Close");

        final JMenuItem cutItem = new JMenuItem("Cut");
        final JMenuItem copyItem = new JMenuItem("Copy");
        final JMenuItem pasteItem = new JMenuItem("Paste");

        final JMenuItem userGuideItem = new JMenuItem("User Guide");
        final JMenuItem aboutItem = new JMenuItem("About");

        // Add action listeners to menu items
        newItem.addActionListener(e -> App.statusLabel.setText("Status: New File Created!"));
        openItem.addActionListener(e -> App.statusLabel.setText("Status: File Opened!"));
        saveItem.addActionListener(e -> App.statusLabel.setText("Status: File Saved!"));
        closeApp.addActionListener(e -> showCloseConfirmDialog());

        configItem.addActionListener(e -> showConfigFrame());

        userGuideItem.addActionListener(e -> showUserGuide());
        aboutItem.addActionListener(e -> showAboutDialog());

        // Add menu items to menus
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(configItem);
        fileMenu.addSeparator();
        fileMenu.add(closeApp);

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);



        helpMenu.add(userGuideItem);
        helpMenu.add(aboutItem);

        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(Box.createHorizontalGlue()); // Add a horizontal glue to push the help menu to the right
        menuBar.add(helpMenu);

        return menuBar;
    }
}
