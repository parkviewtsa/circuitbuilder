/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bc;

import bc.gui.*;
import bc.file.*;
import bc.layout.*;
import bc.desc.*;
import java.util.ArrayList;

public class Application {

  public static FileController fc;
  public static ArrayList<Circuit> openCircuits;
  public static Diagram renderer;
  public static AppWindow mainWindow;

  public static void main (String[] args) {
    fc = new FileController();
    openCircuits = new ArrayList();
    /*String filename = processArgs(args).filename;
    if (filename) activeCircuit = fc.load(filename);
    else*/ openCircuits.add(new Circuit());

    renderer = new Diagram(openCircuits.get(0));

    mainWindow = new AppWindow();
  }

}
