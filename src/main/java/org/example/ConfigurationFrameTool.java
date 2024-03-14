package org.example;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import lombok.experimental.UtilityClass;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

import static org.example.App.configFrame;
import static org.example.App.mainFrame;

@UtilityClass
public class ConfigurationFrameTool {

    private static JPanel detailPanel;

    public static void showConfigFrame() {
        if (configFrame == null) {
            configFrame = new JFrame("Configuration");
            configFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            configFrame.setSize(600, 400);
            configFrame.setLocationRelativeTo(mainFrame);

            JPanel configPanel = new JPanel(new MigLayout("", "[200!][grow]", "[grow]"));

            // Create the master panel
            JPanel masterPanel = new JPanel(new MigLayout("", "[grow]", "[grow]"));
            masterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Create the list of configuration options
            DefaultListModel<String> optionsModel = new DefaultListModel<>();
            optionsModel.addElement("Appearance");
            optionsModel.addElement("Editor");
            optionsModel.addElement("Plugins");
            JList<String> optionsList = new JList<>(optionsModel);
            optionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            optionsList.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        String selectedOption = optionsList.getSelectedValue();
                        updateDetailPanel(selectedOption);
                    }
                }
            });
            JScrollPane optionsScrollPane = new JScrollPane(optionsList);
            masterPanel.add(optionsScrollPane, "grow");

            // Create the detail panel
            detailPanel = new JPanel(new MigLayout("", "[]10[grow]", "[][]"));
            detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the master and detail panels to the config panel
            configPanel.add(masterPanel, "grow");
            configPanel.add(detailPanel, "grow");

            configFrame.getContentPane().add(configPanel);
        }

        configFrame.setVisible(true);
    }

    private static void updateDetailPanel(String selectedOption) {
        detailPanel.removeAll();

        switch (selectedOption) {
            case "Appearance" -> {
                // Add appearance configuration components to the detail panel
                final JLabel themeLabel = new JLabel("Theme:");
                final JComboBox<String> themeComboBox = new JComboBox<>(new String[]{"Light", "Dark"});
                final JLabel fontSizeLabel = new JLabel("Font Size:");
                final JSpinner fontSizeSpinner = new JSpinner(new SpinnerNumberModel(12, 8, 24, 1));
                final JButton applyButton = new JButton("Apply");

                applyButton.addActionListener(e -> {
                    // Apply the appearance configuration settings
                    final String selectedTheme = (String) themeComboBox.getSelectedItem();
                    int fontSize = (int) fontSizeSpinner.getValue();
                    applyConfiguration(selectedTheme, fontSize);
                });

                detailPanel.add(themeLabel);
                detailPanel.add(themeComboBox, "wrap");
                detailPanel.add(fontSizeLabel);
                detailPanel.add(fontSizeSpinner, "wrap");
                detailPanel.add(applyButton, "span, align right");
            }
            case "Editor" -> {
                // Add editor configuration components to the detail panel
                final JLabel tabSizeLabel = new JLabel("Tab Size:");
                final JSpinner tabSizeSpinner = new JSpinner(new SpinnerNumberModel(4, 2, 8, 1));
                final JCheckBox autoIndentCheckBox = new JCheckBox("Auto Indent");
                final JButton applyButton = new JButton("Apply");

                // Apply the editor configuration settings
                applyButton.addActionListener(e -> {
                    int tabSize = (int) tabSizeSpinner.getValue();
                    boolean autoIndent = autoIndentCheckBox.isSelected();
                    // ...
                });

                detailPanel.add(tabSizeLabel);
                detailPanel.add(tabSizeSpinner, "wrap");
                detailPanel.add(autoIndentCheckBox, "span");
                detailPanel.add(applyButton, "span, align right");
            }
            case "Plugins" -> {
                // Add plugins configuration components to the detail panel
                final JLabel pluginsLabel = new JLabel("Installed Plugins:");
                final DefaultListModel<String> pluginsModel = new DefaultListModel<>();

                pluginsModel.addElement("Plugin 1");
                pluginsModel.addElement("Plugin 2");
                pluginsModel.addElement("Plugin 3");

                final JList<String> pluginsList = new JList<>(pluginsModel);
                final JScrollPane pluginsScrollPane = new JScrollPane(pluginsList);
                final JButton installButton = new JButton("Install");

                installButton.addActionListener(e -> {
                    // Show plugin installation dialog or perform installation
                    // ...
                });

                detailPanel.add(pluginsLabel, "wrap");
                detailPanel.add(pluginsScrollPane, "grow, span");
                detailPanel.add(installButton, "span, align right");
            }
        }
        detailPanel.revalidate();
        detailPanel.repaint();
    }

    public static void applyConfiguration(String theme, int fontSize) {
        // Apply the selected theme
        if (theme.equals("Light")) {
            FlatLightLaf.setup();
        } else if (theme.equals("Dark")) {
            FlatDarkLaf.setup();
        }

        // Set the font size for labels and buttons
        final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, fontSize);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);

        // Update the UI components
        SwingUtilities.updateComponentTreeUI(mainFrame);
        SwingUtilities.updateComponentTreeUI(configFrame);
    }
}
