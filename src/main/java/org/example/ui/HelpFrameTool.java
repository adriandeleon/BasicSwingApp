package org.example.ui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import lombok.experimental.UtilityClass;
import org.example.App;

import javax.swing.*;
import java.util.Objects;

@UtilityClass
public class HelpFrameTool {
    public static void showHelpFrame(final JFrame frame) {
        Objects.requireNonNull(frame, "frame must not be null");

        if(App.helpFrame == null) {

            final JFrame helpFrame = new JFrame("Help");
            final JFXPanel jfxPanel = new JFXPanel();

            helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            helpFrame.setSize(600, 800);
            helpFrame.setLocationRelativeTo(frame);

            helpFrame.add(jfxPanel);

            Platform.runLater(() -> {
                final WebView webView = new WebView();
                try {
                    webView.getEngine().load("http://example.com");
                } catch (Exception e) {
                    // Insert appropriate error handling here
                }
                jfxPanel.setScene(new Scene(webView));
            });

            helpFrame.setVisible(true);
            App.updateHelpFrame(helpFrame);
        }else {
            App.helpFrame.setVisible(true);
        }
    }
}
