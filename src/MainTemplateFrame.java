package ioTools;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import java.awt.Toolkit;
import java.awt.Component;
import java.awt.Dimension;


/**
 * Class allowing you to display a frame with initial properties.
 */
public class MainTemplateFrame extends JFrame {
    
    // First display parameter
    private double INITIALSCREENFACTOR = 1.3 / 2;
    private List<Component> MAINCOMPONENTS = new ArrayList<Component>();

    public MainTemplateFrame() {
         // Define default frame parameters
         this.computeDimensions();
         this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    
    // ******************************** GETTERS / SETTERS ******************************** //
    
    
    public double getInitialScreenFactor() {
        return this.INITIALSCREENFACTOR;
    }

    public void setInitialScreenFactor(double initialScreenFactor) {
        this.INITIALSCREENFACTOR = initialScreenFactor;
    }
    
    
    // ******************************** PUBLIC METHODS ******************************** //
    
    
    /**
     * Set the initial size and location of the MainTemplateFrame.
     */
    public void computeDimensions() {
        Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int frameHeight = (int) (this.INITIALSCREENFACTOR * dimScreen.height);
        int frameWidth = (int) (this.INITIALSCREENFACTOR * dimScreen.width);
        this.setPreferredSize(new Dimension(frameWidth, frameHeight));
        this.setMinimumSize(new Dimension(frameWidth, frameHeight));
    }

    public void computeLocation() {
        Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimFrame = this.getSize();
        this.setLocation((dimScreen.width-dimFrame.width) / 2, (dimScreen.height-dimFrame.height) / 2);
    }

    public void update() {
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }

    public void addComponent(Component component) {
        this.MAINCOMPONENTS.add(component);
    }
    
    public void clean() {
        for (Component component : this.MAINCOMPONENTS) {
            this.remove(component);
        }
    }
}
