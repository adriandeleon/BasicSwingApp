package org.example.ui;

import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;

/**
 * The type Tool bar tool.
 */
@UtilityClass
public class ToolBarTool {
    /**
     * Sets toolbar.
     *
     * @param mainFrame the main frame
     * @return the toolbar
     */
    public static JToolBar setupToolbar(final JFrame mainFrame) {
        // Set up a toolbar.
        final JToolBar toolBar = new JToolBar();
        final JButton btnOpen = new JButton(createImageIcon("/icons/icons8-open-50.png", "Open")); // Add your icon path here

        btnOpen.setToolTipText("Open");
        btnOpen.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Open menu item clicked"));
        toolBar.add(btnOpen);

        final JButton btnCut = new JButton(createImageIcon("/icons/icons8-cut-50.png", "Cut")); // Add your icon path here
        btnCut.setToolTipText("Cut");
        btnCut.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Cut action triggered"));
        toolBar.add(btnCut);

        final JButton btnCopy = new JButton(createImageIcon("/icons/icons8-copy-50.png", "Copy")); // Add your icon path here
        btnCopy.setToolTipText("Copy");
        btnCopy.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Copy action triggered"));
        toolBar.add(btnCopy);

        final JButton btnPaste = new JButton(createImageIcon("/icons/icons8-paste-50.png", "Paste")); // Add your icon path here
        btnPaste.setToolTipText("Paste");
        btnPaste.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Paste action triggered"));
        toolBar.add(btnPaste);

        return toolBar;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    private static ImageIcon createImageIcon(final String path, final String description) {
        //TODO: use an Optional?
        java.net.URL imgURL = ToolBarTool.class.getResource(path);

        if (imgURL != null) {
            ImageIcon originalIcon = new ImageIcon(imgURL, description);
            Image originalImage = originalIcon.getImage();
            Image resizedImage = originalImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
