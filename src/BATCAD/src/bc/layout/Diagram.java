package bc.layout;

import org.w3c.dom.*;

import bc.renderer.*;
import bc.desc.*;

public class Diagram extends SVGDocument {

  private Circuit circuit;
  private SVGCanvas canvas;

  public Diagram (Circuit circ) {
    circuit = circ;
    canvas = new SVGCanvas();
  }

  public SVGCanvas getSVGCanvas () {
    return canvas;
  }

  public void draw () {
    Component[] c = circuit.components;
    for (int i=0; i<c.length; i++) {
      drawComponent(c[i]);
      for (int j=0; j<c.terminals.length; j++)
      if (c.terminals[j].type == POSITIVE) {
        
      }
    }
  }

}
