package bc.layout;

import oracle.xml.parser.v2.*;

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
    
  }

}
