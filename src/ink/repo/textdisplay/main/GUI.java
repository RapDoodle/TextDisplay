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
import ink.repo.textdisplay.model.ModelPreference;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public static void main(String[] args) {
        // Initialize the logger
        Log.initLogger("log.txt");
        SwingUtilities.invokeLater(() -> {
            try {
                // Change the color theme of the program to Darcular
                UIManager.setLookAndFeel("com.bulenkov.darcula.DarculaLaf");
                // Theme adjustments on the slider
                UIManager.getLookAndFeelDefaults().put("Slider.selectedTrackColor", new Color(43, 43, 43));
                UIManager.getLookAndFeelDefaults().put("Slider.disabledTickColor", new Color(43, 43, 43));
            }catch(Exception e) {
                Log.logError(e.getMessage());
            }
            try {
                // Initialize various managers from ink.repo.textdisplay.util package
                ConfigManager.initConfigManager();
                LangManger.initLangManager();
                // Initialize the models for the views
                ModelPreference modelPreference = new ModelPreference();
                ControllerPreference controllerPreference = new ControllerPreference(modelPreference);
                // Show preference settings
                new ViewPreference(controllerPreference);
            }catch(Exception e) {
                Log.logError(e.getMessage());
                Log.logError("Unable to initialize the program. Please consider reinstalling the program");
                System.exit(1);
            }

        });
    }

}
