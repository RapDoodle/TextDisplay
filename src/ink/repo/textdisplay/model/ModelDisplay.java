/**
 * The model for the frame display. It is responsible for getting the profile from the profile manager
 * and return the requested data required by the ControllerDisplay
 *
 * @author Bohui WU
 * @since 12/20/2019
 */
package ink.repo.textdisplay.model;

import ink.repo.textdisplay.profile.Profile;
import ink.repo.textdisplay.util.ProfileManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class ModelDisplay {

    private Profile profile;

    public ModelDisplay() {
        profile = ProfileManager.getProfile();
    }

    public ModelDisplay(Profile profile) {
        // NOT USED. JUST FOR FUTURE DEVELOPMENT
        this.profile = profile;
    }

    public void updateProfile() {
        // NOT USED. JUST FOR FUTURE DEVELOPMENT
        // Retrieve profile from Profile manager again
        profile = ProfileManager.getProfile();
    }

    public Profile getProfile() {
        // Return the current profile registered in the model
        return profile;
    }

    public ArrayList<JLabel> getLabels() {
        ArrayList<JLabel> labels = new ArrayList<>();
        String[] originalText = profile.getText().split("\n");
        // Convert to HTML code
        for (String s : originalText) {
            StringBuilder htmlCode = new StringBuilder();
            Font font = profile.getFont();
            Color color = profile.getColor();
            htmlCode.append("<html><p>");
            if(profile.getTextOrientation() != 0) {
                // When the text orientation is vertical
                String[] chars = s.split("");
                for (String currentChar : chars) {
                    htmlCode.append("<p style='margin-top: ")
                            .append(profile.getLetterSpacing())
                            .append(";'>")
                            .append(currentChar)
                            .append("</p>");
                }
            }else{
                // When the orientation is horizontal, append the text directly
                htmlCode.append(s);
            }
            htmlCode.append("</p></html>");
            // Generate the label and add it to the array list
            JLabel label = new JLabel(htmlCode.toString());
            label.setFont(font);
            label.setForeground(profile.getColor());
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            labels.add(label);
        }
        if(profile.getTextOrientation() == 2) {
            // If "vertical (inverse)" is selected
            Collections.reverse(labels);
        }
        return labels;
    }

    public int getMargin() {
        return profile.getMargin();
    }

    public int getVerticalOffset() {
        return profile.getvOffset();
    }

    public int getHorizontalOffset() {
        return profile.gethOffset();
    }

    public boolean isHorizontal() {
        return profile.getTextOrientation() == 0;
    }

    public File getBackgroundImageDir() {
        return profile.getBackgroundImageDir();
    }

    public int getImageFitStyle() {
        return profile.getImgFitStyle();
    }

}
