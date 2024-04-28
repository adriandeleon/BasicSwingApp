package org.example.ui;

import lombok.experimental.UtilityClass;
import net.miginfocom.swing.MigLayout;
import org.example.App;

import javax.swing.*;

/**
 * The type Status panel tool.
 */
@UtilityClass
public class StatusPanelTool {

    /**
     * Sets status bar.
     *
     * @param statusLabel the status label
     * @return the status bar
     */
    public static JPanel setupStatusBar(final JLabel statusLabel) {
        final JPanel statusPanel = new JPanel(new MigLayout("", "[grow]", "[]"));
        statusPanel.add(statusLabel);

        return statusPanel;
    }

    /**
     * Update status label.
     *
     * @param newText the new text
     */
    public static void updateStatusLabel(final String newText) {
        SwingUtilities.invokeLater(() -> App.statusLabel.setText(newText));
    }
}
