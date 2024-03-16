package org.example;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import com.typesafe.config.parser.ConfigDocument;
import com.typesafe.config.parser.ConfigDocumentFactory;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.example.App.config;

@UtilityClass
public class ConfigTool {

    public static String CONFIG_STRING_APP_UI_THEME = "app.ui.theme";
    public static String CONFIG_STRING_APP_UI_FONT_SIZE = "app.ui.fontsize";
    public final String userHomeDirectory = System.getProperty("user.home");
    public final Path configFile = Paths.get(userHomeDirectory, ".SwingApp", "SwingApp.conf");

    public static Config readConfig() {
        Config config = null;
        try {
            // Parse the HOCON file into a ConfigDocument.
            final String hoconContent = Files.readString(configFile);
            final ConfigDocument configDocument = ConfigDocumentFactory.parseString(hoconContent);

            // Load the parsed ConfigDocument into a Config object.
            config = ConfigFactory.parseString(configDocument.render());
        } catch (
                IOException e) {
            System.err.println("Failed to read configuration: " + e.getMessage());
        }
        return config;
    }

    public static void writeConfig(final Map<String, Object> modifications) {
        Config modifiedConfig = null;
        try {
            for (Map.Entry<String, Object> entry : modifications.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                modifiedConfig = config.withValue(key, ConfigValueFactory.fromAnyRef(value));
            }

            // Render the modified Config object back to a ConfigDocument.
            ConfigDocument modifiedDocument = ConfigDocumentFactory.parseString(modifiedConfig.root().render());

            // Save the modified ConfigDocument to the file.
            Files.writeString(configFile, modifiedDocument.render());

            //Reload the modified config from file.
            config = readConfig();

            System.out.println("Configuration updated successfully.");
        } catch (IOException e) {
            System.err.println("Failed to update configuration: " + e.getMessage());
        }
    }
}
