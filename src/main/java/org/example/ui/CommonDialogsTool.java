package org.example.ui;

import org.apache.commons.lang3.Validate;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * The type Common dialogs tool.
 */
public class CommonDialogsTool {

    private static final String MESSAGE_MAIN_FRAME_CANNOT_BE_NULL = "mainFrame cannot be null.";
    private static final String MESSAGE_CANNOT_BE_BLANK_OR_NULL = "message cannot be blank or null.";
    private static final String MESSAGE_TITLE_CANNOT_BE_BLANK_OR_NULL = "title cannot be blank or null.";
    private static final String MESSAGE_OPTION_PANEL_MESSAGE_TYPE = "optionPanelMessageType cannot be blank or null.";

    /**
     * Show close confirm dialog.
     *
     * @param mainFrame the main frame
     */
    public static void showCloseConfirmDialog(final JFrame mainFrame) {
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
     *
     * @param mainFrame the main frame
     */
    public static void showAboutDialog(final JFrame mainFrame) {
        Objects.requireNonNull(mainFrame, MESSAGE_MAIN_FRAME_CANNOT_BE_NULL);

        final MavenXpp3Reader reader = new MavenXpp3Reader();
        final Model model;

        //FIXME: this does not work on Linux or MacOS.
        final InputStream inputStream = CommonDialogsTool.class.getResourceAsStream("/pom.xml");
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            model = reader.read(inputStreamReader);
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }

        final String message = model.getName() +
                """
                        \nVersion:\s""" + model.getVersion() +
                """
                        \nDeveloped by:\s""" + model.getProperties().getProperty("author");

        final String title = "About";
        showMessageDialog(mainFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show the user guide.
     *
     * @param mainFrame the main frame
     */
    public static void showUserGuide(final JFrame mainFrame) {
        Objects.requireNonNull(mainFrame, MESSAGE_MAIN_FRAME_CANNOT_BE_NULL);

        final String message = """
                 Welcome to My Swing App!

                This is a sample user guide.
                Please refer to the documentation for more information.
                """;
        final String title = "User Guide";
        showMessageDialog(mainFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Show error dialog.
     *
     * @param mainFrame the main frame
     * @param message   the message
     */
    public static void showErrorDialog(final JFrame mainFrame, final String message) {
        Objects.requireNonNull(mainFrame, MESSAGE_MAIN_FRAME_CANNOT_BE_NULL);
        Validate.notBlank(message, MESSAGE_CANNOT_BE_BLANK_OR_NULL);

        final String title = "error";
        showMessageDialog(mainFrame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Show message dialog.
     *
     * @param mainFrame             the main frame
     * @param message               the message
     * @param title                 the title
     * @param optionPaneMessageType the option pane message type
     */
    public static void showMessageDialog(final JFrame mainFrame, final String message, final String title,
                                         final Integer optionPaneMessageType) {
        Objects.requireNonNull(mainFrame, MESSAGE_MAIN_FRAME_CANNOT_BE_NULL);
        Validate.notBlank(message, MESSAGE_CANNOT_BE_BLANK_OR_NULL);
        Validate.notNull(title, MESSAGE_TITLE_CANNOT_BE_BLANK_OR_NULL);
        Objects.requireNonNull(optionPaneMessageType, MESSAGE_OPTION_PANEL_MESSAGE_TYPE);

        if (mainFrame.isVisible()) {
            JOptionPane.showMessageDialog(mainFrame, message, title, optionPaneMessageType);
        }
    }
}
