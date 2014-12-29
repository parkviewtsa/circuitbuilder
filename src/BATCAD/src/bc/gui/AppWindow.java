package bc.gui;

import java.awt.*;
import javax.swing.*;
import javax.media.opengl.awt.GLJPanel;

/* Imports are commented out until we need them so that NetBeans will stop
 * bugging me. */

import bc.desc.*;
import bc.layout.*;
import bc.render.*;
import bc.App;
//import bc.report.*;
//import bc.simulation.*;
public class AppWindow extends JFrame {
  GLJPanel canvas;
  
  public AppWindow ()  {
    setTitle("Testing - BATCAD 12w50a (DEVELOPMENT)");
    setSize(800, 500);

    Container contentPane = getContentPane();
    canvas = App.renderer.getSVGCanvas();
    contentPane.add(canvas);

    setVisible(true);
  }
}
