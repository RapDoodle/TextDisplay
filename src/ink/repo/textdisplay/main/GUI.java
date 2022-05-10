/**
 * Run the main method in this class to start the application
 *
 * @author Bohui WU
 * @since 12/18/2019
 */
package ink.repo.textdisplay.main;

import ink.repo.textdisplay.controller.*;
import ink.repo.textdisplay.util.ConfigManager;
import ink.repo.textdisplay.util.LangManger;
import ink.repo.textdisplay.util.Log;
import ink.repo.textdisplay.view.*;
import ink.repo.textdisplay.model.PreferenceModel;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public static void main(String[] args) {
        // Initialize the logger
        Log.initLogger("log.txt");

        // Start GUI interface
        SwingUtilities.invokeLater(() -> {
            try {
                // Change the color theme of the program to Darcular
                UIManager.setLookAndFeel("com.bulenkov.darcula.DarculaLaf");

                // Slider themes
                UIManager.getLookAndFeelDefaults().put("Slider.selectedTrackColor", new Color(43, 43, 43));
                UIManager.getLookAndFeelDefaults().put("Slider.disabledTickColor", new Color(43, 43, 43));
            } catch (ClassNotFoundException | InstantiationException |
                     IllegalAccessException | UnsupportedLookAndFeelException e) {
                // Fail to set the theme, use the default theme
                Log.logError(e.getMessage());
            }

            // Initialize managers
            ConfigManager.initConfigManager();
            LangManger.initLangManager();

            // Start PreferenceView
            new PreferenceController();
        });
    }
}
