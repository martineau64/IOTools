package ioTools;

import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.io.IOException;

import java.awt.Font;
import java.util.List;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Color;

import ioTools.MainTemplateFrame;


/**
 * Class allowing you to display a frame with interactive borders.
 */
public class LineComponentsTemplateFrame {
    
    protected MainTemplateFrame MAINFRAME;
    protected JPanel MAINPANEL;
    protected JScrollPane MAINSCROLLPANE;
    protected JPanel SELECTBAR;

    // First display parameters
    protected double INITIALSCREENFACTOR = 1.3 / 2;
    protected Color BACKGROUNDCOLOR = new Color(31, 31, 31);

    // Buffers
    protected String BUTTONTEXT = "";

    // Default title parameters
    protected int TITLEHEIGHT = 50;
    protected int TITLEUPPERINSET = 10;
    protected int TITLELEFTINSET = 10;
    protected Font TITLEFONT = new Font("Comic Sans MS", Font.PLAIN, 24);
    protected Color TITLECOLOR = Color.RED;

    // Default components parameters
    protected int COMPONENTHEIGHT = 40;
    protected int COMPONENTLEFTINSET = 60;
    protected int COMPONENTRIGHTINSET = 10;
    protected int COMPONENTSPACE = 10;
    protected Font COMPONENTFONT = new Font("Comic Sans MS", Font.PLAIN, 18);
    protected Color COMPONENTCOLOR = Color.RED;

    // Default specific components parameters
    protected int TEXTFIELDSIZE = 0;

    // Main components lists
    protected List<String> LABELS = new ArrayList<String>();
    protected List<JCheckBox> CHECKBOXES = new ArrayList<JCheckBox>();
    protected List<JTextField> TEXTFIELDS = new ArrayList<JTextField>();
    protected List<List<JButton>> BUTTONS = new ArrayList<List<JButton>>();

    // Select panel parameters
    protected int SELECTBARHEIGHT = 0;
    protected Color SELECTBARCOLOR = new Color(24, 24, 24);

    protected int SELECTBARBUTTONMINWIDTH = 120;
    protected int SELECTBARBUTTONHEIGHT = 50;
    protected int SELECTBARNBBUTTONS = 0;
    protected int SELECTBARINSET = 10;
    protected int SELECTBARSPACE = 20;
    protected boolean REDBUTTONADDED = false;
    protected Color REDBUTTONCOLOR = Color.RED;
    protected Color LEFTBUTTONCOLOR = Color.WHITE;

    protected String ERROR = "";


    // ******************************** GETTERS / SETTERS ******************************** //


    public int getNbLines() {
        return this.LABELS.size();
    }
    
    /**
     * Return the list of labels.
     */
    public List<String> getLabels() {
        ArrayList<String> labels = new ArrayList<String>();
        for (String label : this.LABELS) {
            labels.add(label);
        }
        return labels;
    }

    public List<Boolean> getCheckBoxesStatus() {
        ArrayList<Boolean> checkBoxesStatus = new ArrayList<Boolean>();
        for (int index = 0; index < this.CHECKBOXES.size(); index++) {
            checkBoxesStatus.add(this.CHECKBOXES.get(index).isSelected());
        }
        return checkBoxesStatus;
    }

    public List<String> getTextFieldsContents() {
        ArrayList<String> textFieldsContents = new ArrayList<String>();
        for (int index = 0; index < this.TEXTFIELDS.size(); index++) {
            textFieldsContents.add(this.TEXTFIELDS.get(index).getText());
        }
        return textFieldsContents;
    }

    public List<List<String>> getButtonsContents() {
        List<List<String>> buttonsContentsList = new ArrayList<List<String>>();
        for (int buttonNumber = 0; buttonNumber < this.BUTTONS.size(); buttonNumber++) {
            List<String> buttonsContents = new ArrayList<String>();
            for (int index = 0; index < this.LABELS.size(); index++) {
                buttonsContents.add(this.BUTTONS.get(buttonNumber).get(index).getText());
            }
            buttonsContentsList.add(buttonsContents);
        }
        return buttonsContentsList;
    }

    public List<List<JButton>> getButtons() {
        return this.BUTTONS;
    }
    
    /**
     * Return if the Checkbox on the corresponding line is checked.
     * @param componentsId Line associated to the Checkbox.
     */
    public boolean getIsChecked(int componentsId)
    throws IOException {
        if (this.CHECKBOXES.size() > componentsId) {
            return this.CHECKBOXES.get(componentsId).isSelected();
        } else {
            throw new IOException("Component index out of bounds");
        }
    }

    /**
     * Return the String written in the line's TextField.
     * @param componentsId Line associated to the TextField.
     */
    public String getText(int componentsId)
    throws IOException {
        if (this.TEXTFIELDS.size() > componentsId) {
            return this.TEXTFIELDS.get(componentsId).getText();
        } else {
            throw new IOException("Component index out of bounds");
        }
    }

    /**
     * Add the checkbox that will be displayed.
     * @param labels Labels to display depending on MAINCOMPONENTS.
     */
    public void setLabels(List<String> labels) {
        this.LABELS = labels;
        this.CHECKBOXES = new ArrayList<JCheckBox>();
        this.TEXTFIELDS = new ArrayList<JTextField>();
        this.BUTTONS = new ArrayList<List<JButton>>();
        for (int index = 0; index < labels.size(); index++) {
            JCheckBox componentCheckBox = new JCheckBox();
            this.CHECKBOXES.add(componentCheckBox);
            JTextField componentTextField = new JTextField();
            this.TEXTFIELDS.add(componentTextField);
        }
    }

    public void setCheckBoxesStatus(List<Boolean> selected) {
        for (int index = 0; index < this.LABELS.size(); index++) {
            if (index < selected.size()) {
                this.CHECKBOXES.get(index).setSelected(selected.get(index));
            }
        }
    }

    public void setTextFieldsContents(List<String> contents) {
        for (int index = 0; index < this.LABELS.size(); index++) {
            if (index < contents.size()) {
                this.TEXTFIELDS.get(index).setText(contents.get(index));
            }
        }
    }

    public void setNewButtonsContents(List<String> contents) {
        List<JButton> buttons = new ArrayList<JButton>();
        for (int index = 0; index < this.LABELS.size(); index++) {
            JButton button = new JButton();
            if (index < contents.size()) {
                button.setText(contents.get(index));
            }
            buttons.add(button);
        }
        this.BUTTONS.add(buttons);
    }

    public void setTextFieldSize(int textFieldSize) {
        this.TEXTFIELDSIZE = textFieldSize;
    }

    /**
     * Add a SELECTBAR and set its height
     * @param SelectBarHeight DefaultValue is 80
     */
    public void setSelectBar(int selectBarHeight) {
        if (selectBarHeight < this.SELECTBARBUTTONHEIGHT) {
            this.SELECTBARHEIGHT = this.SELECTBARBUTTONHEIGHT;
        } else {
            this.SELECTBARHEIGHT = selectBarHeight;
        }
        this.SELECTBAR.setPreferredSize(new Dimension(0, this.SELECTBARHEIGHT));
    }


    // ******************************** PRECONFIGURED COMPONENTS ******************************** //

    
    /**
     * Return a JLabel object initiated with the class fields for the title.
     * @param mainTitle Title to display
     */
    protected JLabel createMainPanelTitle(String mainTitle) {
        JLabel mainPanelTitleLabel = new JLabel(mainTitle);
        mainPanelTitleLabel.setFont(this.TITLEFONT);
        mainPanelTitleLabel.setForeground(this.TITLECOLOR);
        int titleLength = (int) mainPanelTitleLabel.getPreferredSize().getWidth();
        mainPanelTitleLabel.setPreferredSize(new Dimension(titleLength, this.TITLEHEIGHT));
        return mainPanelTitleLabel;
    }

    /**
     * Return a JLabel for the Label on line componentsId.
     * @param componentsId Line to be printed.
     */
    protected JLabel createJLabel(int componentsId) {
        JLabel componentLabel = new JLabel(this.LABELS.get(componentsId));
        componentLabel.setFont(this.COMPONENTFONT);
        componentLabel.setForeground(COMPONENTCOLOR);
        int labelLength = (int) componentLabel.getPreferredSize().getWidth();
        componentLabel.setPreferredSize(new Dimension(labelLength, this.COMPONENTHEIGHT));
        return componentLabel;
    }

    /**
     * Return a new JCheckBox and add it to this.CHECKBOXES.
     */
    protected JCheckBox createJCheckBox(int componentId) {
        JCheckBox componentCheckBox = this.CHECKBOXES.get(componentId);
        return componentCheckBox;
    }

    /**
     * Return a JTextField sized for at most 3 caracters.
     */
    protected JTextField createJTextField(int componentId) {
        JTextField componentTextField = this.TEXTFIELDS.get(componentId);
        componentTextField.setFont(this.COMPONENTFONT);
        if (this.TEXTFIELDSIZE > 0) {
            componentTextField.setPreferredSize(new Dimension(TEXTFIELDSIZE, this.COMPONENTHEIGHT));
        } else {
            int textLength = (int) componentTextField.getPreferredSize().getWidth();
            componentTextField.setPreferredSize(new Dimension(textLength, this.COMPONENTHEIGHT));
        }
        return componentTextField;
    }

    protected JButton createJButton(int componentId, int buttonNumber, ActionListener actionListener) {
        JButton componentButton = this.BUTTONS.get(buttonNumber).get(componentId);
        int buttonWidth = (int) componentButton.getPreferredSize().getWidth();
        componentButton.setPreferredSize(new Dimension(buttonWidth, this.COMPONENTHEIGHT));
        componentButton.addActionListener(actionListener);
        return componentButton;
    }

    
    // ******************************** MAIN PART ******************************** //


    public LineComponentsTemplateFrame(MainTemplateFrame mainFrame) {
        this.MAINFRAME = mainFrame;
    }
    
    public void initialize() {
        this.MAINFRAME.setLayout(new BorderLayout());
        
        // Create the main panel
        this.MAINPANEL = new JPanel();
        this.MAINPANEL.setBackground(this.BACKGROUNDCOLOR);
        this.MAINPANEL.setLayout(new GridBagLayout());
        
        // Create the scrollbar
        this.MAINSCROLLPANE = new JScrollPane(MAINPANEL, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.MAINSCROLLPANE.getVerticalScrollBar().setUnitIncrement(this.COMPONENTHEIGHT);
        this.MAINSCROLLPANE.getHorizontalScrollBar().setUnitIncrement(this.COMPONENTSPACE);
        
        // Create the selection bar
        this.SELECTBAR = new JPanel();
        this.SELECTBAR.setBackground(this.SELECTBARCOLOR);
        this.SELECTBAR.setLayout(new GridBagLayout());
        this.SELECTBAR.setPreferredSize(new Dimension(0, this.SELECTBARHEIGHT));

        this.MAINFRAME.addComponent(this.MAINSCROLLPANE);
        this.MAINFRAME.add(this.MAINSCROLLPANE, BorderLayout.CENTER);
        this.MAINFRAME.addComponent(this.SELECTBAR);
        this.MAINFRAME.add(this.SELECTBAR, BorderLayout.SOUTH);
        this.MAINFRAME.pack();
        this.MAINFRAME.setLocationRelativeTo(null);
    }

    /**
     * To override, to call when leaving the LineComponentsTemplateFrame.
     */
    protected void closeLineComponentsTemplateFrame() {
        this.MAINFRAME.dispose();
    }


    // ******************************** COMPONENTS LAYOUT ******************************** //


    /**
     * Add the title with its coordinates on the main panel.
     * Layout constraints are defined here.
     * @param mainTitle Title to display
     */
    protected void addTitle(String mainTitle) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.insets = new Insets(this.TITLEUPPERINSET, this.TITLELEFTINSET, 0, 0);
        this.MAINPANEL.add(this.createMainPanelTitle(mainTitle), gbc);
    }
    
    /**
     * Add a components line with all coordinates on the main panel.
     * Layout constraints are defined here, depending on MAINCOMPONENTS.
     */
    protected void addLineComponents(int row, int componentsId, List<String> types, int lastLeft, ActionListener actionListener)
    throws IOException {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        int upperInset = row == 0 ? this.TITLELEFTINSET : 0;
        int buttonNumber = 0;
        for (int index = 0; index < types.size(); index++) {
            gbc.gridx = index;
            gbc.gridwidth = index + 1 == types.size() ? GridBagConstraints.REMAINDER : 1;
            gbc.weightx = index == lastLeft ? 1 : 0;
            gbc.fill = GridBagConstraints.NONE;
            int leftInset = index == 0 ? this.COMPONENTLEFTINSET : this.COMPONENTSPACE;
            int rightInset = index + 1 == types.size() ? this.COMPONENTRIGHTINSET : 0;
            gbc.insets = new Insets(upperInset, leftInset, 0, rightInset);
            switch (types.get(index)) {
                case "label":
                    this.MAINPANEL.add(this.createJLabel(componentsId), gbc);
                    break;
                case "checkbox":
                    this.MAINPANEL.add(this.createJCheckBox(componentsId), gbc);
                    break;
                case "textfield":
                    gbc.fill = this.TEXTFIELDSIZE > 0 ? GridBagConstraints.NONE : GridBagConstraints.BOTH;
                    this.MAINPANEL.add(this.createJTextField(componentsId), gbc);
                    break;
                case "button":
                    if (buttonNumber < this.BUTTONS.size()) {
                        this.MAINPANEL.add(this.createJButton(componentsId, buttonNumber, actionListener), gbc);
                        buttonNumber++;
                    } else {
                        throw new IOException("Invalid types configuration: not enough buttons");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Add an empty weighted space at the end of the main panel
     * to have every line evenly spaced.
     * @param row Y-coordinate of the space in the GridBagLayout
     */
    protected void addEOFLine(int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weighty = 1;
        gbc.gridheight = gbc.gridwidth = GridBagConstraints.REMAINDER;
        this.MAINPANEL.add(new JLabel(""), gbc);
    }

    /**
     * Add every lines to the main panel, starting with the title.
     * Add Labels, Checkboxes or textField according to MAINCOMPONENTS.
     * @param mainTitle Title to display
     */
    public void addMainComponents(String mainTitle, List<String> types, int lastLeft, ActionListener actionListener)
    throws IOException {
        int printedRow = 0;
        if (!mainTitle.equals("")) {
            // Display main title
            this.addTitle(mainTitle);
            printedRow++;
        }
        int nbTypes = types.size();
        if (nbTypes > 0) {
            if (lastLeft < 0 || lastLeft >= types.size()) {
                lastLeft = types.size() - 1;
            }
            // Display line components
            for (int componentsId = 0; componentsId < this.LABELS.size(); componentsId++) {
                try {
                    this.addLineComponents(printedRow, componentsId, types, lastLeft, actionListener);
                } catch (IOException e) {
                    throw e;
                }
                printedRow++;
            }
        }
        this.addEOFLine(printedRow);
        this.REDBUTTONADDED = false;
    }

    public void addNewLine(int afterLine, String label, boolean checked, String field, List<String> buttons) {
        // TODO
        System.out.println(afterLine);
    }

    public void removeLine(int line)
    throws IOException {
        // TODO
        System.out.println(line);
    }


    // ******************************** SELECT BAR ******************************** //


    /**
     * Add a button from left to right, on its left side.
     * @param buttonName Name that will be displayed on the button.
     */
    public void addLeftButton(String buttonName, ActionListener actionListener) {
        if (this.REDBUTTONADDED) {
            this.ERROR = "RedButton already added, can't add another button!";
            this.closeLineComponentsTemplateFrame();
        } else {
            JButton leftButton = new JButton(buttonName);
            int preferredWidth = (int) leftButton.getPreferredSize().getWidth();
            if (this.SELECTBARBUTTONMINWIDTH > preferredWidth) {
                preferredWidth = this.SELECTBARBUTTONMINWIDTH;
            }
            leftButton.setPreferredSize(new Dimension(preferredWidth, this.SELECTBARBUTTONHEIGHT));
            leftButton.setMinimumSize(new Dimension(preferredWidth, this.SELECTBARBUTTONHEIGHT));
            leftButton.setBackground(this.LEFTBUTTONCOLOR);
            leftButton.addActionListener(actionListener);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = this.SELECTBARNBBUTTONS;
            gbc.gridheight = GridBagConstraints.REMAINDER;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.BASELINE_LEADING;
            if (this.SELECTBARNBBUTTONS > 0) {
                gbc.insets = new Insets(0, this.SELECTBARSPACE, 0, 0);
            } else {
                gbc.insets = new Insets(0, this.SELECTBARINSET, 0, 0);
            }
            this.SELECTBAR.add(leftButton, gbc);
            this.SELECTBARNBBUTTONS++;
        }
    }

    /**
     * Add a RedButton after every other button of this panel.
     */
    public void addRedButton(String buttonName, ActionListener actionListener) {
        if (this.REDBUTTONADDED) {
            this.ERROR = "Trying to add a new RedButton, but already existed!";
            this.closeLineComponentsTemplateFrame();
        } else {
            JButton redButton = new JButton(buttonName);
            int preferredWidth = (int) redButton.getPreferredSize().getWidth();
            if (this.SELECTBARBUTTONMINWIDTH > preferredWidth) {
                preferredWidth = this.SELECTBARBUTTONMINWIDTH;
            }
            redButton.setPreferredSize(new Dimension(preferredWidth, this.SELECTBARBUTTONHEIGHT));
            redButton.setMinimumSize(new Dimension(preferredWidth, this.SELECTBARBUTTONHEIGHT));
            redButton.setBackground(this.REDBUTTONCOLOR);
            redButton.addActionListener(actionListener);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = this.SELECTBARNBBUTTONS;
            gbc.gridheight = gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
            gbc.insets = new Insets(0, this.SELECTBARSPACE, 0, this.SELECTBARINSET);
            this.SELECTBAR.add(redButton, gbc);
            this.REDBUTTONADDED = true;
        }
    }
}
