import java.io.IOException;
import java.util.ArrayList;

import ioTools.MainTemplateFrame;
import ioTools.TemplateManager;

public class Main {
	public static void main(String[] args) {
		MainTemplateFrame mainFrame = new MainTemplateFrame();
		TemplateManager templateManager = new TemplateManager(mainFrame);
		templateManager.setTemplate("mainmenu");
		templateManager.initialize();
		templateManager.display();
	}
}
