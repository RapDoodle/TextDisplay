package ink.repo.textdisplay.view;

import ink.repo.textdisplay.controller.*;
import ink.repo.textdisplay.util.*;
import ink.repo.textdisplay.view.panel.PanelPreferenceInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ViewPreference extends JFrame{

    private ControllerPreference controller;
    private JList list;
    private PanelPreferenceInfo profilePanel;
    private DefaultListModel listData;

    public ViewPreference(ControllerPreference controller) {

        this.controller = controller;  // Register the controller from ink.repo.textdisplay.controller

        // Register references
        ViewsManager.setViewPreference(this);

        setBounds(0, 0, 20, 20);
        setTitle(LangManger.get("preference"));

        initMenuBar();

        setLayout(new BorderLayout());

        // Profile Panel
        profilePanel = new PanelPreferenceInfo(this, controller);
        add(profilePanel, BorderLayout.CENTER);

        // Prepare the window for display
        setSize(800, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void initMenuBar() {
        // Prepare the menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(LangManger.get("file"));
//        JMenuItem itemOpen = new JMenuItem(LangManger.get("open"));
        JMenuItem itemSave = new JMenuItem(LangManger.get("save"));
        JMenuItem itemExit = new JMenuItem(LangManger.get("exit"));
//        fileMenu.add(itemOpen);
        fileMenu.add(itemSave);
        fileMenu.addSeparator();
        fileMenu.add(itemExit);
        menuBar.add(fileMenu);

        JMenu languageMenu = new JMenu(LangManger.get("language"));
        JMenuItem itemENUS = new JMenuItem("English");
        JMenuItem itemZHHANS = new JMenuItem("简体中文");
        JMenuItem itemZHHANT = new JMenuItem("正體中文");
        languageMenu.add(itemENUS);
        languageMenu.add(itemZHHANS);
        languageMenu.add(itemZHHANT);
        menuBar.add(languageMenu);

        JMenu helpMenu = new JMenu(LangManger.get("help"));
        JMenuItem itemHelp = new JMenuItem(LangManger.get("help"));
        JMenuItem itemAbout = new JMenuItem(LangManger.get("about"));
        helpMenu.add(itemHelp);
        helpMenu.addSeparator();
        helpMenu.add(itemAbout);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        // Define actions
        itemSave.addActionListener((ActionEvent e) -> controller.applyButtonClicked(profilePanel.wrapProfileView()));
        itemExit.addActionListener((ActionEvent e) -> System.exit(0));
        itemENUS.addActionListener((ActionEvent e) -> {
            ConfigManager.setConfigEntry("lang", "en-US");
            JOptionPane.showMessageDialog(null, LangManger.get("lang_update_msg"));
        });
        itemZHHANS.addActionListener((ActionEvent e) -> {
            ConfigManager.setConfigEntry("lang", "zh-Hans");
            JOptionPane.showMessageDialog(null, LangManger.get("lang_update_msg"));
        });
        itemZHHANT.addActionListener((ActionEvent e) -> {
            ConfigManager.setConfigEntry("lang", "zh-Hant");
            JOptionPane.showMessageDialog(null, LangManger.get("lang_update_msg"));
        });
        itemAbout.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Version 1.2\n" +
                            "Copyright \u00A9 2019-2020 Bohui WU (\u0040RapDoodle)",
                    LangManger.get("about"), JOptionPane.INFORMATION_MESSAGE);
        });
        itemHelp.addActionListener((ActionEvent e) -> {
            if(!CommonUtils.openLocalFile("./help/README-" + ConfigManager.getConfigEntry("lang") + ".html")) {
                // When the manual is not found
                JOptionPane.showMessageDialog(null, LangManger.get("err_help_not_found"),
                        LangManger.get("message"), JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
