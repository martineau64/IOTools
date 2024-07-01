import java.io.IOException;
import java.util.ArrayList;

import ioTools.TemplateFrame;
import ioTools.SearchFrame;
import ioTools.FileManager;

import structures.CustomTree;

public class Main {
	public static void main(String[] args) {
		FileManager f = new FileManager();
		try {
			CustomTree<String> tree = f.readXMLFile("/home/marthoma/java/iotools/build/toto");
			tree.display(false);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		// TemplateFrame newFrame = new TemplateFrame();
		// ArrayList<String> checkboxes = new ArrayList<String>();
		// for (int i = 0; i < 3; i++) {
		// 	checkboxes.add("Element " + Integer.toString(i));
		// }
		// newFrame.setSelectBar(80);
		// newFrame.setLabels("Title", checkboxes);
		// newFrame.display();

		// try {
		// 	new SearchFrame();	
		// } catch (IOException e) {
		// 	System.out.println(e.getMessage());
		// }
			
	}
}
