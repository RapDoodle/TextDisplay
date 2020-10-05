/**
 * The controller for the frame Display
 *
 * @author Bohui WU
 * @since 12/20/2019
 */

package ink.repo.textdisplay.controller;

import ink.repo.textdisplay.profile.Profile;
import ink.repo.textdisplay.model.ModelDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ControllerDisplay {

    private ModelDisplay m;

    public ControllerDisplay(ModelDisplay m) {
        this.m = m;
    }

    public Profile getProfile() {
        return m.getProfile();
    }

    public ArrayList<JLabel> getLabels() {
        return m.getLabels();
    }

    public boolean isHorizontal() {
        return m.isHorizontal();
    }

    public void setTextPanelBounds(JPanel textPanel, Dimension screenSize) {
        double vMargin = m.isHorizontal() ? (double) m.getMargin() / 100 : 0;
        double hMargin = m.isHorizontal() ? 0 : (double) m.getMargin() / 100;
        double vOffset = (double) m.getVerticalOffset() / 100;
        double hOffset = (double) m.getHorizontalOffset() / 100;
        textPanel.setBounds((int)(screenSize.getWidth() * hMargin) + (int)(screenSize.getWidth() * hOffset),
                (int)(screenSize.getHeight() * vMargin) + (int)(screenSize.getHeight() * vOffset),
                (int)(screenSize.getWidth() * (1 - 2 * hMargin)), (int)(screenSize.getHeight() * (1 - 2 * vMargin)));

    }

    public JLabel getBackgroundLabel(Dimension screenSize) {
        // TO-DO: Stretch and Scale modes
        // Load Background image
        ImageIcon img = new ImageIcon(m.getBackgroundImageDir().getPath());
        // Accommodate different fitting styles
        int width = 0, height = 0;
        switch(m.getImageFitStyle()) {
            case 0:
                // Fit
                double imgRatio = (double) img.getImage().getWidth(null) / (double) img.getImage().getHeight(null);
                // When the ration is greater than 1, the image is wide.
                // When the ration is in the interval of (0, 1), the image is tall.
                double screenRatio = screenSize.getWidth() / screenSize.getHeight();
                // When the ration is greater than 1, the screen is in landscape mode.
                // When the ration is in the interval of (0, 1), the screen is in portrait mode.
                width = Math.min((int) (imgRatio * screenSize.getHeight()), (int)screenSize.getWidth());
                height = Math.min((int) (1 / imgRatio * screenSize.getWidth()), (int)screenSize.getHeight());
                break;
            case 1:
                // Stretch
                width = (int)screenSize.getWidth();
                height = (int)screenSize.getHeight();
                break;
            case 2:
                // Tile
                width = img.getImage().getWidth(null);
                height = img.getImage().getHeight(null);
                break;
        }
        Image backgroundImg = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel ("", new ImageIcon(backgroundImg), JLabel.CENTER);
        imgLabel.setBounds(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
        return imgLabel;
    }

}
