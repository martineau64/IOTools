package ioTools;

import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import ioTools.MainTemplateFrame;


/**
 * Class allowing you to display a menu frame.
 */
public class MainMenuTemplateFrame {
    
    protected MainTemplateFrame MAINFRAME;
    protected JPanel MAINPANEL;
    protected JScrollPane MAINSCROLLPANE;

    // Default display parameter
    protected double INITIALSCREENFACTOR = 1.3 / 2;
    protected Color BACKGROUNDCOLOR = new Color(31, 31, 31);

    // Buffers
    protected String BUTTONTEXT = "";
    protected int PRINTEDROW = 0;

    // Default title parameters
    protected int TITLEHEIGHT = 50;
    protected int TITLEUPPERINSET = 30;
    protected Font TITLEFONT = new Font("Comic Sans MS", Font.BOLD, 24);
    protected Color TITLECOLOR = Color.RED;
    
    // Default buttons parameters
    protected int BUTTONHEIGHT = 40;
    protected int BUTTONMINWIDTH = 150;
    protected int BUTTONUPPERLOWERINSET = 10;
    protected Font BUTTONFONT = new Font("Comic Sans MS", Font.PLAIN, 18);
    protected Color BUTTONCOLOR = Color.WHITE;
    protected Color BUTTONTEXTCOLOR = Color.BLACK;


    // ******************************** PRECONFIGURED COMPONENTS ******************************** //


    /**
     * Return a JLabel object initiated with the given title.
     * @param mainTitle Title of the main frame.
     */
    protected JLabel getMainPanelTitle(String mainTitle) {
        JLabel label = new JLabel(mainTitle);
        label.setFont(this.TITLEFONT);
        label.setForeground(this.TITLECOLOR);
        int titleLength = (int) label.getPreferredSize().getWidth();
        label.setPreferredSize(new Dimension(titleLength, this.TITLEHEIGHT));
        return label;
    }

    
    /**
     * Return a JButton object initiated with the given text.
     * @param buttonText Text to display on the button.
     */
    protected JButton getMainPanelButton(String buttonText, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.setFont(this.BUTTONFONT);
        button.setBackground(this.BUTTONCOLOR);
        button.setForeground(this.BUTTONTEXTCOLOR);
        int preferredWidth = (int) button.getPreferredSize().getWidth();
        if (this.BUTTONMINWIDTH > preferredWidth) {
            preferredWidth = this.BUTTONMINWIDTH;
        }
        button.setPreferredSize(new Dimension(preferredWidth, this.BUTTONHEIGHT));
        button.setMinimumSize(new Dimension(preferredWidth, this.BUTTONHEIGHT));
        button.addActionListener(actionListener);
        return button;
    }


    // ******************************** MAIN PART ******************************** //


    public MainMenuTemplateFrame(MainTemplateFrame mainFrame) {
        this.MAINFRAME = mainFrame;
    }
    
    public void initialize() {
        // Create the main panel
        this.MAINPANEL = new JPanel();
        this.MAINFRAME.setLayout(new BorderLayout());
        this.MAINPANEL.setBackground(this.BACKGROUNDCOLOR);
        this.MAINPANEL.setLayout(new GridBagLayout());

        // Create the scrollbar
        this.MAINSCROLLPANE = new JScrollPane(MAINPANEL, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        int increment = this.BUTTONHEIGHT + 2 * this.BUTTONUPPERLOWERINSET;
        this.MAINSCROLLPANE.getVerticalScrollBar().setUnitIncrement(increment);

        this.MAINFRAME.addComponent(this.MAINSCROLLPANE);
        this.MAINFRAME.add(this.MAINSCROLLPANE, BorderLayout.CENTER);
        this.MAINFRAME.pack();
        this.MAINFRAME.setLocationRelativeTo(null);

        this.PRINTEDROW = 0;
    }


    // ******************************** COMPONENTS LAYOUT ******************************** //


    /**
     * Add the title with its coordinates on the main panel.
     * Layout constraints are defined here.
     * @param mainTitle Title to add
     */
    public void addTitle(String mainTitle) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 0.5;
        gbc.anchor = GridBagConstraints.ABOVE_BASELINE;
        gbc.insets = new Insets(0, this.TITLEUPPERINSET, 0, 0);
        this.MAINPANEL.add(this.getMainPanelTitle(mainTitle), gbc);
        this.PRINTEDROW++;
    }

    /**
     * Add a new button to the frame. Must be done before calling addEOFLine.
     * @param buttonText Text to display on the button.
     */
    public void addButton(String buttonText, ActionListener actionListener) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = this.PRINTEDROW;
        gbc.weighty = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(this.BUTTONUPPERLOWERINSET, 0, this.BUTTONUPPERLOWERINSET, 0);
        this.MAINPANEL.add(this.getMainPanelButton(buttonText, actionListener), gbc);
        this.PRINTEDROW++;
    }

    /**
     * Add an empty weighted space at the end of the main panel
     * to have every line evenly spaced.
     */
    public void addEOFLine() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = this.PRINTEDROW;
        gbc.weighty = 0.5;
        gbc.gridheight = gbc.gridwidth = GridBagConstraints.REMAINDER;
        JLabel EOFLine = new JLabel("");
        EOFLine.setPreferredSize(new Dimension(this.TITLEHEIGHT, this.TITLEHEIGHT));
        this.MAINPANEL.add(EOFLine, gbc);
    }
}
