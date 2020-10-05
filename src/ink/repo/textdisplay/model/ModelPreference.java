/**
 * The model for the frame preference. It handles the IO of the current profile
 *
 * @author Bohui WU
 * @since 12/20/2019
 */
package ink.repo.textdisplay.model;

import ink.repo.textdisplay.profile.Profile;
import ink.repo.textdisplay.util.ConfigManager;
import ink.repo.textdisplay.util.LangManger;
import ink.repo.textdisplay.util.Log;
import ink.repo.textdisplay.util.ProfileManager;

import javax.swing.*;
import java.io.*;

public class ModelPreference {

    // TO-DO: Support user selected preference
    private File profileDirectory;

    public ModelPreference() {

        profileDirectory = new File(ConfigManager.getConfigEntry("profile_location"));

        if(!profileDirectory.exists()) {
            JOptionPane.showMessageDialog(null, LangManger.get("init_profile"));
            ProfileManager.setProfile(new Profile());
        }else{
            try {
                read();
            }catch(IOException | NullPointerException | ClassNotFoundException e) {
                Log.log(e.getMessage());
                if(JOptionPane.showConfirmDialog(null, LangManger.get("incompatible_profile"),
                        LangManger.get("message"), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                    // When a incompatible profile is detected
                    ProfileManager.setProfile(new Profile());
                }else{
                    System.exit(1);
                }
            }
        }
    }

    public void save(Profile profile) throws IOException{
        ProfileManager.setProfile(profile);  // Register the profile to the profile manager
        // Write the profile
        FileOutputStream fos = new FileOutputStream(profileDirectory);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        out.writeObject(profile);
        out.close();
        fos.close();
        Log.log("Profile saved.");
    }

    public void read() throws IOException, ClassNotFoundException {
        // Read the profile
        FileInputStream fis = new FileInputStream(profileDirectory);
        ObjectInputStream in = new ObjectInputStream(fis);
        // Register the profile to the profile manager
        ProfileManager.setProfile((Profile)in.readObject());
        in.close();
        fis.close();
        Log.log("Profile loaded.");
    }

    public Profile getProfile() {
        // Return the profile in the profile manager
        return ProfileManager.getProfile();
    }

}
