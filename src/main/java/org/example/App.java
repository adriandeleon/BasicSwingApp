package org.example;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.util.SystemInfo;
import com.typesafe.config.Config;
import net.miginfocom.swing.MigLayout;
import org.example.ui.*;

import javax.swing.*;
import java.awt.*;
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
        setupFlatLaf();

        SwingUtilities.invokeLater(() -> {
            if (ConfigTool.readConfig().isEmpty()) {
                CommonDialogsTool.showErrorDialog(mainFrame, "Cannot read configuration file");
            }

            config = ConfigTool.readConfig().get();

            ConfigFrameTool.applyConfiguration(config.getString(ConfigTool.CONFIG_STRING_APP_UI_THEME),
                    config.getInt(ConfigTool.CONFIG_STRING_APP_UI_FONT_SIZE));

            // Do all the main frame setup.
            setupMainFrame();

            mainFrame.setJMenuBar(MenuBarTool.createMenuBar());

            final JToolBar toolBar = ToolBarTool.setupToolbar(mainFrame);
            final JPanel contentPanel = MainContentPanelTool.setupContentPanel();

            statusLabel = new JLabel("Status: Ready");
            final JPanel statusPanel = StatusPanelTool.setupStatusBar(statusLabel);

            // Add the content contentPanel, toolbar and status bar to the frame.
            mainFrame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][]"));
            mainFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
            mainFrame.getContentPane().add(contentPanel, "grow");
            mainFrame.getContentPane().add(statusPanel, "dock south");

            SystemTrayTool.createSystemTray(mainFrame);

            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }

    private static void setupFlatLaf() {
        if (SystemInfo.isMacOS) {
            // enable screen menu bar
            // (moves menu bar from JFrame window to top of screen)
            System.setProperty("apple.laf.useScreenMenuBar", "true");

            // application name used in screen menu bar
            // (in first menu after the "apple" menu)
            System.setProperty("apple.awt.application.name", "FlatLaf Demo");

            // appearance of window title bars
            // possible values:
            //   - "system": use current macOS appearance (light or dark)
            //   - "NSAppearanceNameAqua": use light appearance
            //   - "NSAppearanceNameDarkAqua": use dark appearance
            // (must be set on main thread and before AWT/Swing is initialized;
            //  setting it on AWT thread does not work)
            System.setProperty("apple.awt.application.appearance", "system");
        }

        // Linux
        if (SystemInfo.isLinux) {
            // enable custom window decorations
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        }

        if(SystemInfo.isWindows) {
            //Do Windows stuff here
        }

        FlatLaf.setPreferredFontFamily(FlatInterFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatInterFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatInterFont.FAMILY_SEMIBOLD);

        UIManager.put("MenuItem.selectionType", "underline");

        FlatLightLaf.setup();
    }

    private static void setupMainFrame() {
        // Create the main frame of the app.
        mainFrame = new JFrame("My Swing App");
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setSize(700, 600);

        // Use the close confirmation dialog when trying to close the app.
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CommonDialogsTool.showCloseConfirmDialog(mainFrame);
            }
        });
    }
}