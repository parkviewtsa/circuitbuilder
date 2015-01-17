/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bc;

import bc.gui.*;
import bc.file.*;
import bc.desc.*;
import bc.render.SVGRenderer;

import java.util.ArrayList;
import java.util.Arrays;

public class App {

	public static FileController fc;
	public static ArrayList<Circuit> openCircuits;
	public static SVGRenderer renderer;
	public static AppWindow mainWindow;
	public static UserSettings userSettings;

	public static void main(String[] args) {
		fc = new FileController();
		openCircuits = new ArrayList();
		renderer = (new SVGRenderer()).setup();
		ArrayList<String> filenames = processArgs(args).filenames;
		if (filenames.size() > 0) {
			for (int i=0; i<filenames.size(); i++) {
				openCircuits.add(new Circuit(filenames.get(i)));
			}
		} else {
			openCircuits.add(new Circuit());
		}

		mainWindow = new AppWindow();
	}
	
	public class Args {
		ArrayList<String> filenames;
		public void init () {
			filenames = new ArrayList<>();
		}
	}
	
	public static Args processArgs (String[] argv) {
		/*
		 * Process the command line arguments.
		 * There is a problem somewhere in this method - perhaps because static
		 * methods can't utilise internal classes. We need to figure this out.
		 */
		Args args;
		args.init();
		args.filenames.addAll(Arrays.asList(argv));
		return args;
	}

}
