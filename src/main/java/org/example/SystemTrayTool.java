package org.example;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

import static org.example.App.showCloseConfirmDialog;

/**
 * The type System tray tool.
 */
@UtilityClass
public class SystemTrayTool {

    /**
     * Create system tray.
     *
     * @param frame the frame
     */
    public static void createSystemTray(final JFrame frame) {
        if (SystemTray.isSupported()) {
            // Get the system tray instance
            final SystemTray tray = SystemTray.getSystemTray();
            Image image;

            // Create a tray icon
            try {
                 image= ImageIO.read(Objects.requireNonNull(SystemTrayTool.class.getResource("/icons/tray-icon.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            final TrayIcon trayIcon = new TrayIcon(image, "My Swing App");
            trayIcon.setImageAutoSize(true);

            // Add a mouse listener to the tray icon
            trayIcon.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        frame.setVisible(true);
                        frame.setState(JFrame.NORMAL);
                    }
                }
            });

            // Create a popup menu for the tray icon
            final PopupMenu popupMenu = new PopupMenu();
            final MenuItem openItem = new MenuItem("Open");
            final MenuItem exitItem = new MenuItem("Exit");

            openItem.addActionListener(e -> {
                frame.setVisible(true);
                frame.setState(JFrame.NORMAL);
            });

            exitItem.addActionListener(e -> showCloseConfirmDialog());

            popupMenu.add(openItem);
            popupMenu.add(exitItem);
            trayIcon.setPopupMenu(popupMenu);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }
}
