/**
 * The ViewsManger class registers the references of the views
 *
 * @author Bohui WU
 * @since 12/28/2019
 * @version 1.0
 */

package ink.repo.textdisplay.util;

import ink.repo.textdisplay.view.ViewDisplay;
import ink.repo.textdisplay.view.ViewPreference;

public class ViewsManager {

    private static ViewDisplay viewDisplay;
    private static ViewPreference viewPreference;

    public static ViewDisplay getViewDisplay() {
        return viewDisplay;
    }

    public static void setViewDisplay(ViewDisplay viewDisplay) {
        ViewsManager.viewDisplay = viewDisplay;
    }

    public static ViewPreference getViewPreference() {
        return viewPreference;
    }

    public static void setViewPreference(ViewPreference viewPreference) {
        // Not allowed to change the reference once it is set
        if(viewPreference != null) {
            ViewsManager.viewPreference = viewPreference;
        }
    }

}
