/**
 * The controller for the frame Preference
 *
 * @author Bohui WU
 * @since 12/20/2019
 */
package ink.repo.textdisplay.controller;

import ink.repo.textdisplay.model.ModelDisplay;
import ink.repo.textdisplay.profile.Profile;
import ink.repo.textdisplay.model.ModelPreference;
import ink.repo.textdisplay.util.CommonUtils;
import ink.repo.textdisplay.util.Log;
import ink.repo.textdisplay.util.ViewsManager;
import ink.repo.textdisplay.view.ViewDisplay;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ControllerPreference {

    private ModelPreference m;
    private Color tmpColor;

    public ControllerPreference(ModelPreference m) {
        this.m = m;
    }

    public Color getColor() {
        return m.getProfile().getColor();
    }

    public Color getTmpColor() {
        return tmpColor;
    }

    public void setTmpColor(Color tmpColor) {
        this.tmpColor = tmpColor;
    }

    private void trySave(Profile profile) {
        try {
            m.save(profile);
        }catch(IOException ex) {
            Log.logError(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error occurred while saving the profile!");
        }
    }

    public void confirmButtonClicked(Profile profile) {
        trySave(profile);
        new ViewDisplay(new ControllerDisplay(new ModelDisplay()));
        ViewsManager.getViewPreference().setVisible(false);
    }

    public void applyButtonClicked(Profile profile) {
        trySave(profile);
    }

    public void previewButtonClicked(Profile profile) {
        trySave(profile);
        new ViewDisplay(new ControllerDisplay(new ModelDisplay()), true);
        ViewsManager.getViewPreference().setVisible(false);
    }

    public void cancelButtonClicked() {
        if(ViewsManager.getViewDisplay() == null) {
            ViewsManager.getViewPreference().dispose();
        }else{
            ViewsManager.getViewPreference().setVisible(false);
            ViewsManager.getViewDisplay().setVisible(true);
        }
    }

    public Profile wrapProfile(JTextField profileNameTextField, JComboBox<String> fontComboBox,
                               JComboBox<String> fontStyleComboBox, JComboBox<Integer> fontSizeComboBox,
                               @Nullable Color color, JComboBox<String> textDirectionComboBox, JTextArea textArea,
                               JSlider letterSpacing, JSlider marginSlider, JSlider vOffsetSlider, JSlider hOffsetSlider,
                               JTextField imageDirectoryTextField, JComboBox<String> imgFitStyleComboBox) {
        Profile profile = new Profile(profileNameTextField.getText(),
                textArea.getText(),
                new Font(fontComboBox.getSelectedItem().toString(),
                        fontStyleComboBox.getSelectedIndex(),
                        Integer.parseInt(fontSizeComboBox.getSelectedItem().toString())),
                color == null ? tmpColor : color,  // Use tmpColor when it was not passed in as argument
                letterSpacing.getValue(),
                marginSlider.getValue(),
                vOffsetSlider.getValue(),
                hOffsetSlider.getValue(),
                new File(imageDirectoryTextField.getText()),
                textDirectionComboBox.getSelectedIndex(),
                imgFitStyleComboBox.getSelectedIndex(),
                false
                );
        return profile;
    }

    public void updateFields(JTextField profileNameTextField, JComboBox<String> fontComboBox,
                             JComboBox<String> fontStyleComboBox, JComboBox<Integer> fontSizeComboBox,
                             JComboBox<String> colorComboBox, JComboBox<String> textDirectionComboBox, JTextArea textArea,
                             JSlider letterSpacingSlider, JSlider marginSlider, JSlider vOffsetSlider,
                             JSlider hOffsetSlider, JTextField imageDirectoryTextField,
                             JComboBox<String> imgFitStyleComboBox) {
        Profile profile = m.getProfile();
        profileNameTextField.setText(profile.getName());
        Font font = profile.getFont();
        fontComboBox.setSelectedItem(font.getFontName());
        fontSizeComboBox.setSelectedItem(font.getSize());
        fontStyleComboBox.setSelectedIndex(font.getStyle());
        try {
            tmpColor = profile.getColor();
            colorComboBox.setSelectedIndex(CommonUtils.getColorFromIndex(profile.getColor()));
        }catch(NullPointerException e) {
            // When the color is not in the hash map, select "others"
            tmpColor = profile.getColor();
            colorComboBox.setSelectedIndex(colorComboBox.getMaximumRowCount() - 1);
        }
        letterSpacingSlider.setValue(profile.getLetterSpacing());
        vOffsetSlider.setValue(profile.getvOffset());
        hOffsetSlider.setValue(profile.gethOffset());
        textDirectionComboBox.setSelectedIndex(profile.getTextOrientation());
        textArea.setText(profile.getText());
        marginSlider.setValue(profile.getMargin());
        try {
            imageDirectoryTextField.setText(profile.getBackgroundImageDir().getPath());
        }catch(NullPointerException e) {
            // Do nothing when the file does not exist
        }
        imgFitStyleComboBox.setSelectedIndex(profile.getImgFitStyle());
    }

}
