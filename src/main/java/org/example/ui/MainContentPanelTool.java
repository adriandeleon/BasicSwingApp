package org.example.ui;

import lombok.experimental.UtilityClass;
import net.miginfocom.swing.MigLayout;
import org.example.App;

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

        final JTabbedPane tabbedPane = TabPaneTool.createTabPane(App.mainFrame);
        contentPanel.add(tabbedPane, "cell 0 0,grow");

        return contentPanel;
    }
}
