package ioTools;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import javax.swing.UIManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Class allowing you to display and navigate through files and folders
 */
public class SearchFrame extends JFrame implements ActionListener {
    
    private String CURRENTDIR;
    private List<String> CURRENTFILES = new ArrayList<String>();
    private List<String> CURRENTFOLDERS = new ArrayList<String>();
    private int FRAMEHEIGHT;
    private int FRAMEWIDTH;

    private double INITIALSCREENFACTOR = 1.3 / 2;
    private Color FONTCOLOR = new Color(31, 31, 31);

    private int BORDERSPACE = 10;
    private int VSPACE = 15;
    private int HSPACE = 15;

    private String CHOOSEFOLDERTEXT = "Choose Folder :";
    private String CHOOSEFILETEXT = "Choose File :";
    private String MAKEFOLDERTEXT = "New Folder :";
    private String MAKEFILETEXT = "New File :";
    private String CREATEFOLDERBUTTONTEXT = "OK";
    private String CREATEFILEBUTTONTEXT = " OK ";
    private String OPENBUTTONTEXT = "Open";
    private String SELECTBUTTONTEXT = "Select";
    private String BACKBUTTONTEXT = "Back";
    private String CANCELBUTTONTEXT = "Cancel";
    
    private JTextField NEWFOLDERTEXTFIELD = new JTextField();
    private JTextField NEWFILETEXTFIELD = new JTextField();
    private DefaultMutableTreeNode ROOT = new DefaultMutableTreeNode("root");
    private DefaultTreeModel FOLDERTREEMODEL = new DefaultTreeModel(ROOT);
    private JTree FOLDERTREE = new JTree(FOLDERTREEMODEL);
    private JScrollPane SCROLLPANE = new JScrollPane(FOLDERTREE);

    private String BUTTONTEXT = "";

    private String SELECTED = "";
    private ChooseType EXPECTEDTYPE = ChooseType.FILE;
    private boolean CANCREATEFILE = false;

    private String ERROR = "";


    private enum ChooseType {
        FOLDER, FILE
    }


    // ******************************** GETTERS / SETTERS ******************************** //


    public String getCurrentDirectory() {
        return this.CURRENTDIR;
    }

    public List<String> getCurrentFiles() {
        return this.CURRENTFILES;
    }

    public List<String> getCurrentFolders() {
        return this.CURRENTFOLDERS;
    }

    public String getSelected() {
        return this.SELECTED;
    }
    
    public void setVisibleRoot(boolean showRoot) {
        if (!showRoot) {
            this.FOLDERTREE.expandRow(0);
            this.FOLDERTREE.setRootVisible(false);
        }
    }

    public void setExpectedType(String type) {
        switch (type.toLowerCase().trim()) {
            case "folder":
                this.EXPECTEDTYPE = ChooseType.FOLDER;
                break;
            case "file":
                this.EXPECTEDTYPE = ChooseType.FILE;
                break;
            default:
                break;
        }
        
    }

    public void setCreateFile(boolean canCreateFile) {
        this.CANCREATEFILE = canCreateFile;
    }
    
    
    // ******************************** MAIN PART ******************************** //

    
    public SearchFrame() {
        super();
    }

    public void initialize() {
        this.CURRENTDIR = System.getProperty("user.dir");
        try {
            this.getFilesAndDirectories();
            this.buildJTree();
            this.FOLDERTREE.setCellRenderer(new MyTreeCellRenderer());
            this.setVisibleRoot(false);

            // Define default frame parameters
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setLayout(new GridBagLayout());

            // Get the frame dimensions and compute its initial location
            Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
            int FRAMEHEIGHT = (int) (this.INITIALSCREENFACTOR * dimScreen.height);
            int FRAMEWIDTH = (int) (this.INITIALSCREENFACTOR * dimScreen.width);
            this.setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
            this.setLocation((dimScreen.width-FRAMEWIDTH) / 2, (dimScreen.height-FRAMEHEIGHT) / 2);
            this.pack();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            this.ERROR = e.getMessage();
            this.closeSearchFrame();
        }
    }

    public void display() {
        this.placeComponents();
        this.update();
    }

    public void update() {
        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }

    private void closeSearchFrame() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public void actionPerformed(ActionEvent event) {
        JButton button = (JButton) event.getSource();
        this.BUTTONTEXT = button.getText();
        if (this.BUTTONTEXT.equals(this.CANCELBUTTONTEXT)) {
            this.SELECTED = "";
            this.closeSearchFrame();
        }
        if (this.BUTTONTEXT.equals(this.CREATEFOLDERBUTTONTEXT)) {
            if (!this.NEWFOLDERTEXTFIELD.getText().isBlank()) {
                String newFolderName = this.NEWFOLDERTEXTFIELD.getText();
                String newDirectory = this.CURRENTDIR + "/" + newFolderName;
                try {
                    Files.createDirectories(Paths.get(newDirectory));
                    this.getFilesAndDirectories();
                    this.buildJTree();
                    this.update();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    this.ERROR = e.getMessage();
                    this.closeSearchFrame();
                }
            }
            this.NEWFOLDERTEXTFIELD.setText("");
        }
        if (this.BUTTONTEXT.equals(this.CREATEFILEBUTTONTEXT)) {
            if (!this.NEWFILETEXTFIELD.getText().isBlank()) {
                String newFileName = this.NEWFILETEXTFIELD.getText();
                String newFullName = this.CURRENTDIR + "/" + newFileName;
                try {
                    Files.createFile(Paths.get(newFullName));
                    this.getFilesAndDirectories();
                    this.buildJTree();
                    this.update();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    this.ERROR = e.getMessage();
                    this.closeSearchFrame();
                }
            }
            this.NEWFILETEXTFIELD.setText("");
        }
        if (this.BUTTONTEXT.equals(this.OPENBUTTONTEXT)) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.FOLDERTREE.getLastSelectedPathComponent();
            if (node != null) {
                FileOrFolderNode selectedNode = (FileOrFolderNode) node.getUserObject();
                if (selectedNode.isFolder()) {
                    try {
                        this.CURRENTDIR += "/" + selectedNode.getName();
                        this.getFilesAndDirectories();
                        this.buildJTree();
                        this.update();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        this.ERROR = e.getMessage();
                        this.closeSearchFrame();
                    }
                }
            }
        }
        if (this.BUTTONTEXT.equals(this.SELECTBUTTONTEXT)) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.FOLDERTREE.getLastSelectedPathComponent();
            if (node != null) {
                FileOrFolderNode selectedNode = (FileOrFolderNode) node.getUserObject();
                boolean condition = selectedNode.isFolder() && this.EXPECTEDTYPE.equals(ChooseType.FOLDER);
                condition = condition || (!selectedNode.isFolder() && this.EXPECTEDTYPE.equals(ChooseType.FILE));
                if (condition) {
                    this.SELECTED = this.CURRENTDIR + "/" + selectedNode.getName();
                    this.closeSearchFrame();
                }
            }
        }
        if (this.BUTTONTEXT.equals(this.BACKBUTTONTEXT)) {
            try {
                this.CURRENTDIR += "/..";
                this.getFilesAndDirectories();
                this.buildJTree();
                this.update();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                this.ERROR = e.getMessage();
                this.closeSearchFrame();
            }
        }
    }


    // ******************************** FILES RELATED ******************************** //


    /**
     * Get all files and subdirectoriers in CURRENTFILES and CURRENTFOLDERS
     */
    private void getFilesAndDirectories()
    throws IOException {
        this.CURRENTFILES.clear();
        this.CURRENTFOLDERS.clear();
        File CURRENTDIRFile = new File(this.CURRENTDIR);
        if (!CURRENTDIRFile.exists()) {
            throw new IOException("Directory " + this.CURRENTDIR + " does not exists!");
        }
        for (File fileFolderName : CURRENTDIRFile.listFiles()) {
            if (fileFolderName.isDirectory()) {
                this.CURRENTFOLDERS.add(fileFolderName.getName());
            } else {
                this.CURRENTFILES.add(fileFolderName.getName());
            }
        }
        this.CURRENTFILES.sort(String::compareToIgnoreCase);
        this.CURRENTFOLDERS.sort(String::compareToIgnoreCase);
    }

    private void buildJTree() {
        this.ROOT.removeAllChildren();
        for (String folder : this.CURRENTFOLDERS) {
            DefaultMutableTreeNode folderNode = new DefaultMutableTreeNode(new FileOrFolderNode(folder, true));
            this.ROOT.add(folderNode);
        }
        if (this.EXPECTEDTYPE.equals(ChooseType.FILE)) {
            for (String file : this.CURRENTFILES) {
                DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(new FileOrFolderNode(file));
                this.ROOT.add(fileNode);
            }
        }
        this.FOLDERTREEMODEL.reload();
    }


    // ******************************** COMPONENTS LAYOUT ******************************** //


    public void placeComponents() {
        // Create each item
        JLabel chooseLabel = new JLabel(this.CHOOSEFILETEXT);
        if (this.EXPECTEDTYPE.equals(ChooseType.FOLDER)) {
            chooseLabel.setText(this.CHOOSEFOLDERTEXT);
        }
        JLabel newFolderLabel = new JLabel(this.MAKEFOLDERTEXT);
        JButton createFolderButton = new JButton(this.CREATEFOLDERBUTTONTEXT);
        createFolderButton.addActionListener(this);
        JLabel newFileLabel = new JLabel(this.MAKEFILETEXT);
        JButton createFileButton = new JButton(this.CREATEFILEBUTTONTEXT);
        createFileButton.addActionListener(this);
        JButton openButton = new JButton(this.OPENBUTTONTEXT);
        openButton.addActionListener(this);
        JButton selectButton = new JButton(this.SELECTBUTTONTEXT);
        selectButton.addActionListener(this);
        JButton backButton = new JButton(this.BACKBUTTONTEXT);
        backButton.addActionListener(this);
        JButton cancelButton = new JButton(this.CANCELBUTTONTEXT);
        cancelButton.addActionListener(this);
        
        // Set the same dimensions for all buttons
        int preferredWidth = (int) newFolderLabel.getPreferredSize().getWidth();
        int preferredHeight = (int) createFolderButton.getPreferredSize().getHeight();
        int minimumWidth = (int) newFolderLabel.getMinimumSize().getWidth();
        int minimumHeight = (int) createFolderButton.getMinimumSize().getHeight();
        Dimension preferredDimension = new Dimension(preferredWidth, preferredHeight);
        Dimension minimumDimension = new Dimension(minimumWidth, minimumHeight);
        createFolderButton.setPreferredSize(preferredDimension);
        createFolderButton.setMinimumSize(minimumDimension);
        createFileButton.setPreferredSize(preferredDimension);
        createFileButton.setMinimumSize(minimumDimension);
        openButton.setPreferredSize(preferredDimension);
        openButton.setMinimumSize(minimumDimension);
        selectButton.setPreferredSize(preferredDimension);
        selectButton.setMinimumSize(minimumDimension);
        backButton.setPreferredSize(preferredDimension);
        backButton.setMinimumSize(minimumDimension);
        cancelButton.setPreferredSize(preferredDimension);
        cancelButton.setMinimumSize(minimumDimension);

        // Place each item
        GridBagConstraints gbc = new GridBagConstraints();

        int nbLines = 0;
        
        gbc.gridx = gbc.gridy = nbLines;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.insets = new Insets(this.BORDERSPACE, this.BORDERSPACE, 0, this.BORDERSPACE);
        this.add(chooseLabel, gbc);
        nbLines++;

        gbc.gridy = nbLines;
        gbc.weightx = gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(this.HSPACE, this.BORDERSPACE, 0, this.BORDERSPACE);
        this.add(this.SCROLLPANE, gbc);
        nbLines++;

        gbc.gridy = nbLines;
        gbc.gridwidth = 1;
        gbc.weightx = gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(this.HSPACE, this.BORDERSPACE, 0, 0);
        this.add(newFolderLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(0, this.VSPACE, 0, 0);
        this.add(this.NEWFOLDERTEXTFIELD, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(0, this.VSPACE, 0, this.BORDERSPACE);
        this.add(createFolderButton, gbc);
        nbLines++;

        if (this.CANCREATEFILE && !this.EXPECTEDTYPE.equals(ChooseType.FOLDER)) {
            gbc.gridx = 0;
            gbc.gridy = nbLines;
            gbc.gridwidth = 1;
            gbc.weightx = gbc.weighty = 0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(this.HSPACE, this.BORDERSPACE, 0, 0);
            this.add(newFileLabel, gbc);

            gbc.gridx = 1;
            gbc.gridwidth = GridBagConstraints.RELATIVE;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.BASELINE;
            gbc.insets = new Insets(0, this.VSPACE, 0, 0);
            this.add(this.NEWFILETEXTFIELD, gbc);

            gbc.gridx = 3;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
            gbc.insets = new Insets(0, this.VSPACE, 0, this.BORDERSPACE);
            this.add(createFileButton, gbc);
            nbLines++;
        }

        gbc.gridx = 0;
        gbc.gridy = nbLines;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(this.HSPACE, this.BORDERSPACE, this.BORDERSPACE, 0);
        this.add(openButton, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, this.VSPACE, 0, 0);
        this.add(selectButton, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(0, this.VSPACE, 0, 0);
        this.add(backButton, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, this.VSPACE, 0, this.BORDERSPACE);
        this.add(cancelButton, gbc);
    }


    // ******************************** PRIVATE CLASSES ******************************** //


    private static class FileOrFolderNode {

        private String name;
        private boolean isFolder = true;

        public FileOrFolderNode(String nodeName, boolean isFolderNode) {
            this.name = nodeName;
            this.isFolder = isFolderNode;
        }

        public FileOrFolderNode(String nodeName) {
            this(nodeName, false);
        }

        public String getName() {
            return name;
        }

        public boolean isFolder() {
            return isFolder;
        }

        @Override
        public String toString() {
            return name;
        }
    }


    private static class MyTreeCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    
            // decide what icons you want by examining the node
            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                if (node.getUserObject() instanceof String) {
                    // Root                   
                    setIcon(UIManager.getIcon("FileView.computerIcon"));
                } else if (node.getUserObject() instanceof FileOrFolderNode) {
                    // No root
                    FileOrFolderNode contact = (FileOrFolderNode)  node.getUserObject();
                    if (contact.isFolder) {
                        setIcon(UIManager.getIcon("FileView.directoryIcon"));
                    } else {
                        setIcon(UIManager.getIcon("FileView.fileIcon"));
                    }
                }
            }
            return this;
        }
    }
}
