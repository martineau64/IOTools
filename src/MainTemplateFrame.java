package ioTools;

import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.Dimension;


/**
 * Class allowing you to display a frame with initial properties.
 */
public class MainTemplateFrame extends JFrame {
    
    // First display parameter
    protected double INITIALSCREENFACTOR = 1.3 / 2;
    
    public MainTemplateFrame() {
         // Define default frame parameters
         this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
         // Get the frame dimensions and compute its initial location
         Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
         int frameHeight = (int) (this.INITIALSCREENFACTOR * dimScreen.height);
         int frameWidth = (int) (this.INITIALSCREENFACTOR * dimScreen.width);
         this.setPreferredSize(new Dimension(frameWidth, frameHeight));
         this.setLocation((dimScreen.width-frameWidth) / 2, (dimScreen.height-frameHeight) / 2);
    }

    
    // ******************************** GETTERS / SETTERS ******************************** //
    
    
    public double getInitialScreenFactor() {
        return this.INITIALSCREENFACTOR;
    }

    public void setInitialScreenFactor(double initialScreenFactor) {
        this.INITIALSCREENFACTOR = initialScreenFactor;
    }
    
    
    // ******************************** INITIAL PROPERTIES ******************************** //
    
    
    /**
     * Set the initial size and location of the MainTemplateFrame.
     */
    public void computeDimensions() {
        Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int frameHeight = (int) (this.INITIALSCREENFACTOR * dimScreen.height);
        int frameWidth = (int) (this.INITIALSCREENFACTOR * dimScreen.width);
        this.setPreferredSize(new Dimension(frameWidth, frameHeight));
        this.setLocation((dimScreen.width-frameWidth) / 2, (dimScreen.height-frameHeight) / 2);
    }
}
