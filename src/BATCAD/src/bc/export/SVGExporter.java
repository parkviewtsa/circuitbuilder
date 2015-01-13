package bc.export;

import bc.App;
import org.w3c.dom.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import bc.render.*;
import bc.desc.*;
// ** import bc.desc.Orientation.*;

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
