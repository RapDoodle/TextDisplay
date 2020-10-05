/**
 * The panel stores all the components for adjusting the parameters of the profile
 *
 * @author Bohui WU
 * @since 12/18/2019
 * @version 1.8
 */
package ink.repo.textdisplay.view.panel;

import ink.repo.textdisplay.controller.*;
import ink.repo.textdisplay.profile.Profile;
import ink.repo.textdisplay.util.ColorfulListRenderer;
import ink.repo.textdisplay.util.CommonUtils;
import ink.repo.textdisplay.util.DefaultConst;
import ink.repo.textdisplay.util.LangManger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;

public class PanelPreferenceInfo extends JPanel {

    private ControllerPreference c;

    private Container centerPanel;
    private JPanel bottomPanel;

    private JTextField profileNameTextField;
    private JComboBox<String> fontComboBox;
    private JComboBox<Integer> fontSizeComboBox;
    private JComboBox<String> fontStyleComboBox;
    private JComboBox<String> colorComboBox;
    private JComboBox<String> textDirectionComboBox;
    private JTextArea textArea;
    private JSlider letterSpacingSlider;
    private JSlider marginSlider;
    private JSlider vOffsetSlider;
    private JSlider hOffsetSlider;
    private JTextField imageDirectoryTextField;
    private JButton selectImageDirectoryButton;
    private JComboBox<String> imageFitStyleComboBox;

    private boolean initializing;

    private JFrame parentFrame;

    public PanelPreferenceInfo(JFrame parentFrame, ControllerPreference c) {

        // Register references and set default instance variable(s)
        this.c = c;
        this.parentFrame = parentFrame;
        initializing = true;

        // Initialize center panel
        initCenterPanelComponents();
        initCenterPanelLayout();

        // Initialize bottom panel
        initBottomPanel();

        // Load data into the fields
        loadProfile();

        // Exit initializing mode
        initializing = false;

    }

    private void initCenterPanelComponents() {
        profileNameTextField = new JTextField();
        initFontComboBox();
        initColorComboBox();
        initTextDirectionComboBox();
        textArea = new JTextArea();
        textArea.setRows(3);
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createMatteBorder(1,1,1,1, new Color(98, 98, 98)));
        initSliders();
        imageDirectoryTextField = new JTextField();
        imageDirectoryTextField.setEditable(false);
        selectImageDirectoryButton = new JButton("...");
        initImageFitStyleComboBox();
        selectImageDirectoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.addChoosableFileFilter(new FileNameExtensionFilter("Image(*.jpg, *.jpeg, *.png)",
                        "jpg", "JPG", "jpeg", "JPEG", "png", "PNG"));
                if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file  = fc.getSelectedFile();
                    imageDirectoryTextField.setText(file.getPath());
                }
            }
        });
    }

    private void initSliders() {
        // Margin Slider
        marginSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 20);
        // Create the label table
        Hashtable<Integer, JLabel> marginSliderLabelTable = new Hashtable<>();
        marginSliderLabelTable.put(50, new JLabel(LangManger.get("small")) );
        marginSliderLabelTable.put(25, new JLabel(LangManger.get("moderate")) );
        marginSliderLabelTable.put(0, new JLabel(LangManger.get("large")) );
        marginSlider.setLabelTable(marginSliderLabelTable);
        marginSlider.setPaintLabels(true);
        marginSlider.setInverted(true);

        // Letter spacing slider
        Hashtable<Integer, JLabel> verticalLetterSpacingLabelTable = new Hashtable<>();
        verticalLetterSpacingLabelTable.put(-50, new JLabel(LangManger.get("small")) );
        verticalLetterSpacingLabelTable.put(-20, new JLabel(LangManger.get("moderate")) );
        verticalLetterSpacingLabelTable.put(10, new JLabel(LangManger.get("large")) );
        letterSpacingSlider = new JSlider(JSlider.HORIZONTAL, -50, 10, -20);
        letterSpacingSlider.setLabelTable(verticalLetterSpacingLabelTable);
        letterSpacingSlider.setPaintLabels(true);
        letterSpacingSlider.setEnabled(false);

        // The label table for vertical and horizontal offset sliders
        Hashtable<Integer, JLabel> hSliderTable = new Hashtable<>();
        hSliderTable.put(-25, new JLabel(LangManger.get("left")));
        hSliderTable.put(0, new JLabel(LangManger.get("center")));
        hSliderTable.put(25, new JLabel(LangManger.get("right")));
        Hashtable<Integer, JLabel> vSliderTable = new Hashtable<>();
        vSliderTable.put(-25, new JLabel(LangManger.get("top")));
        vSliderTable.put(0, new JLabel(LangManger.get("center")));
        vSliderTable.put(25, new JLabel(LangManger.get("bottom")));

        // Vertical Offset Slider
        vOffsetSlider = new JSlider(JSlider.HORIZONTAL, -25, 25, 0);
        vOffsetSlider.setLabelTable(vSliderTable);
        vOffsetSlider.setPaintLabels(true);
        vOffsetSlider.setInverted(true);
        vOffsetSlider.setSnapToTicks(true);

        // Horizontal Offset Slider
        hOffsetSlider = new JSlider(JSlider.HORIZONTAL, -25, 25, 0);
        hOffsetSlider.setLabelTable(hSliderTable);
        hOffsetSlider.setPaintLabels(true);
        hOffsetSlider.setSnapToTicks(true);

    }

    private void initCenterPanelLayout() {
        setBorder(BorderFactory.createTitledBorder(LangManger.get("current_profile")));
        setLayout(new BorderLayout());
        centerPanel = new Container();
        centerPanel.setLayout(new GridBagLayout());

        // Line 1: Profile name
        CommonUtils.gbcNewLine();
        centerPanel.add(new JLabel(LangManger.get("profile_name") + ": "),
                CommonUtils.getGridBagConstraints(0, 1,1, 0.2, 1.0, DefaultConst.INSETS_LEFT));
        centerPanel.add(profileNameTextField,
                CommonUtils.getGridBagConstraints(1, 8, 1, 1.6, 1.0, DefaultConst.INSETS_RIGHT));

        // Line 2: Font
        CommonUtils.gbcNewLine();
        centerPanel.add(new JLabel(LangManger.get("font") + ": "),
                CommonUtils.getGridBagConstraints(0, 1, 1, 0.2, 1.0, DefaultConst.INSETS_LEFT));
        centerPanel.add(fontComboBox,
                CommonUtils.getGridBagConstraints(1, 8, 1, 1.6, 1.0, DefaultConst.INSETS_RIGHT));

        // Line 3: Font Size
        CommonUtils.gbcNewLine();
        centerPanel.add(new JLabel(LangManger.get("size") + ": "),
                CommonUtils.getGridBagConstraints(0, 1, 1, 0.2, 1.0, DefaultConst.INSETS_LEFT));
        centerPanel.add(fontSizeComboBox,
                CommonUtils.getGridBagConstraints(1, 8, 1, 1.6, 1.0, DefaultConst.INSETS_RIGHT));

        // Line 4: Font Style
        CommonUtils.gbcNewLine();
        centerPanel.add(new JLabel(LangManger.get("style") + ": "),
                CommonUtils.getGridBagConstraints(0, 1, 1, 0.2, 1.0, DefaultConst.INSETS_LEFT));
        centerPanel.add(fontStyleComboBox,
                CommonUtils.getGridBagConstraints(1, 8, 1, 1.6, 1.0, DefaultConst.INSETS_RIGHT));

        // Line 5: Color
        CommonUtils.gbcNewLine();
        centerPanel.add(new JLabel(LangManger.get("color") + ": "),
                CommonUtils.getGridBagConstraints(0, 1, 1, 0.2, 1.0, DefaultConst.INSETS_LEFT));
        centerPanel.add(colorComboBox,
                CommonUtils.getGridBagConstraints(1, 8, 1, 1.6, 1.0, DefaultConst.INSETS_RIGHT));

        // Line 6: TextDirection
        CommonUtils.gbcNewLine();
        centerPanel.add(new JLabel(LangManger.get("text_direction") + ": "),
                CommonUtils.getGridBagConstraints(0, 1, 1, 0.2, 1.0, DefaultConst.INSETS_LEFT));
        centerPanel.add(textDirectionComboBox,
                CommonUtils.getGridBagConstraints(1, 8, 1, 1.6, 1.0, DefaultConst.INSETS_RIGHT));

        // Line 7: Text
        CommonUtils.gbcNewLine();
        centerPanel.add(new JLabel(LangManger.get("text") + ": "),
                CommonUtils.getGridBagConstraints(0, 1, 3, 0.2, 1.0, DefaultConst.INSETS_LEFT));
        centerPanel.add(textArea,
                CommonUtils.getGridBagConstraints(1, 8, 3, 1.6, 1.0, DefaultConst.INSETS_RIGHT));

        // Line 8: Vertical letter spacing
        CommonUtils.gbcNewLine(3);
        centerPanel.add(new JLabel(LangManger.get("vertical_letter_spacing") + ": "),
                CommonUtils.getGridBagConstraints(0, 1, 1, 0.2, 1.0, DefaultConst.INSETS_LEFT));
        centerPanel.add(letterSpacingSlider,
                CommonUtils.getGridBagConstraints(1, 8, 1, 1.6, 1.0, DefaultConst.INSETS_RIGHT));

        // Line 8: Vertical Margin
        CommonUtils.gbcNewLine();
        centerPanel.add(new JLabel(LangManger.get("line_space") + ": "),
                CommonUtils.getGridBagConstraints(0, 1, 1, 0.2, 1.0, DefaultConst.INSETS_LEFT));
        centerPanel.add(marginSlider,
                CommonUtils.getGridBagConstraints(1, 8, 1, 1.6, 1.0, DefaultConst.INSETS_RIGHT));

        // Line 9: Vertical Offset
        CommonUtils.gbcNewLine();
        centerPanel.add(new JLabel(LangManger.get("vertical_offset") + ": "),
                CommonUtils.getGridBagConstraints(0, 1, 1, 0.2, 1.0, DefaultConst.INSETS_LEFT));
        centerPanel.add(vOffsetSlider,
                CommonUtils.getGridBagConstraints(1, 8, 1, 1.6, 1.0, DefaultConst.INSETS_RIGHT));

        // Line 10: Horizontal Offset
        CommonUtils.gbcNewLine();
        centerPanel.add(new JLabel(LangManger.get("horizontal_offset") + ": "),
                CommonUtils.getGridBagConstraints(0, 1, 1, 0.2, 1.0, DefaultConst.INSETS_LEFT));
        centerPanel.add(hOffsetSlider,
                CommonUtils.getGridBagConstraints(1, 8, 1, 1.6, 1.0, DefaultConst.INSETS_RIGHT));

        // Line 10: Background image
        CommonUtils.gbcNewLine();
        centerPanel.add(new JLabel(LangManger.get("image") + ": "),
                CommonUtils.getGridBagConstraints(0, 1, 1, 0.2, 0.8, DefaultConst.INSETS_LEFT));
        centerPanel.add(imageDirectoryTextField,
                CommonUtils.getGridBagConstraints(1, 1, 1, 1.6, 0.8, DefaultConst.INSETS_CENTER));
        centerPanel.add(selectImageDirectoryButton,
                CommonUtils.getGridBagConstraints(2, 1, 1, 0.1, 0.8, DefaultConst.INSETS_RIGHT));
        selectImageDirectoryButton.setFont(new Font(null, Font.BOLD, 8));

        // Line 11: TextDirection
        CommonUtils.gbcNewLine();
        centerPanel.add(new JLabel(LangManger.get("fit") + ": "),
                CommonUtils.getGridBagConstraints(0, 1, 1, 0.2, 1.0, DefaultConst.INSETS_LEFT));
        centerPanel.add(imageFitStyleComboBox,
                CommonUtils.getGridBagConstraints(1, 6, 1, 1.6, 1.0, DefaultConst.INSETS_RIGHT));

        // For smaller line space
        CommonUtils.gbcNewLine();
        centerPanel.add(new Label(" "), CommonUtils.getGridBagConstraints(1, 1, 1, 0.2, 3, DefaultConst.INSETS_RIGHT));

        add(centerPanel, BorderLayout.CENTER);
    }

    private void initBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Initialize buttons
        JButton confirmButton = new JButton(LangManger.get("confirm"));
        JButton applyButton = new JButton(LangManger.get("apply"));
        JButton previewButton = new JButton(LangManger.get("preview"));
        JButton cancelButton = new JButton(LangManger.get("cancel"));

        // Add buttons to the panel
        bottomPanel.add(cancelButton);
        bottomPanel.add(previewButton);
        bottomPanel.add(applyButton);
        bottomPanel.add(confirmButton);

        // Define actions
        confirmButton.addActionListener((ActionEvent e) -> {
            c.confirmButtonClicked(wrapProfileView());
        });
        applyButton.addActionListener((ActionEvent e) -> {
            c.applyButtonClicked(wrapProfileView());
        });
        previewButton.addActionListener((ActionEvent e) -> {
            c.previewButtonClicked(wrapProfileView());
        });
        cancelButton.addActionListener((ActionEvent e) -> {
            c.cancelButtonClicked();
        });

        add(bottomPanel, BorderLayout.SOUTH);

    }

    public Profile wrapProfileView() {
        return c.wrapProfile(profileNameTextField, fontComboBox, fontStyleComboBox, fontSizeComboBox, null,
                textDirectionComboBox, textArea, letterSpacingSlider, marginSlider, vOffsetSlider, hOffsetSlider, imageDirectoryTextField,
                imageFitStyleComboBox);
    }

    private void initFontComboBox() {
        this.fontComboBox = new JComboBox<>();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] allFonts = ge.getAllFonts();
        for (Font font : allFonts) {
            fontComboBox.addItem(font.getFontName());
        }
        this.fontSizeComboBox = new JComboBox<>();
        fontSizeComboBox.setEditable(true);
        int i = 8;
        while(i <= 72) {
            fontSizeComboBox.addItem(i);
            if(i < 12) {
                i++;
            }else if(i < 28) {
                i += 2;
            }else if(i < 36) {
                i += 8;
            }else if(i < 48) {
                i += 12;
            }else {
                i += 24;
            }
        }
        this.fontStyleComboBox = new JComboBox<>();
        // Line up with the static constants in the class Font
        fontStyleComboBox.addItem(LangManger.get("plain"));  // Font.PLAIN
        fontStyleComboBox.addItem(LangManger.get("bold"));  // Font.BOLD
        fontStyleComboBox.addItem(LangManger.get("italic"));  // Font.ITALIC
    }

    private void initTextDirectionComboBox() {
        this.textDirectionComboBox = new JComboBox<>();
        textDirectionComboBox.addItem(LangManger.get("horizontal"));
        textDirectionComboBox.addItem(LangManger.get("vertical"));
        textDirectionComboBox.addItem(LangManger.get("vertical_inverted"));
        textDirectionComboBox.addActionListener((ActionEvent e) -> {
            if(textDirectionComboBox.getSelectedIndex() != 0) {
                letterSpacingSlider.setEnabled(true);
            }else{
                letterSpacingSlider.setEnabled(false);
            }
        });
    }

    private void initImageFitStyleComboBox() {
        this.imageFitStyleComboBox = new JComboBox<>();
        imageFitStyleComboBox.addItem(LangManger.get("fit"));
        imageFitStyleComboBox.addItem(LangManger.get("stretch"));
        imageFitStyleComboBox.addItem(LangManger.get("tile"));
    }

    private void initColorComboBox() {
        this.colorComboBox = new JComboBox<>();
        Hashtable<Integer, Color> table = new Hashtable<Integer, Color>();
        table.put(0, Color.BLACK);
        table.put(1, Color.RED);
        table.put(2, Color.ORANGE);
        table.put(3, Color.YELLOW);
        table.put(4, Color.GREEN);
        table.put(5, Color.BLUE);
        table.put(6, Color.WHITE);
        colorComboBox.addItem(LangManger.get("black"));
        colorComboBox.addItem(LangManger.get("red"));
        colorComboBox.addItem(LangManger.get("orange"));
        colorComboBox.addItem(LangManger.get("yellow"));
        colorComboBox.addItem(LangManger.get("green"));
        colorComboBox.addItem(LangManger.get("blue"));
        colorComboBox.addItem(LangManger.get("white"));
        colorComboBox.addItem(LangManger.get("others"));

        c.setTmpColor(c.getColor());
        try {
            CommonUtils.getColorFromIndex(c.getTmpColor());
        }catch(NullPointerException e) {
            // When the color is not in the hash map, select "others"
            colorComboBox.setSelectedIndex(colorComboBox.getMaximumRowCount() - 1);
            table.put(7, c.getTmpColor());
        }

        colorComboBox.setRenderer(new ColorfulListRenderer(table));

        // Define events
        colorComboBox.addActionListener((ActionEvent e) -> {
                Color localTmpColor = table.get(colorComboBox.getSelectedIndex());
                if(localTmpColor != null) {
                    c.setTmpColor(localTmpColor);;
                }
                if(colorComboBox.getSelectedIndex() == colorComboBox.getMaximumRowCount() - 1 && !initializing) {
                    try {
                        c.setTmpColor(JColorChooser.showDialog(null, "Color Picker", c.getTmpColor()));
                        // Redefine the color of the "others" option
                        table.put(7, c.getTmpColor());
                        colorComboBox.setRenderer(new ColorfulListRenderer(table));  // Rerender for the combo box
                    }catch(NullPointerException ex) {
                        try {
                            colorComboBox.setSelectedIndex(CommonUtils.getColorFromIndex(c.getTmpColor()));
                        }catch(NullPointerException exp) {
                            // Do nothing when the color previously selected was not in the color table
                        }
                    }
                }
            }
        );
    }

    private void loadProfile() {
        c.updateFields(profileNameTextField, fontComboBox, fontStyleComboBox, fontSizeComboBox, colorComboBox,
                textDirectionComboBox, textArea, letterSpacingSlider, marginSlider, vOffsetSlider, hOffsetSlider, imageDirectoryTextField,
                imageFitStyleComboBox);
    }

}

