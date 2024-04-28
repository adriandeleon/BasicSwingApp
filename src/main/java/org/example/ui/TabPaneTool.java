package org.example.ui;

import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import com.formdev.flatlaf.util.FontUtils;
import lombok.experimental.UtilityClass;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

@UtilityClass
public class TabPaneTool {

    public static JTabbedPane tabbedPane;
    public static JTabbedPane createTabPane(final JFrame frame) {
        // Tabbed pane
        tabbedPane = new JTabbedPane();

        final JPanel editPanel = createEditPanel();
        final JPanel systemInfoPanel = createSystemInfoPanel();
        final JPanel networkInfoPanel = createNetworkInfoPanel();

        tabbedPane.addTab("Edit Me", editPanel);
        tabbedPane.addTab("System Information", systemInfoPanel);
        tabbedPane.addTab("Network Information", networkInfoPanel);

        // Adding the tabbed pane to the center of the frame
        frame.add(tabbedPane, BorderLayout.CENTER);

        return tabbedPane;
    }

    public static JPanel createEditPanel(){
        final JPanel editPanel = new JPanel(new BorderLayout());

        // TODO: Move this to a resource file.
        final String exampleCode = """
                public class HelloWorld {
                    public static void main(String[] args) {
                        System.out.println("Hello, World!");
                    }
                }
                """;

        //final RSyntaxTextArea rSyntaxTextArea = new RSyntaxTextArea(20, 60);
        final RSyntaxTextArea rSyntaxTextArea = new RSyntaxTextArea();
        rSyntaxTextArea.setFont(FontUtils.getCompositeFont(FlatJetBrainsMonoFont.FAMILY, Font.PLAIN, 16));
        rSyntaxTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        rSyntaxTextArea.setCodeFoldingEnabled(true);
        rSyntaxTextArea.setText(exampleCode);

        final RTextScrollPane rTextScrollPane = new RTextScrollPane(rSyntaxTextArea);

        editPanel.add(rTextScrollPane, BorderLayout.CENTER);

        return editPanel;
    }

    public static JPanel createSystemInfoPanel(){
        final JPanel systemInfoPanel = new JPanel();

        // Use a grid layout to display the labels and values TODO: use migLayout.
        systemInfoPanel.setLayout(new GridLayout(0, 2)); // 0 means any number of rows, 2 columns

        // Get system properties
        final String osName = System.getProperty("os.name");
        final String osVersion = System.getProperty("os.version");
        final String javaVersion = System.getProperty("java.version");
        long memory = Runtime.getRuntime().maxMemory();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        final OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        // System Information Labels
        systemInfoPanel.add(new JLabel("Operating System: "));
        systemInfoPanel.add(new JLabel(osName + " " + osVersion));
        systemInfoPanel.add(new JLabel("Java Version: "));
        systemInfoPanel.add(new JLabel(javaVersion));
        systemInfoPanel.add(new JLabel("Maximum Memory (bytes): "));
        systemInfoPanel.add(new JLabel(String.valueOf(memory)));
        systemInfoPanel.add(new JLabel("Available Processors: "));
        systemInfoPanel.add(new JLabel(String.valueOf(availableProcessors)));
        systemInfoPanel.add(new JLabel("System CPU Load: "));
        systemInfoPanel.add(new JLabel(String.format("%.2f", osBean.getSystemLoadAverage() * 100) + "%"));

        return systemInfoPanel;
    }

    public static JPanel createNetworkInfoPanel(){
        final JPanel networkInfoPanel = new JPanel();
        networkInfoPanel.add(new JLabel("Network Information"));

        return networkInfoPanel;
    }
}
