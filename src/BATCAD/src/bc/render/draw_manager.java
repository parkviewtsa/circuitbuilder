package bc.render;
// not quite sure if all these imports are necessary, but they can't hurt so just clean them up later

import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;

public class draw_manager {

  // This doesn't really do any rendering itself, but relies on draw_cw for that
  boolean updated;
  
  public class imgproto {
	// MOVED from its own space into this class, since buffers and such will be context-specific

    // This will be a prototype for an SVG so it can be drawn onto an image.
	// will hold geometry & color data, probably in the form of GL buffers
	// imgproto objects should *NOT* be alloc'ed and then destroyed every frame!
	public String loadedfrom; // avoid having duplicates of the same prototype

	public imgproto(String filepath) {
	  // if this one's already loaded, that one

      // Load & parse the SVG
	  // alloc some buffers 'n stuff
	}

	public void free () {
            // Free resources like GL buffers
	  // This MUST be called to avoid memory leaks!
	  // Only call it once, and after that the object is useless
	}
	
	public void setColor (float r, float g, float b, float a) {
	  /* TODO:
	   * So that we can set the colour of an element more quickly than we would
	   * if we had to reload the entire SVG. Will be useful for highlighing
	   * elements the user is interacting with and such.
	   */
	}
	
	public void setStrokeWidth (float thickness) {
	  /* TODO:
	   * See comment for setColor(), except this one does the stroke width.
	   */
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

  public void DisplayToPanel(JPanel put_in) {
	draw_cw renderer = new draw_cw(this);
  }

  public void free () {
        // Deallocate all the memory resources that Java's GC won't get
	// (ie the GL buffers)
	for (int n = 0; n < imgprotocount; n++) {
	  imgprotos[n].free();
	}
  }
}
