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
	String style;
	if (uri.equals("")) {
	  switch (qName) {
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
	
  }
  
  @Override
  public void endDocument() {
	working = false;
  }
  
  //*** END EVENT HANDLERS ***//
  
  //*** PER-ELEMENT METHODS ***//

  
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
