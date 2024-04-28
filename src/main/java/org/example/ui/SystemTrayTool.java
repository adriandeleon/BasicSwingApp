package org.example.ui;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


/**
 * The type System tray tool.
 */
@UtilityClass
public class SystemTrayTool {

    /**
     * Create a system tray.
     *
     * @param frame the frame
     */
    public static void createSystemTray(final JFrame frame) {
        if (SystemTray.isSupported()) {
            // Get the system tray instance
            final SystemTray tray = SystemTray.getSystemTray();
            Image image;

            // Create a tray icon
            try (InputStream is = Objects.requireNonNull(SystemTrayTool.class.getResourceAsStream("/icons/tray-icon.png"))) {
                image = ImageIO.read(is);
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
                if (!frame.isVisible()) {
                    frame.setVisible(true);
                }

                if (frame.getState() == JFrame.ICONIFIED) {
                    frame.setState(JFrame.NORMAL);
                }
            });
            exitItem.addActionListener(e -> CommonDialogsTool.showCloseConfirmDialog(frame));

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
