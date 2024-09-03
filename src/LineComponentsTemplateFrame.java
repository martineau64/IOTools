package ioTools;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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
public class LineComponentsTemplateFrame implements ActionListener {
    
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
    protected int TITLEUPPERINSET = 30;
    protected int TITLELEFTINSET = 10;
    protected Font TITLEFONT = new Font("Comic Sans MS", Font.PLAIN, 24);
    protected Color TITLECOLOR = Color.RED;

    // Default components parameters
    protected MainComponents MAINCOMPONENTS = MainComponents.LABEL_ONLY;
    protected int COMPONENTHEIGHT = 40;
    protected int COMPONENTLEFTINSET = 60;
    protected int COMPONENTRIGHTSPACE = 10;
    protected int COMPONENTRIGHTINSET = 10;
    protected Font COMPONENTFONT = new Font("Comic Sans MS", Font.PLAIN, 18);
    protected Color COMPONENTCOLOR = Color.RED;

    // Default specific components parameters
    protected int DEFAULTCHECKBOXSIZE = 21;
    protected Color TEXTFIELDCOLOR = Color.BLACK;

    // Main components lists
    protected List<String> LABELS = new ArrayList<String>();
    protected List<JCheckBox> CHECKBOXES = new ArrayList<JCheckBox>();
    protected List<JTextField> TEXTFIELDS = new ArrayList<JTextField>();

    // Select panel parameters
    protected int SELECTBARHEIGHT = 0;
    protected Color SELECTBARCOLOR = new Color(24, 24, 24);

    protected int SELECTBARBUTTONWIDTH = 120;
    protected int SELECTBARBUTTONHEIGHT = 60;
    protected int SELECTBARNBBUTTONS = 0;
    protected int SELECTBARINSET = 10;
    protected int SELECTBARSPACE = 20;
    protected boolean CANCELBUTTONADDED = false;
    protected String CANCELBUTTONTEXT = "CANCEL";
    protected Color CANCELBUTTONCOLOR = Color.RED;
    protected Color LEFTBUTTONCOLOR = Color.WHITE;


    // ******************************** CONFIGURATIONS ******************************** //


    protected enum MainComponents {
        LABEL_ONLY, CHECKBOX, END_LINE_CHECKBOX, TEXTFIELD
    }

    /**
     * Set the type of component to display.
     * @param mainComponents One of the enum values.
     */
    protected void setMainComponents(MainComponents mainComponents) {
        this.MAINCOMPONENTS = mainComponents;
    }

    /**
     * Return a JLabel object initiated with the class fields for the title.
     * @param mainTitle Title to display
     */
    protected JLabel getMainPanelTitle(String mainTitle) {
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
    protected JLabel getLabel(int componentsId) {
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
    protected JCheckBox getCheckBox() {
        JCheckBox componentCheckBox = new JCheckBox();
        this.CHECKBOXES.add(componentCheckBox);
        return componentCheckBox;
    }

    /**
     * Return if the Checkbox on the corresponding line is checked.
     * @param componentsId Line associated to the Checkbox.
     */
    protected boolean isChecked(int componentsId) {
        return this.CHECKBOXES.get(componentsId).isSelected();
    }

    /**
     * Return a JTextField sized for at most 3 caracters.
     */
    protected JTextField getTextField() {
        JTextField componentTextField = new JTextField();
        componentTextField.setFont(this.COMPONENTFONT);
        // 100 by default, when value < 100 expected
        componentTextField.setText("100");
        int textFieldLength = (int) componentTextField.getPreferredSize().getWidth();
        componentTextField.setText("");
        componentTextField.setPreferredSize(new Dimension(textFieldLength, this.COMPONENTHEIGHT));
        this.TEXTFIELDS.add(componentTextField);
        return componentTextField;
    }

    /**
     * Return the String written in the line's TextField.
     * @param componentsId Line associated to the TextField.
     */
    protected String getText(int componentsId) {
        return this.TEXTFIELDS.get(componentsId).getText();
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
        this.MAINSCROLLPANE.getHorizontalScrollBar().setUnitIncrement(this.COMPONENTRIGHTSPACE);
        
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
    }

    public void actionPerformed(ActionEvent event) {
        JButton button = (JButton) event.getSource();
        this.BUTTONTEXT = button.getText();
    }


    // ******************************** LABELS ******************************** //


    /**
     * Add the checkbox that will be displayed.
     * @param Labels Labels to display depending on MAINCOMPONENTS.
     */
    public void setLabels(List<String> Labels) {
        this.LABELS = Labels;
    }

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
        gbc.insets = new Insets(this.TITLELEFTINSET, this.TITLEUPPERINSET, 0, 0);
        this.MAINPANEL.add(this.getMainPanelTitle(mainTitle), gbc);
    }
    
    /**
     * Add a components line with all coordinates on the main panel.
     * Layout constraints are defined here, depending on MAINCOMPONENTS.
     */
    protected void addLineComponents(int row, int componentsId) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        int upperInset = row == 0 ? this.TITLELEFTINSET : 0;
        int checkboxInset = this.COMPONENTHEIGHT > this.DEFAULTCHECKBOXSIZE ? (this.COMPONENTHEIGHT - this.DEFAULTCHECKBOXSIZE) / 2 : 0;
        gbc.insets = new Insets(upperInset, this.COMPONENTLEFTINSET, 0, 0);
        switch (this.MAINCOMPONENTS) {
            case LABEL_ONLY:
                gbc.gridwidth = GridBagConstraints.REMAINDER;    
                gbc.weightx = 1;
                this.MAINPANEL.add(this.getLabel(componentsId), gbc);
                break;
            case CHECKBOX:
                this.MAINPANEL.add(this.getCheckBox(), gbc);
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.gridx = 1;
                gbc.weightx = 1;
                gbc.insets = new Insets(upperInset, this.COMPONENTRIGHTSPACE, 0, 0);
                this.MAINPANEL.add(this.getLabel(componentsId), gbc);
                break;
            case END_LINE_CHECKBOX:
                gbc.weightx = 1;
                this.MAINPANEL.add(this.getLabel(componentsId), gbc);
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.gridx = 1;
                gbc.weightx = 0;
                gbc.insets = new Insets(upperInset, this.COMPONENTRIGHTSPACE, 0, this.COMPONENTRIGHTINSET);
                this.MAINPANEL.add(this.getCheckBox(), gbc);
                break;
            case TEXTFIELD:
                gbc.insets = new Insets(upperInset, this.COMPONENTLEFTINSET, 0, 0);
                this.MAINPANEL.add(this.getTextField(), gbc);
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.gridx = 1;
                gbc.weightx = 1;
                gbc.insets = new Insets(upperInset, this.COMPONENTRIGHTSPACE, 0, 0);
                this.MAINPANEL.add(this.getLabel(componentsId), gbc);
                break;
            default:
                break;
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
    protected void addMainComponents(String mainTitle) {
        int printedRow = 0;
        if (!mainTitle.equals("")) {
            // Display main title
            this.addTitle(mainTitle);
            printedRow++;
        }
        for (int componentsId = 0; componentsId < this.LABELS.size(); componentsId++) {
            // Display line components
            this.addLineComponents(printedRow, componentsId);
            printedRow++;
        }
        this.addEOFLine(printedRow);
        this.CANCELBUTTONADDED = false;
    }


    // ******************************** SELECT BAR ******************************** //


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

    /**
     * Add a button from left to right, on its left side.
     * @param buttonName Name that will be displayed on the button.
     * @throws IOException Can't add any other button after the CancelButton
     */
    protected void addLeftButton(String buttonName)
    throws IOException {
        if (this.CANCELBUTTONADDED) {
            throw new IOException("CancelButton already added, can't add another button!");
        }
        JButton leftButton = new JButton(buttonName);
        leftButton.setPreferredSize(new Dimension(this.SELECTBARBUTTONWIDTH, this.SELECTBARBUTTONHEIGHT));
        leftButton.setMinimumSize(new Dimension(this.SELECTBARBUTTONWIDTH, this.SELECTBARBUTTONHEIGHT));
        leftButton.setBackground(this.LEFTBUTTONCOLOR);
        leftButton.addActionListener(this);
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

    /**
     * Add a CancelButton after every other button of this panel.
     * @throws IOException Can't add several CancelButtons on one panel.
     */
    protected void addCancelButton()
    throws IOException {
        if (this.CANCELBUTTONADDED) {
            throw new IOException("Try to add new CancelButton, but already existed!");
        }
        JButton cancelButton = new JButton(this.CANCELBUTTONTEXT);
        cancelButton.setPreferredSize(new Dimension(this.SELECTBARBUTTONWIDTH, this.SELECTBARBUTTONHEIGHT));
        cancelButton.setMinimumSize(new Dimension(this.SELECTBARBUTTONWIDTH, this.SELECTBARBUTTONHEIGHT));
        cancelButton.setBackground(this.CANCELBUTTONCOLOR);
        cancelButton.addActionListener(this);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = this.SELECTBARNBBUTTONS;
        gbc.gridheight = gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(0, this.SELECTBARSPACE, 0, this.SELECTBARINSET);
        this.SELECTBAR.add(cancelButton, gbc);
        this.CANCELBUTTONADDED = true;
    }
}
