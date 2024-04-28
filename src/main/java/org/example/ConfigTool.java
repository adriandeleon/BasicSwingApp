package org.example;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigValueFactory;
import com.typesafe.config.parser.ConfigDocument;
import com.typesafe.config.parser.ConfigDocumentFactory;
import lombok.experimental.UtilityClass;
import org.example.ui.CommonDialogsTool;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Map;
import java.util.Optional;


@UtilityClass
public class ConfigTool {

    public static String CONFIG_STRING_APP_UI_THEME = "app.ui.theme";
    public static String CONFIG_STRING_APP_UI_FONT_SIZE = "app.ui.fontsize";
    public static final String userHomeDirectory = System.getProperty("user.home");
    public static final String appConfigDirectory = ".SwingApp";
    public static final String appConfigFile = "SwingApp.conf";
    public static final Path configDir = Paths.get(userHomeDirectory, appConfigDirectory);
    public static final Path configFile = Paths.get(userHomeDirectory, appConfigDirectory, appConfigFile);

    public static Optional<Config> readConfig() {

        //Check if the config file exists, if not create a default version
        if(Files.notExists(configDir)) {
            try {
                Files.createDirectory(configDir);
                final String resourceFileName = appConfigFile;  // Name of the file in the resources folder

                // Get the InputStream for the resource file
                try (InputStream inputStream = ConfigTool.class.getResourceAsStream("/" + resourceFileName)) {
                    if (inputStream == null) {
                        System.out.println("Resource file not found.");
                        return Optional.empty();
                    }

                    // Set up the target path
                    final Path targetPath = Paths.get(configDir.toString(), resourceFileName);

                    // Copy the file
                    Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                System.err.println("Failed to read configuration: " + e.getMessage());
            }
        }

        try {
            // Parse the HOCON file into a ConfigDocument.
            final String hoconContent = Files.readString(configFile);
            final ConfigDocument configDocument = ConfigDocumentFactory.parseString(hoconContent);

            // Load the parsed ConfigDocument into a Config object.
            return Optional.of(ConfigFactory.parseString(configDocument.render()));
        } catch (
                IOException e) {
            System.err.println("Failed to read configuration: " + e.getMessage());
        }
        return Optional.empty();
    }

    public static void writeConfig(final Map<String, Object> modifications) {
        Config modifiedConfig = null;
        try {
            for (Map.Entry<String, Object> entry : modifications.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                modifiedConfig = App.config.withValue(key, ConfigValueFactory.fromAnyRef(value));
            }

            final String serializedConfig = modifiedConfig.root().render(ConfigRenderOptions.concise().setFormatted(true).setJson(false));
            // Save the modified ConfigDocument to the file.
            Files.writeString(configFile, serializedConfig, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            //Reload the modified config from file.
            if(readConfig().isEmpty()) {
               CommonDialogsTool.showErrorDialog(App.mainFrame, "Error opening configuration file.");
            }
            App.config = readConfig().get();

            System.out.println("Configuration updated successfully.");
        } catch (IOException e) {
            System.err.println("Failed to update configuration: " + e.getMessage());
        }
    }
}
