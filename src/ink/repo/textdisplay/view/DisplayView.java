package ink.repo.textdisplay.view;

import ink.repo.textdisplay.util.LangManger;

import javax.swing.*;
import java.awt.*;

public class DisplayView extends JFrame {

    private JPopupMenu rightClickMenu;
    private JMenuItem preferenceItem;
    private JMenuItem exitItem;

    public DisplayView() {
        // Full screen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        // Initialize the right click menu
        initRightClickMenu();

        // Set the default color
        getContentPane().setBackground(Color.BLACK);
    }

    private void initRightClickMenu() {
        rightClickMenu = new JPopupMenu();
        preferenceItem = new JMenuItem(LangManger.get("preference"));
        exitItem = new JMenuItem(LangManger.get("exit"));
        rightClickMenu.add(preferenceItem);
        rightClickMenu.addSeparator();
        rightClickMenu.add(exitItem);
    }

    public JPopupMenu getRightClickMenu() {
        return rightClickMenu;
    }

    public JMenuItem getPreferenceItem() {
        return preferenceItem;
    }

    public JMenuItem getExitItem() {
        return exitItem;
    }
}
