package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import com.typesafe.config.Config;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

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
        FlatLightLaf.setup();

        mainFrame = new JFrame("My Swing App");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(700, 600);

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

        config = ConfigTool.readConfig();

        ConfigFrameTool.applyConfiguration(config.getString(ConfigTool.CONFIG_STRING_APP_UI_THEME),
                config.getInt(ConfigTool.CONFIG_STRING_APP_UI_FONT_SIZE));
    }

    /**
     * Show about dialog.
     *
     */
    public static void showAboutDialog() {
        JOptionPane.showMessageDialog(mainFrame, "My Swing App\nVersion 1.0\nDeveloped by Your Name",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show the user guide.
     *
     */
    public static void showUserGuide() {
        JOptionPane.showMessageDialog(mainFrame, """
                Welcome to My Swing App!

                This is a sample user guide.
                Please refer to the documentation for more information.""", "User Guide", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show close confirm dialog.
     *
     */
    public static void showCloseConfirmDialog() {
        final JOptionPane optionPane = new JOptionPane("Are you sure you want to close the application?",
                JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);

        final JDialog dialog = optionPane.createDialog(mainFrame, "Confirm Close");

        dialog.setLocationRelativeTo(null); // Center the dialog on the screen
        dialog.setVisible(true);

        int option = (Integer) optionPane.getValue();
        if (option == JOptionPane.YES_OPTION) {
           System.exit(0);
        }
    }
}