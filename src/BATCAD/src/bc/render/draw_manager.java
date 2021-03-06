package bc.render;
// not quite sure if all these imports are necessary, but they can't hurt so just clean them up later

import bc.export.SVGExporter;
import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
import org.w3c.dom.Document;

/**
 * An explanation on the rendering process.
 *
 * 1. A circuit is created. 2. The application requests
 * create_instance("imagefile.svg") from the rendering subsystem. 3. The
 * renderer checks to see if that image is already in memory. 4. If it isn't,
 * the renderer loads the image from its source file and breaks it into
 * components. 5. The draw_instance is made to link back to the image.
 *
 * Whenever the renderer cycles, it looks through the instances and draws each
 * one's linked image prototype to a buffer, stepping through each component
 * primitive in the prototype, and offsetting the primitive by (1) its place in
 * the image, (2) the instance's position in the environment, and (3) the
 * environment perspective.
 */
public class draw_manager {

  boolean updated;

  public enum imgcomponent_t {

	LINEAR,
	BEZIER,
	CONIC,
	TRIANGLE,
	RECT
  }

  // RENAMED imgcomponent and RESTORED OLD imgproto
  // imgproto is a composite class describing a complete image,
  // and is composed of imgcomponent objects describing individual pieces
  public class imgcomponent {
	// MOVED from its own space into this class, since buffers and such will be context-specific

	// This will be a prototype for an SVG so it can be drawn onto an image.
	// will hold geometry & color data, probably in the form of GL buffers
	// imgproto objects should *NOT* be alloc'ed and then destroyed every frame!
	/* NOTE:
	 * The imgprotos should NOT be entire SVG files. Each one of these objects
	 * represents a graphical primitive (a line, Bézier curve, triangle,
	 * rectangle, or conic section). Even a single SVG element, if it is a
	 * <path> or <polyline>, may have many imgprotos. For example, the SVG that
	 * makes the symbol for a resistor is a single <polyline>, but it consists
	 * of nine line segments, so it should be deconstructed into nine imgprotos.
	 */
	imgcomponent_t type;

	public imgcomponent() {
	  /* imgprotos should NOT be loaded from files, they
	   * should be generated by the parser */
	  
	  /* Imgcomponents vary so widely in type and structure (and necessary
	   * parameters for construction) that it would probably be better to have
	   * the constructor do nothing, and then have a static method (eg something
	   * like makeConic(focus_1, focus_2, eccentricity) or whatever) to create
	   * each type of imgcomponent. */

	  // alloc some buffers 'n stuff
	}

	public void free() {
	  // Free resources like GL buffers
	  // This MUST be called to avoid memory leaks!
	  // Only call it once, and after that the object is useless
	}

	public void setColor(float r, float g, float b, float a) {
	  /* TODO:
	   * So that we can set the colour of an element more quickly than we would
	   * if we had to reload the entire SVG. Will be useful for highlighing
	   * elements the user is interacting with and such.
	   */
	}

	public void setStrokeWidth(float thickness) {
	  /* TODO:
	   * See comment for setColor(), except this one does the stroke width.
	   */
	}
  }

  public class imgproto {
        // MOVED from its own space into this class, since buffers and such will be context-specific

	// This will be a prototype for an SVG so it can be drawn onto an image.
	// will hold geometry & color data, probably in the form of GL buffers
	// imgproto objects should *NOT* be alloc'ed and then destroyed every frame!
	imgcomponent[] components;
	int componentcount;
	public String loadedfrom; // avoid having duplicates of the same prototype

	public imgproto(String filepath) {
	  // Load & parse the SVG
	  // alloc some buffers 'n stuff
	}

	public void free() {

	  // Free resources like GL buffers
	  // This MUST be called to avoid memory leaks!
	  // Only call it once, and after that the object is useless
	}
  }

  // The protos need to be logged to deallocate them later.
  imgproto[] imgprotos;
  int imgprotocount;

  void add_imgproto(imgproto proto) {
	imgproto[] prev = imgprotos;
	imgprotos = new imgproto[imgprotocount + 1];
	for (int cur = 0; cur < imgprotocount; cur++) {
	  imgprotos[cur] = prev[cur];
	}
	imgproto out = proto;
	imgprotocount++;
  }

  void remove_imgproto(imgproto toremove) {
	imgproto[] prev = imgprotos;
	imgprotos = new imgproto[imgprotocount - 1];
	int cur = 0;
	while (cur < imgprotocount) {
	  if (prev[cur] == toremove) {
		break;
	  }
	  imgprotos[cur] = prev[cur];
	  cur++;
	}
	for (; cur < imgprotocount; cur++) {
	  imgprotos[cur] = prev[cur + 1];
	}
	imgprotocount--;
  }

  public void free() {
	// Deallocate all the memory resources that Java's GC won't get
	// (ie the GL buffers)
	for (int n = 0; n < imgprotocount; n++) {
	  imgprotos[n].free();
	}
  }

  public class draw_instance {

	// should be created and tracked by the Circuit Component class.
	public float posx, posy;
	imgproto proto;

	public draw_instance(String imgpath) {
	  // Checks imgprotos to see if the SVG is already loaded
	  // otherwise loads it anew 
	  boolean already = false;
	  for (int cur = 0; cur < imgprotocount; cur++) {
		if (imgprotos[cur].loadedfrom == imgpath) {
		  // great, it's already loaded, use the existing copy
		  proto = imgprotos[cur];
		  already = true;
		  break;
		}
	  }
	  if (!already) {
		proto = new imgproto(imgpath);
		add_imgproto(proto); // keep track of it
	  }
	}
	
	public draw_instance(Document svgDom) {
	  
	}
  }
  public draw_instance[] instances;
  public int instancecount = 0;

  public draw_instance create_instance(String path, float posx, float posy) {
	draw_instance[] prev = instances;
	instances = new draw_instance[instancecount + 1];
	for (int cur = 0; cur < instancecount; cur++) {
	  instances[cur] = prev[cur];
	}
	draw_instance out = new draw_instance(path);
	instances[instancecount] = out;
	instancecount++;
	
	out.posx = posx;
	out.posy = posy;
	return out;
  }
  
  public draw_instance create_instance(Document svg, float posx, float posy) {
	draw_instance[] prev = instances;
	instances = new draw_instance[instancecount + 1];
	for (int cur = 0; cur < instancecount; cur++) {
	  instances[cur] = prev[cur];
	}
	draw_instance out = new draw_instance(svg);
	instances[instancecount] = out;
	instancecount++;
	
	out.posx = posx;
	out.posy = posy;
	return out;
  }

  public void remove_instance(draw_instance toremove) {
	draw_instance[] prev = instances;
	instances = new draw_instance[instancecount - 1];
	int cur = 0;
	while (cur < instancecount) {
	  if (prev[cur] == toremove) {
		break;
	  }
	  instances[cur] = prev[cur];
	  cur++;
	}
	for (; cur < instancecount; cur++) {
	  instances[cur] = prev[cur + 1];
	}
	instancecount--;
  }

  public void CollectGarbage() {
	for (int n = 0; n < imgprotocount; n++) {
	  boolean used = false;
	  for (int a = 0; a < instancecount; a++) {
		if (instances[a].proto == imgprotos[n]) {
		  used = true;
		  break;
		}
	  }
	  if (!used) {
		imgprotos[n].free();
		remove_imgproto(imgprotos[n]);
	  }
	}
  }

  public float p_x, p_y, p_w, p_h;//perspective

  public void Zoom(float ratio) {
	// bigger ratio: zoom out
	// smaller ratio: zoom in
	p_w *= ratio;
	p_h *= ratio;
  }

  public void Pan(float x, float y) {
	// positive: up | right
	// negative: down | left
	p_x += x;
	p_y += y;
  }

  public void DisplayToPanel(JPanel put_in) {
	draw_cw renderer = new draw_cw(this);
	GLPbuffer pbuf = renderer.WaitForImage();
	// optimally this will be done as late as possible before the next frame,
	//in order to give it time to process in the background without blocking.

	// Now take the renderer's output and stick it in `put_in`
  }
}
