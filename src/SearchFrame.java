package ioTools;

/**
 * See JFileChooser
 */

import java.util.List;
import java.util.ArrayList;
import java.io.File;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

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

import java.io.IOException;


/**
 * Class allowing you to display and navigate through files and folders
 */
public class SearchFrame extends JFrame implements ActionListener {
    
    private String currentDir;
    private List<String> currentFiles = new ArrayList<String>();
    private List<String> currentFolders = new ArrayList<String>();
    private Container inside = this.getContentPane();
    private int frameHeight;
    private int frameWidth;

    private double DEFAULTSCREENFACTOR = 1.3 / 2;
    private int SELECTBARHEIGHT = 80;
    private int SCROLLBARWIDTH = 30;
    private Color FONTCOLOR = new Color(31, 31, 31);
    private Color SELECTBARCOLOR = new Color(24, 24, 24);
    private Color SCROLLBARCOLOR = new Color(79, 79, 79);

    private int SELECTBUTTONWIDTH = 120;
    private int SELECTBUTTONHEIGHT = 60;

    private String selected = "";
    private String selectedType = "";

    /**
     * Get all files and subdirectoriers in currentFiles and currentFolders
     */
    private void getFilesAndDirectories()
    throws IOException {
        this.currentFiles.clear();
        this.currentFolders.clear();
        File currentDirFile = new File(this.currentDir);
        if (!currentDirFile.exists()) {
            throw new IOException("Directory " + this.currentDir + " does not exists!");
        }
        for (File fileFolderName : currentDirFile.listFiles()) {
            if (fileFolderName.isDirectory()) {
                this.currentFolders.add(fileFolderName.getName());
            } else {
                this.currentFiles.add(fileFolderName.getName());
            }
        }
    }


    /*************************** Comment ***************************/

    protected void test() {
        /* 1- Initialisation du container. */
        this.setLayout(new GridBagLayout());
        JLabel chooseLabel = new JLabel("Choose Download Folder :");
        JLabel folderLabel = new JLabel("Folder :");
        JTextField folderTextField = new JTextField();
        JButton makeNewFolderButton = new JButton("Make New Folder");
        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");
        okButton.setPreferredSize(cancelButton.getPreferredSize());
        okButton.setMinimumSize(cancelButton.getMinimumSize());
        JTree folderTree = new JTree();
        JScrollPane scrollPane = new JScrollPane(folderTree);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0; // la grille commence en (0, 0)
        gbc.gridwidth = GridBagConstraints.REMAINDER; // seul composant de sa colonne, il est donc le dernier.
        gbc.gridheight = 1; // valeur par défaut - peut s'étendre sur une seule ligne.
        gbc.anchor = GridBagConstraints.LINE_START; // ou BASELINE_LEADING mais pas WEST.
        gbc.insets = new Insets(10, 15, 0, 0); // Marge à gauche de 15 et marge au dessus de 10.
        /* - les attributs ipadx, ipdady, weightx et weighty valent tous 0 (valeur par défaut).
        * - l'attribut fill est à NONE, car on ne souhaite pas de redimentionnement pour cette étiquette. */
        this.add(chooseLabel, gbc);
        
        /* réutilisons le même objet <code>gbc</code>. */
        /* positionnons notre composant suivant (notre JScrollPane) sur la ligne suivante. */
        gbc.gridx = 0;
        gbc.gridy = 1;
        /* ce qui suit est inutile, nous avions déjà définie des valeurs pareilles pour le composant précédent.
        * cependant, il est toujours bon d'avoir toute les étapes dans un premier exemple. */
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1; // valeur par défaut.
        /* Nous pouvons le voir sur l'image à réaliser. Ce composant s'étend sur tout l'espace qu'il recoit aussi bien
        * horizontalement que verticalement.
        * Remarquons que c'est souvent le cas pour ce genre de composant de les laissez s'étendre un maximum possible dans 
        * le container en récupérant l'espace supplémentaire.
        */
        gbc.weightx = 1.;
        gbc.weighty = 1.;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START; // pas WEST.
        /* Marge à gauche de 15 (gardons la même que précédemment)
        * Marge au dessus de 30 et
        * Marge à droite de 10. */
        gbc.insets = new Insets(30, 15, 0, 10);
        this.add(scrollPane, gbc);

        /* le composant suivant à placer est notre étiquette.
        * Réutilisons encore le même objet gbc.*/
        gbc.gridx = 0;
        gbc.gridy = 2;
        /* une seule cellule sera disponible pour ce composant. */
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        /* Nous devons supprimer les poids que nous avons spécifiés précédemment, et supprimer
        * le redimentionnement. */
        gbc.weightx = 0.;
        gbc.weighty = 0.;
        gbc.fill = GridBagConstraints.NONE;
        /* Maintenant, nous voyons sur notre interface que le composant n'est pas le seul sur sa ligne.
        * Un champ de saisie le suit. Pour aligner correctement les étiquettes et les champs de saisie,
        * la ligne d'écriture nous facilite le travail. Nous allons l'utiliser ici. */
        gbc.anchor = GridBagConstraints.BASELINE_LEADING; // pas LINE_START ni WEST !!
        /* Une petite marge autour du composant. Attention à toujours indiquer les mêmes marges à gauche, sinon les
        * composants ne sont plus alignés. */
        gbc.insets = new Insets(10, 15, 0, 0);
        this.add(folderLabel, gbc);


        /* passons au composant suivant: le champ de saisie. */
        gbc.gridx = 1; /* une position horizontalement à droite de l'étiquette */
        gbc.gridy = 2; /* sur la même ligne que l'étiquette */
        gbc.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
        gbc.gridheight = 1; /* une seule cellule verticalement suffit */
        /* Le composant peut s'étendre sur tout l'espace qui lui est attribué horizontalement. */
        gbc.fill = GridBagConstraints.HORIZONTAL;
        /* Alignons ce composant sur la même ligne d'écriture que son étiquette. */
        gbc.anchor = GridBagConstraints.BASELINE;
        /* Une petite marge autour du composant. Remarquons que nous n'avons pas spécifié de marge au dessus du
        * composant. Comme nous avons décidé d'alginer ce composant sur la même ligne d'écriture que l'étiquette, 
        * la marge du haut sera calculée en interne pour s'aligner correctement avec l'étiquette. */
        gbc.insets = new Insets(0, 15, 0, 10);
        this.add(folderTextField, gbc);

        /* Nous pouvons passé aux boutons. */
        gbc.gridy = 3; /* nouvelle ligne */
        gbc.gridx = 0; /* première colonne, nous allons placé notre bouton "make new folder" */
        /* Reprenons l'image. Nous voyons que le bouton est plus large que l'étiquette située au dessus de lui et
        * que le champ de saisie commence avant le bord gauche du bouton. Nous précisons donc deux cellules
        * horizontalement.
        */
        gbc.gridwidth = 2;
        gbc.gridheight = 1; /* une seule cellule verticalement suffit */
        /* Nous allons alignerles boutons sur leur ligne d'écriture également.*/
        gbc.anchor = GridBagConstraints.BASELINE_LEADING;
        /* Aucun redimentionnement possible. Le bouton garde toujours soit sa taille minimum soit préférée. */
        gbc.fill = GridBagConstraints.NONE;
        /* Les attributs weightx, weighty sont tout deux à 0.*/
        /* Une petite marge autour du composant.*/
        gbc.insets = new Insets(10, 15, 10, 10);
        this.add(makeNewFolderButton, gbc);

        /* bouton suivant. */
        /* le bouton précédent peut s'étendre sur deux cellules horizontalement. Celui-ci commence en 2. */
        gbc.gridx = 2; /* pour les dubitatifs, gridy vaut toujours 3 ;-) */
        gbc.gridwidth = GridBagConstraints.RELATIVE; // le bouton est l'avant dernier composant de sa ligne.
        /* nous allons, sur ce bouton, définir un poids pour que celui-ci s'éloigne le plus du bouton précédent.*/
        gbc.weightx = 1.;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING; // Pas LINE_END, ni EAST.
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(okButton, gbc);

        /* notre dernier bouton. */
        gbc.gridx = 3;
        gbc.weightx = 0.; /* remettons le poids à zéro. */
        gbc.insets = new Insets(0, 3, 0, 10);
        this.add(cancelButton, gbc);
    }
    


    public SearchFrame()
    throws IOException {
        super();
        this.currentDir = System.getProperty("user.dir");
        try {
            this.getFilesAndDirectories();
            // Define default frame parameters
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.inside.setBackground(this.FONTCOLOR);
            Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
            this.frameHeight = (int) (this.DEFAULTSCREENFACTOR * dimScreen.height);
            this.frameWidth = (int) (this.DEFAULTSCREENFACTOR * dimScreen.width);
            this.inside.setPreferredSize(new Dimension(this.frameWidth, this.frameHeight));
            this.setLocation((dimScreen.width-this.frameWidth) / 2, (dimScreen.height-this.frameHeight) / 2);
            this.test();
            this.pack();
            this.setVisible(true);
        } catch (IOException e) {
            throw e;
        }
    }
    
    public String getCurrentDirectory() {
        return this.currentDir;
    }

    public List<String> getCurrentFiles() {
        return this.currentFiles;
    }

    public List<String> getCurrentFolders() {
        return this.currentFolders;
    }
    
    /**
     * Open a new directory.
     */
    // public void openDirectory(String newDirectory)
    // throws IOException {
    //     this.currentDir = newDirectory;
    //     try {
    //         this.getFilesAndDirectories();
    //         this.displayFolder();
    //     } catch (IOException e) {
    //         throw e;
    //     }
    // }
    
    /**
     * Display the current directory on the interface.
     */
    // public void displayFolder() {
    //     this.addSelectBar();
    //     this.addScrollBar();
    //     this.addMainFont();
    //     this.setVisible(true);
    // }

    public void actionPerformed(ActionEvent event) {
        JButton button = (JButton) event.getSource();
        switch (button.getText()) {
            case "Cancel":
                this.dispose();
                break;
            // case "Back":
            //     try{
            //         this.openDirectory(this.currentDir + "/..");
            //         System.out.println(this.currentDir);
            //     } catch (IOException error) {
            //         System.out.println(error.getMessage());
            //     }
            //     break;
            default:
                System.out.println("No instruction for this JButton !");
                break;
        }
    }
}
