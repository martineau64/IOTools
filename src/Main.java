import java.io.IOException;
import java.util.ArrayList;

import ioTools.MainTemplateFrame;
import ioTools.MainMenuTemplateFrame;
import ioTools.SearchFrame;
import ioTools.FileManager;

import structures.CustomTree;

public class Main {
	public static void main(String[] args) {
		// FileManager f = new FileManager();
		// try {
		// 	CustomTree<String> tree = f.readXMLFile("/home/marthoma/java/iotools/build/toto");
		// 	tree.display(false);
		// 	f.writeXMLFile(tree, "/home/marthoma/java/iotools/build/titi");
		// } catch (IOException e) {
		// 	System.out.println(e.getMessage());
		// }
		
		// MainTemplateFrame mainFrame = new MainTemplateFrame();
		// mainFrame.computeDimensions();

		// MainMenuTemplateFrame mainMenu = new MainMenuTemplateFrame(mainFrame);
		// mainMenu.initialize();
		// mainMenu.addTitle("Titre");
        // mainMenu.addButton("A1");
        // mainMenu.addButton("A2");
        // mainMenu.addEOFLine();
        // mainFrame.update();

		SearchFrame frame = new SearchFrame();
		frame.setCreateFile(true);
		frame.setExpectedType("folder");
		frame.initialize();
	}
}
