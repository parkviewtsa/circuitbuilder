package bc.layout;

import bc.App;
import org.w3c.dom.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import bc.render.*;
import bc.desc.*;
// ** import bc.desc.Orientation.*;
import static bc.desc.TerminalType.*;

public class SVGExporter {

  private Circuit circuit;
  private SVGRenderer renderer;
  private Element g;
  private Document doc;

  public SVGExporter(Circuit circ) {
	circuit = circ;
	renderer = new SVGRenderer();
	g = doc.createElement("g");
	g.setAttribute("class", "-bc-circ-diagram");
	doc.appendChild(g);
  }

  public SVGRenderer getSVGRenderer() {
	return renderer;
  }

  public void draw() {
	Component[] c = circuit.components;
	float from_x, from_y, to_x, to_y;
	for (Component c1 : c) {
	  drawComponent(c1);
	  for (Terminal term : c1.terminals) {
		if (term.type == POSITIVE) {
		  from_x = term.getGlobalXPos();
		  from_y = term.getGlobalYPos();
		  to_x = term.dest.getGlobalXPos();
		  to_y = term.dest.getGlobalYPos();

		  if (from_x == to_x || from_y == to_y) {
			/*
			 * If the two components we are trying to connect line up either
			 * horizontally or vertically, it's easy. We simply draw a line from
			 * one terminal to the other.
			 *
			 */

			Element e = doc.createElement("polyline");
			e.setAttribute("points",
					from_x + "," + from_y + " "
					+ to_x + "," + to_x
			);
			g.appendChild(e);
		  } else {
			/*
			 * Otherwise, we have to do a little more work.
			 *
			 */

			float left = Math.min(from_x, to_x);
			float right = Math.max(from_x, to_x);
			float top = Math.min(from_y, to_y);
			float bottom = Math.max(from_y, to_y);
			float _width = right - left;
			float _height = bottom - top;
			float hcenter = (left + right) / 2;
			float vcenter = (top + bottom) / 2;

			/* TODO:
			 * Eventually this will be a conditional based on user settings. */
			float p_x, p_y, q_x, q_y;
			if (true) {
			  p_x = hcenter - (_height / 2);
			  q_x = hcenter + (_height / 2);
			  p_y = from_y;
			  q_y = to_y;
			}

			Element e = doc.createElement("polyline");
			e.setAttribute("points",
					from_x + "," + from_y + " "
					+ p_x + "," + p_y + " "
					+ q_x + "," + q_y + " "
					+ to_x + "," + to_x
			);
			g.appendChild(e);
		  }
		}
	  }
	}
  }

  private void drawComponent(Component c) {
	throw new UnsupportedOperationException("Not supported yet.");
	//To change body of generated methods, choose Tools | Templates.
  }
  
  public void outputSVGToConsole() {
	try {
	  DOMSource domSource = new DOMSource(doc);
	  StringWriter writer = new StringWriter();
	  StreamResult result = new StreamResult(writer);
	  Transformer t = TransformerFactory.newInstance().newTransformer();
	  t.transform(domSource, result);
	  System.out.println(writer.toString());
	} catch (TransformerException e) {
	  System.out.println("ERROR: TransformerException");
	  e.printStackTrace();
	}
  }

}
