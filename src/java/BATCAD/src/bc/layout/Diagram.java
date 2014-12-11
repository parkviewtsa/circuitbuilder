package bc.layout;

import bc.renderer.*;
import bc.desc.*;

public class Diagram {

  private Circuit circuit;
  private SVGCanvas canvas;

  public Diagram (Circuit circ) {
    circuit = circ;
    canvas = new SVGCanvas();
  }

  public SVGCanvas getSVGCanvas () {
    return canvas;
  }

  //public void draw () {}

}
