/**
 * Some utilities used by the program
 *   1). Convert a given Color object to the index of colorComboBox in PanelPreferenceInfo
 *   2). Convert a given index in the colorComboBox in PanelPreferenceInfo to a Color object
 *   3). Control the grid bag constrains object in the PanelPreferenceInfo
 *   4). Get the screenshot of a given window (not used)
 *
 * @author Bohui WU
 * @since 12/24/2019
 * @version 1.0
 */
package ink.repo.textdisplay.util;

import com.google.common.collect.HashBiMap;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

public class CommonUtils {

    private static HashBiMap<Color, Integer> colorHashMap;
    private static GridBagConstraints gbc;

    static {
        // Define the color map
        colorHashMap = new HashBiMap<>();
        colorHashMap.put(Color.BLACK, 0);
        colorHashMap.put(Color.RED, 1);
        colorHashMap.put(Color.ORANGE, 2);
        colorHashMap.put(Color.YELLOW, 3);
        colorHashMap.put(Color.GREEN, 4);
        colorHashMap.put(Color.BLUE, 5);
        colorHashMap.put(Color.WHITE, 6);

        // Initialize the grid bag constraints
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
    }

    public static int getColorFromIndex(Color color) throws NullPointerException {
        try {
            return colorHashMap.get(color);
        } catch (NullPointerException e) {
            return -1;
        }
    }

    public static Color getIndexFromColor(int index) throws NullPointerException{
        return colorHashMap.inverse().get(index);
    }

//    public static BufferedImage getScreenshot(Component component) {
//        // Unable to implement correctly under the current framework
//        BufferedImage image = new BufferedImage(
//                component.getWidth(),
//                component.getHeight(),
//                BufferedImage.TYPE_INT_RGB
//        );
//        // Call the Component's paint method, using the Graphics object of the image.
//        component.paint(image.getGraphics());
//        return image;
//    }

    public static GridBagConstraints getGridBagConstraints(int gridX, int gridWidth, int gridHeight, double weightX,
                                                           double weightY, Insets insets) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = gridX;
        gbc.gridwidth = gridWidth;
        gbc.gridheight = gridHeight;
        gbc.weightx = weightX;
        gbc.weighty = weightY;
        gbc.insets = insets;
        return gbc;
    }

    public static void gbcNewLine() {
        gbc.gridy += 1;
    }

    public static void gbcNewLine(int lines) {
        gbc.gridy += lines;
    }

    public static boolean openLocalFile(String loc) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().open(new File(loc));
                return true;  // Indicating success
            }
        }catch(Exception e) {
            Log.logError(e.getMessage());
        }
        return false;
    }

}
