package org.example;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import com.formdev.flatlaf.util.SystemInfo;
import com.typesafe.config.Config;
import net.miginfocom.swing.MigLayout;
import org.example.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/// The App class serves as the main entry point for the Swing-based application.
/// It is responsible for initializing the user interface and configuring is necessary settings
/// for different operating systems.
///  This class also sets up the main application frame,
/// status bar, menus, and system tray functionality.
public class App {
    public static JLabel statusLabel;
    public static JFrame mainFrame;
    public static JFrame configFrame;
    public static JFrame helpFrame;
    public static Config config;

    /// The main entry point for the application.
    /// Initializes and sets up the application's main graphical user interface (GUI),
    /// including configuration loading, theme setup, toolbar, content panel, and system tray.
    ///
    public static void main() {
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

    /// Configures and initializes the FlatLaf look-and-feel settings for the application.
    /// This method adjusts platform-specific properties and preferences for macOS, Linux, and
    /// Windows to ensure a consistent appearance and behavior of the user interface.
    ///  It also installs custom fonts and applies a specific theme using FlatLaf.
    /// Key setups include:
    /// - On macOS, enables the screen menu bar, sets the application name, and configures
    ///   the appearance of window title bars.
    /// - On Linux, enables custom window decorations for JFrame and JDialog components.
    /// - Applies custom font preferences for the application UI using FlatLaf.
    /// - Configures FlatLaf to use the FlatLightLaf theme as the preferred style.
    private static void setupFlatLaf() {
        if (SystemInfo.isMacOS) {
            // Enable a screen menu bar.
            // (moves menu bar from JFrame window to top of screen)
            System.setProperty("apple.laf.useScreenMenuBar", "true");

            // application name used in the screen menu bar
            // (in the first menu after the "apple" menu)
            System.setProperty("apple.awt.application.name", "SwingApp");

            // Appearance of window title bars.
            // possible values:
            //   - "system": use current macOS appearance (light or dark)
            //   - "NSAppearanceNameAqua": use light appearance
            //   - "NSAppearanceNameDarkAqua": use dark appearance
            // (must be set on the main thread and before AWT/Swing is initialized;
            //  setting it on AWT thread does not work)
            System.setProperty("apple.awt.application.appearance", "system");
        }

        // Linux
        if (SystemInfo.isLinux) {
            // Enable custom window decorations.
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        }

        if(SystemInfo.isWindows) {
            //Do Windows stuff here.
        }
        FlatJetBrainsMonoFont.install();

        FlatLaf.setPreferredFontFamily(FlatInterFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatInterFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatInterFont.FAMILY_SEMIBOLD);

        UIManager.put("MenuItem.selectionType", "underline");

        FlatLightLaf.setup();
    }

    /// Sets up the main application frame.
    /// This method initializes the application's primary graphical user interface
    /// component, including setting the frame title, size, and default close
    /// operation.
    ///  Additionally, it registers a window listener to handle
    /// custom behavior when the user attempts to close the application, such
    /// as prompting for confirmation using a dialog.
    /// Key configurations include:
    /// - Setting the title of the frame to "My Swing App".
    /// - Defining the default close operation as DO_NOTHING_ON_CLOSE to ensure
    ///   the application does not close immediately when the close button is clicked.
    /// - Adding a `WindowListener` to provide a confirmation dialog before closing.
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

    /// Updates the application's help frame.
    /// This method sets the provided JFrame instance as the new help frame
    /// within the application using the Event Dispatch Thread (EDT).
    ///
    /// @param frame the new JFrame to be set as the help frame
    public static void updateHelpFrame(final JFrame frame) {
        SwingUtilities.invokeLater(() -> App.helpFrame = frame);
    }
}