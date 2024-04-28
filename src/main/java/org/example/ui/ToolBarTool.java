package org.example.ui;

import lombok.experimental.UtilityClass;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The type Tool bar tool.
 */
@UtilityClass
public class ToolBarTool {
    /**
     * Sets toolbar.
     *
     * @param mainFrame the main frame
     * @return the toolbar
     */
    public static JToolBar setupToolbar(final JFrame mainFrame) {
        // Set up a toolbar.
        final JToolBar toolBar = new JToolBar();
        final JButton btnOpen = new JButton(createImageIcon("/icons/project.png", "Open")); // Add your icon path here

        btnOpen.setToolTipText("Open");
        btnOpen.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Open menu item clicked"));
        btnOpen.setToolTipText("Open a file...");
        toolBar.add(btnOpen);
        toolBar.addSeparator();

        final JButton btnCut = new JButton(createImageIcon("/icons/menu-cut.png", "Cut")); // Add your icon path here
        btnCut.setToolTipText("Cut");
        btnCut.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Cut action triggered"));
        btnCut.setToolTipText("Cut");
        toolBar.add(btnCut);
        toolBar.addSeparator();

        final JButton btnCopy = new JButton(createImageIcon("/icons/menu-copy.png", "Copy")); // Add your icon path here
        btnCopy.setToolTipText("Copy");
        btnCopy.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Copy action triggered"));
        btnCopy.setToolTipText("Copy");
        toolBar.add(btnCopy);
        toolBar.addSeparator();

        final JButton btnPaste = new JButton(createImageIcon("/icons/menu-paste.png", "Paste")); // Add your icon path here
        btnPaste.setToolTipText("Paste");
        btnPaste.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Paste action triggered"));
        btnPaste.setToolTipText("Paste");
        toolBar.add(btnPaste);

        return toolBar;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    private static ImageIcon createImageIcon(final String path, final String description) {



        //TODO: use an Optional?
        java.net.URL imgURL = ToolBarTool.class.getResource(path);

        if (imgURL != null) {
            ImageIcon originalIcon = new ImageIcon(imgURL, description);
            Image originalImage = originalIcon.getImage();
            Image resizedImage = originalImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            return new ImageIcon(resizedImage);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private static ImageIcon createImageIconFromSvg(final String path, final String description) {
        final ImageIcon[] icon = new ImageIcon[1];

        SwingUtilities.invokeLater(() -> {
            // Load the SVG file
            final String parser = XMLResourceDescriptor.getXMLParserClassName();
            final SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
            SVGDocument svgDocument;
            try {
                java.net.URL imgURL = ToolBarTool.class.getResource(path);
                svgDocument = factory.createSVGDocument(imgURL.toString());

                // Create a JSVGCanvas to render the SVG image
                JSVGCanvas svgCanvas = new JSVGCanvas();
                svgCanvas.setSVGDocument(svgDocument);
                svgCanvas.setPreferredSize(new Dimension(24, 24));

                // Create a button with the SVG icon
                icon[0] = new ImageIcon(svgCanvas.createImage(24,24));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return icon[0];
    }

}
