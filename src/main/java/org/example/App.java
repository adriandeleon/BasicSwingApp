package org.example;

import com.typesafe.config.Config;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The App.
 */
public class App {
    public static JLabel statusLabel;
    public static JFrame mainFrame;
    public static JFrame configFrame;
    public static Config config;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        mainFrame = new JFrame("My Swing App");
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setSize(700, 600);

        //use the close confirmation dialog when trying to close the app.
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               showCloseConfirmDialog();
            }
        });

        //final JPanel panel = new JPanel();
        final JPanel panel = new JPanel(new MigLayout("", "[grow]", "[]10[]"));
        final JLabel label = new JLabel("Hello, Swing!");
        final JButton button = new JButton("Click Me");

        button.addActionListener(e -> statusLabel.setText("Status: Button Clicked!"));
        panel.add(label, "wrap");
        panel.add(button);

        // Set the menu bar for the frame
        mainFrame.setJMenuBar(MenuBarTool.createMenuBar());

        // Create a status bar.
        final JPanel statusPanel = new JPanel(new MigLayout("", "[grow]", "[]"));
        statusLabel = new JLabel("Status: Ready");
        statusPanel.add(statusLabel);

        // Add the content panel and status bar to the frame.
        mainFrame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][]"));
        mainFrame.getContentPane().add(panel, "grow");
        mainFrame.getContentPane().add(statusPanel, "dock south");


        SystemTrayTool.createSystemTray(mainFrame);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);


        if (ConfigTool.readConfig().isEmpty()) {
            showErrorDialog("Cannot read configuration file");
        }
        config = ConfigTool.readConfig().get();

        ConfigFrameTool.applyConfiguration(config.getString(ConfigTool.CONFIG_STRING_APP_UI_THEME),
                config.getInt(ConfigTool.CONFIG_STRING_APP_UI_FONT_SIZE));
    }

    /**
     * Show close confirm dialog.
     */
    public static void showCloseConfirmDialog() {
        if (mainFrame != null && mainFrame.isVisible()) {
            final JOptionPane optionPane = new JOptionPane("Are you sure you want to close the application?",
                    JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);

            final JDialog dialog = optionPane.createDialog(mainFrame, "Confirm Close.");

            dialog.setLocationRelativeTo(null); // Center the dialog on the screen
            dialog.setVisible(true);

            Integer value = (Integer) optionPane.getValue();
            if (value != null && value == JOptionPane.YES_OPTION) {
                mainFrame.dispose(); // or any other suitable method to close the window
                // Perform any necessary cleanup or state saving here
                System.exit(0);
            }
        }
    }

    /**
     * Show about dialog.
     */
    public static void showAboutDialog() {
        final String message = """
                My Swing App
                Version 1.0
                Developed by Your Name
                """;
        final String title = "About";
        showMessageDialog(message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show the user guide.
     */
    public static void showUserGuide() {
        final String message = """
                 Welcome to My Swing App!

                This is a sample user guide.
                Please refer to the documentation for more information.
                """;
        final String title = "User Guide";
        showMessageDialog(message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorDialog(final String message) {
        final String title = "error";
        showMessageDialog(message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showMessageDialog(final String message, final String title, final Integer optionPaneMessageType) {
        if (mainFrame != null && mainFrame.isVisible()) {
            JOptionPane.showMessageDialog(mainFrame, message, title, optionPaneMessageType);
        }
    }

    public static void updateStatusLabel(final String newText) {
        SwingUtilities.invokeLater(() -> statusLabel.setText(newText));
    }
}