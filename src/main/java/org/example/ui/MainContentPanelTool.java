package org.example.ui;

import lombok.experimental.UtilityClass;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * The type Main content panel tool.
 */
@UtilityClass
public class MainContentPanelTool {

    /**
     * Sets content panel.
     *
     * @return the content panel
     */
    public static JPanel setupContentPanel() {
        final JPanel contentPanel = new JPanel(new MigLayout("", "[grow]", "[]10[]"));
        final JLabel label = new JLabel("Hello, Swing!");
        final JButton button = new JButton("Click Me");

        button.addActionListener(e -> StatusPanelTool.updateStatusLabel("Status: Button Clicked!"));
        contentPanel.add(label, "wrap");
        contentPanel.add(button);

        return contentPanel;
    }
}
