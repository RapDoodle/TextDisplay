package ink.repo.textdisplay.view;

import ink.repo.textdisplay.controller.*;
import ink.repo.textdisplay.util.LangManger;
import ink.repo.textdisplay.util.ViewsManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ViewDisplay extends JFrame {

    private ControllerDisplay c;
    private JPopupMenu rightClickMenu;
    private Dimension screenSize;

    public ViewDisplay(ControllerDisplay c, boolean previewMode) {

        // Register
        this.c = c;

        // Full screen state
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Paint various components
        update();

        // Initialize right click menu
        initRightClickMenu();

        if(!previewMode) {
            // When its not in preview mode
            ViewsManager.setViewDisplay(this);
        }else{
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dispose();
                    ViewsManager.getViewPreference().setVisible(true);
                }
            });
        }

        // Setting the color
        getContentPane().setBackground(Color.BLACK);

        // Display the frame
        setVisible(true);
    }

    public ViewDisplay(ControllerDisplay c) {
        this(c, false);
    }

    private void update() {

        // Initialize the text panel
        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(0,0,0,0));
        c.setTextPanelBounds(textPanel, screenSize);
        // Load labels into the frame
        ArrayList<JLabel> labels = c.getLabels();
        for(JLabel label : labels) {
            textPanel.add(label);
        }

        // Define the orientation of the layout according to the profile
        int rows = 1, cols = 1;
        if(c.isHorizontal()) {
            rows = labels.size();
        }else{
            cols = labels.size();
        }
        textPanel.setLayout(new GridLayout(rows, cols, 0, 0));

        add(textPanel);
        add(c.getBackgroundLabel(screenSize));
    }

    private void initRightClickMenu() {

        rightClickMenu = new JPopupMenu();
        JMenuItem menuPreference = new JMenuItem(LangManger.get("preference"));
        JMenuItem menuExit = new JMenuItem(LangManger.get("exit"));
        rightClickMenu.add(menuPreference);
        rightClickMenu.addSeparator();
        rightClickMenu.add(menuExit);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3) {
                    // Right click
                    rightClickMenu.show(ViewDisplay.this, e.getX(), e.getY());
                }
            }
        });

        // Define item events
        menuPreference.addActionListener((ActionEvent e) -> {
            setVisible(false);
            ViewsManager.getViewPreference().setVisible(true);
        });
        menuExit.addActionListener((ActionEvent e) -> System.exit(0));
    }

}
