package org.example.ui;

import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;

@UtilityClass
public class TabPaneTool {

    public static JTabbedPane tabbedPane;
    public static JTabbedPane createTabPane(final JFrame frame) {
        // Tabbed pane
        tabbedPane = new JTabbedPane();

        final JPanel editPanel = createEditPanel();
        final JPanel systemInfoPanel = createSystemInfoPanel();

        tabbedPane.addTab("Edit Me", editPanel);
        tabbedPane.addTab("System Information", systemInfoPanel);

        // Adding the tabbed pane to the center of the frame
        frame.add(tabbedPane, BorderLayout.CENTER);

        return tabbedPane;
    }

    public static JPanel createEditPanel(){
        final JPanel editPanel = new JPanel();

        final JLabel label = new JLabel("Hello, Swing!");
        final JButton button = new JButton("Click Me");

        button.addActionListener(e -> StatusPanelTool.updateStatusLabel("Status: Button Clicked!"));
        editPanel.add(label, "wrap");
        editPanel.add(button);

        return editPanel;
    }

    public static JPanel createSystemInfoPanel(){
        final JPanel systemInfoPanel = new JPanel();
        systemInfoPanel.add(new JLabel("System Information"));

        return systemInfoPanel;
    }
}
