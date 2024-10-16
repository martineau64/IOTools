package ioTools;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ioTools.SearchFrame;
import ioTools.MainTemplateFrame;
import ioTools.MainMenuTemplateFrame;
import ioTools.LineComponentsTemplateFrame;
import ioTools.FileManager;
import structures.CustomTree;


public class TemplateManager implements ActionListener {
    
    protected static Object LOCK = new Object();
    
    // Common parameters
    protected Template TEMPLATE = Template.MAINMENU;
    protected MainTemplateFrame MAINFRAME;

    protected JButton BUTTON = new JButton();
    protected String BUTTONTEXT = "";
    
    // Main menu parameters
    protected String MAINTITLE = "";
    protected MainMenuTemplateFrame MAINMENUTEMPLATE;

    // Line components parameters
    protected String LINECOMPONENTSTITLE = "";
    protected LineComponentsTemplateFrame LINECOMPONENTSTEMPLATE;
    protected String ROOT = "%root%";

    protected enum Template {
        MAINMENU, LINECOMPONENTS
    }

    public void setMainTitle(String mainTitle) {
        this.MAINTITLE = mainTitle;
    }

    public TemplateManager(MainTemplateFrame mainFrame) {
        this.MAINFRAME = mainFrame;
        this.MAINMENUTEMPLATE = new MainMenuTemplateFrame(mainFrame);
        this.LINECOMPONENTSTEMPLATE = new LineComponentsTemplateFrame(mainFrame);
    }

    public void setTemplate(String template) {
        switch (template.toLowerCase().trim()) {
            case "mainmenu":
                this.TEMPLATE = Template.MAINMENU;
                break;
            case "linecomponents":
                this.TEMPLATE = Template.LINECOMPONENTS;
                break;
            default:
                break;
        }
    }

    public void initialize() {
        switch (this.TEMPLATE) {
            case MAINMENU:
                this.MAINMENUTEMPLATE.initialize();
                break;
            case LINECOMPONENTS:
                this.LINECOMPONENTSTEMPLATE.initialize();
                break;
            default:
                break;
        }
    }

    protected void displayMainMenu() {
        System.out.println("Call to display");
    }

    protected void displayLineComponents() {
        System.out.println("Call to display");
    }

    public void display() {
        switch (this.TEMPLATE) {
            case MAINMENU:
                this.displayMainMenu();
                break;
            case LINECOMPONENTS:
                this.displayLineComponents();
                break;
            default:
                break;
        }
    }

    protected void openSearchFrame(String expectedType, boolean canCreateFile) {
        SearchFrame searchFrame = new SearchFrame();
        searchFrame.setExpectedType(expectedType);
        searchFrame.setCreateFile(canCreateFile);
        searchFrame.initialize();
        searchFrame.display();
        searchFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                closeSearchFrame(searchFrame.getSelected());
            }
        });
    }

    protected void closeSearchFrame(String selected) {
        System.out.println("Closing Search Frame");
    }

    protected void performMainMenu() {
        System.out.println("Using BUTTONTEXT");
    }

    protected void performLineComponents() {
        System.out.println("Using BUTTONTEXT");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        this.BUTTON = (JButton) event.getSource();
        this.BUTTONTEXT = this.BUTTON.getText();
        switch (this.TEMPLATE) {
            case MAINMENU:
                performMainMenu();
                break;
            case LINECOMPONENTS:
                performLineComponents();
                break;
            default:
                break;
        }
    }


    // ******************************** PRECONFIGURED METHODS ******************************** //


    protected void openTitledList(String file)
    throws IOException {
        try {
            List<String> labels = new ArrayList<String>();
            List<Boolean> firstSelected = new ArrayList<Boolean>();
            FileManager fileManager = new FileManager();
            CustomTree<String> mainTree = fileManager.readXMLFile(file);
            CustomTree<String> titleTree = mainTree.getChild("Title");
            this.LINECOMPONENTSTITLE = titleTree.getChild(0).getLabel();
            CustomTree<String> labelsTree = mainTree.getChild("Tasks");
            for (CustomTree<String> node : labelsTree.getChildren()) {
                labels.add(node.getLabel());
                if (node.getNbChildren() == 1) {
                    firstSelected.add(node.getChild(0).getLabel().equals("x"));
                }
            }
            this.LINECOMPONENTSTEMPLATE.setLabels(labels);
            this.LINECOMPONENTSTEMPLATE.setCheckBoxesStatus(firstSelected);
            this.TEMPLATE = Template.LINECOMPONENTS;
        } catch (IOException e) {
            throw e;
        }
    }

    protected void writeTitledList(String file)
    throws IOException {
        try {
            FileManager fileManager = new FileManager();
            CustomTree<String> mainTree = new CustomTree<String>(this.ROOT);
            mainTree.addChild("Title");
            mainTree.getChild("Title").addChild(this.LINECOMPONENTSTITLE);
            mainTree.addChild("Tasks");
            CustomTree<String> labelsTree = mainTree.getChild("Tasks");
            int index = 0;
            for (String label : this.LINECOMPONENTSTEMPLATE.getLabels()) {
                labelsTree.addChild(label);
                CustomTree<String> node = labelsTree.getChild(label);
                if (this.LINECOMPONENTSTEMPLATE.getIsChecked(index)) {
                    node.addChild("x");
                } else {
                    node.addChild("");
                }
                index++;
            }
            fileManager.writeXMLFile(mainTree, file);
        } catch (IOException e) {
            throw e;
        }
    }
}
