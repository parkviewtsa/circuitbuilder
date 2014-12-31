/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bc.render;

import java.util.ArrayList;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Brent
 */
public class ImgprotoFactory extends DefaultHandler {
  private draw_manager.imgproto current;
  private ArrayList<draw_manager.imgcomponent> components;
  private String groupStyle;
  private String pGroupStyle; // parent group style
  
  public boolean working;
  
  public ImgprotoFactory() {
	super();
	working = false;
  }
  
  private class Point {
	public float x;
	public float y;
	
	public Point (float x, float y) {
	  this.x = x;
	  this.x = y;
	}
  }
  
  private class Path {
	private float curX;
	private float curY;
	
	/* Each of these methods represents one or more of the commands in the
	 * <path> element's "d" (data) attribute. The command(s) that each
	 * represents is listed next to the method declaration. */
	
	public void absMoveTo (float x, float y) { // M
	  curX = x;
	  curY = y;
	}
	
	public void relMoveTo (float dx, float dy) { // m
	  curX += dx;
	  curY += dy;
	}
	
	public void absLineTo (float x, float y) { // l, h, v
	  
	}
	
	public void relLineTo (float dx, float dy) { // L, H, V
	  
	}
	
	public void absCubicBezierTo (float c1_x, float c1_y, float c2_x,
	  float c2_y, float f_x, float f_y) // C, S
	{
	  
	}
	
	public void relCubicBezierTo (float c1_x, float c1_y, float c2_x,
	  float c2_y, float f_x, float f_y) // c, s
	{
	  
	}
	
	public void absQuadBezierTo (float c_x, float c_y, float f_x, float f_y) {
	  // Q, T
	}
	
	public void relQuadBezierTo (float c_x, float c_y, float f_x, float f_y) {
	  // q, t
	}
	
	public void done () { // Z, z
	  
	}
  }
  
  //*** EVENT HANDLERS ***//
  
  @Override
  public void startDocument() {
	working = true;
  }
  
   @Override
  public void startElement(String uri, String localName, String qName,
	Attributes attr)
  {
	float x, y, cx, cy, r, rx, ry, w, h;
	Point[] points;
	Path path;
	String style;
	if (uri.equals("")) {
	  switch (qName) {
		case "g":
		  // TODO: not sure about this
		  pGroupStyle = pGroupStyle + groupStyle;
		  groupStyle = attr.getValue("style");
		  break;
		case "circle":
		  cx = Float.parseFloat(attr.getValue("cx"));
		  cy = Float.parseFloat(attr.getValue("cy"));
		  r = Float.parseFloat(attr.getValue("r"));
		  style = attr.getValue("style");
		  circle(cx, cy, r, style);
		  break;
		case "ellipse":
		  cx = Float.parseFloat(attr.getValue("cx"));
		  cy = Float.parseFloat(attr.getValue("cy"));
		  rx = Float.parseFloat(attr.getValue("rx"));
		  ry = Float.parseFloat(attr.getValue("ry"));
		  style = attr.getValue("style");
		  ellipse(cx, cy, rx, ry, style);
		  break;
		case "rect":
		  x = Float.parseFloat(attr.getValue("x"));
		  y = Float.parseFloat(attr.getValue("y"));
		  w = Float.parseFloat(attr.getValue("width"));
		  h = Float.parseFloat(attr.getValue("height"));
		  style = attr.getValue("style");
		  rectangle(x, y, w, h, style);
		  break;
		case "polygon":
		  points = parsePointsString(attr.getValue("points"));
		  polygon(points);
		  break;
		case "line":
		  break;
	  	case "polyline":
		  points = parsePointsString(attr.getValue("points"));
		  polyline(points);
		  break;
		case "path":
		  path = parsePathString(attr.getValue("d"));
		  break;
		case "text":
		  break;
		default:
		  break;
	  }
	} else {
	}
	
	
  }
  
  @Override
  public void endElement(String uri, String localName, String qName) {
	groupStyle = pGroupStyle;
  }
  
  @Override
  public void endDocument() {
	working = false;
  }
  
  //*** END EVENT HANDLERS ***//
  
  //*** PER-ELEMENT METHODS ***//
  
  /* TODO:
   * Each of these methods needs to create the necessary imgcomponents for the
   * specified graphical primitive, and add them to the components ArrayList.
   * CSS processing also needs to take place here, both with the style parameter
   * to each method, and with the groupStyle class field (which comes from <g>
   * elements).
   */
  
  private void circle (float cx, float cy, float r, String style) {
	
  }
  
  private void ellipse (float cx, float cy, float rx, float ry, String style) {
	
  }
  
  private void rectangle (float x, float y, float w, float h, String style) {
	
  }
  
  private void polygon (Point[] points) {
	
  }
  
  private void polyline (Point[] points) {
	
  }
  
  //*** END PER-ELEMENT METHODS ***//
  
  //*** ATTRIBUTE STRING PARSERS ***//
  
  private Point[] parsePointsString (String str) {
	
  }
  
  private Path parsePathString (String str) {
	
  }
  
  //*** OTHER STUFF ***//
  
  public draw_manager.imgproto get() {
	while (working) {} // wait
	return current;
  }
}
