package org.example;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import lombok.experimental.UtilityClass;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang3.Validate;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static org.example.App.*;

@UtilityClass
public class ConfigFrameTool {

    private static JPanel detailPanel;
    public static final String OPTION_APPEARANCE = "Appearance";
    public static final String OPTION_EDITOR = "Editor";
    public static final String OPTION_PLUGINS = "Plugins";

    public static void showConfigFrame() {
        if (configFrame == null) {
            configFrame = new JFrame("Configuration");
            configFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            configFrame.setSize(600, 400);
            configFrame.setLocationRelativeTo(mainFrame);

            JPanel configPanel = new JPanel(new MigLayout("", "[200!][grow]", "[grow]"));

            // Create the master panel
            final JPanel masterPanel = new JPanel(new MigLayout("", "[grow]", "[grow]"));
            masterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Create the list of configuration options
            final DefaultListModel<String> optionsModel = new DefaultListModel<>();
            optionsModel.addElement(OPTION_APPEARANCE);
            optionsModel.addElement(OPTION_EDITOR);
            optionsModel.addElement(OPTION_PLUGINS);

            final JList<String> optionsList = new JList<>(optionsModel);
            optionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            optionsList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    String selectedOption = optionsList.getSelectedValue();
                    updateDetailPanel(selectedOption);
                }
            });

            final JScrollPane optionsScrollPane = new JScrollPane(optionsList);
            masterPanel.add(optionsScrollPane, "grow");

            // Create the detail panel
            detailPanel = new JPanel(new MigLayout("", "[]10[grow]", "[][]"));
            detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add the master and detail panels to the config panel
            configPanel.add(masterPanel, "grow");
            configPanel.add(detailPanel, "grow");

            configFrame.getContentPane().add(configPanel);
        }
        updateDetailPanel(OPTION_APPEARANCE);
        configFrame.setVisible(true);
    }

    private static void updateDetailPanel(final String selectedOption) {
        detailPanel.removeAll();

         final String[] themeItems = {"Light", "Dark"};

        switch (selectedOption) {
            // Add appearance configuration components to the detail panel
            case OPTION_APPEARANCE -> {
                appearanceDetailPanel(themeItems);
            }
            case OPTION_EDITOR -> {
                editorDetailsPanel();
            }
            case OPTION_PLUGINS -> {
                pluginsDetailPanel();
            }
        }
        detailPanel.revalidate();
        detailPanel.repaint();
    }

    private static void appearanceDetailPanel(final String[] themeItems) {
        final JLabel themeLabel = new JLabel("Theme:");
        final JComboBox<String> themeComboBox = new JComboBox<>(themeItems);

        themeComboBox.setSelectedItem(config.getString(ConfigTool.CONFIG_STRING_APP_UI_THEME));

        final JLabel fontSizeLabel = new JLabel("Font Size:");
        final JSpinner fontSizeSpinner = new JSpinner(new SpinnerNumberModel(12, 8, 24, 1));

        fontSizeSpinner.setValue(config.getInt(ConfigTool.CONFIG_STRING_APP_UI_FONT_SIZE));

        final JButton applyButton = new JButton("Apply");

        // Apply the appearance configuration settings
        SwingUtilities.invokeLater(() -> {
            applyButton.addActionListener(e -> {
                final String selectedTheme = (String) themeComboBox.getSelectedItem();
                int fontSize = (int) fontSizeSpinner.getValue();
                applyConfiguration(selectedTheme, fontSize);
            });
        });

        detailPanel.add(themeLabel);
        detailPanel.add(themeComboBox, "wrap");
        detailPanel.add(fontSizeLabel);
        detailPanel.add(fontSizeSpinner, "wrap");
        detailPanel.add(applyButton, "span, align right");
    }

    private static void editorDetailsPanel() {
        // Add editor configuration components to the detail panel.
        final JLabel tabSizeLabel = new JLabel("Tab Size:");
        final JSpinner tabSizeSpinner = new JSpinner(new SpinnerNumberModel(4, 2, 8, 1));
        final JCheckBox autoIndentCheckBox = new JCheckBox("Auto Indent");
        final JButton applyButton = new JButton("Apply");


        SwingUtilities.invokeLater(() -> {
            applyButton.addActionListener(e -> {
                if(tabSizeSpinner.getValue() instanceof Integer) {
                    int tabSize = (int) tabSizeSpinner.getValue();
                }
                boolean autoIndent = autoIndentCheckBox.isSelected();
            });
        });

        detailPanel.add(tabSizeLabel);
        detailPanel.add(tabSizeSpinner, "wrap");
        detailPanel.add(autoIndentCheckBox, "span");
        detailPanel.add(applyButton, "span, align right");
    }

    private static void pluginsDetailPanel() {
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

    public static void applyConfiguration(final String theme, final int fontSize) {
        Validate.notBlank(theme, "Theme cannot be blank");

        // Apply the selected theme.
        if (theme.equals("Dark")) {
            ConfigTool.writeConfig(Map.of(ConfigTool.CONFIG_STRING_APP_UI_THEME, "Dark"));
            FlatDarkLaf.setup();
        } else {
            ConfigTool.writeConfig(Map.of(ConfigTool.CONFIG_STRING_APP_UI_THEME, "Light"));
            FlatLightLaf.setup();
        }

        // Set the font size for labels and buttons.
        final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, fontSize);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);

        ConfigTool.writeConfig(Map.of(ConfigTool.CONFIG_STRING_APP_UI_FONT_SIZE, fontSize));

        // Update the UI components.
        SwingUtilities.invokeLater(() -> {
            SwingUtilities.updateComponentTreeUI(mainFrame);
            SwingUtilities.updateComponentTreeUI(configFrame);
        });
    }
}
