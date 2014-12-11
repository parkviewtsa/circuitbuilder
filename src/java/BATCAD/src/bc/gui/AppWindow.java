package bc.gui;

import javax.swing.*;

/* Imports are commented out until we need them so that NetBeans will stop
 * bugging me. */

//import bc.desc.*;
//import bc.fileio*;
//import bc.layout.*;
//import bc.report.*;
//import bc.simulation.*;

public class AppWindow extends JFrame {
  SVGRenderer canvas;

  public AppWindow ()  {
    setTitle("Testing - BATCAD 12w50a (DEVELOPMENT)");
    setSize(800, 500);

    Container contentPane = getContentPane();
    canvas = new SVGRenderer();
    contentPane.add(canvas);

    setVisible(true); // Display the window.
  }
}
