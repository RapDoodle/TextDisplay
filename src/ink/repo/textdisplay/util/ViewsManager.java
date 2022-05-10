/**
 * The ViewsManger class registers the references of the views
 *
 * @author Bohui WU
 * @since 12/28/2019
 * @version 1.0
 */

package ink.repo.textdisplay.util;

import ink.repo.textdisplay.view.DisplayView;
import ink.repo.textdisplay.view.PreferenceView;

public class ViewsManager {

    private static DisplayView displayView;
    private static PreferenceView preferenceView;

    public static DisplayView getDisplayView() {
        return displayView;
    }

    public static void setDisplayView(DisplayView displayView) {
        ViewsManager.displayView = displayView;
    }

    public static PreferenceView getPreferenceView() {
        return preferenceView;
    }

    public static void setPreferenceView(PreferenceView preferenceView) {
        // Not allowed to change the reference once it is set
        if (preferenceView != null) {
            ViewsManager.preferenceView = preferenceView;
        }
    }

}
