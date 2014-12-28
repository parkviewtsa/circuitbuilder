package bc.render;

import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;

public class SVGRenderer implements GLEventListener {

  private GLCanvas canvas;

  public SVGRenderer () {
    GLProfile glp = GLProfile.getDefault();
    GLCapabilities caps = new GLCapabilities(glp);
    canvas = new GLJPanel(caps);   
  }
  
  public SVGRenderer setup () {
	/* TODO:
	 * NetBeans wanted this call out of the constructor,  so here we go.
	 * This will now have to be called on every new SVGRenderer. */
	
	/* Once `this` is added as an event listener, the methods below will be
     * called with `canvas` as their argument whenever a GLEvent occurs
     * (GLEvents will be part of the rendering loop, read the tutorials). */
    canvas.addGLEventListener(this);
	
	/* returns itself so that we can call
	 * SVGRenderer foo = (new SVGRenderer()).setup(); */
	return this;
  }

  /*** GL METHODS START HERE ***
   *** DO NOT PUT ANYTHING OTHER THAN GL METHODS BELOW THIS POINT ***/

  // *** NOTE: GLAutoDrawable is a superclass of GLCanvas.
  public draw_manager manager;
  public void init (GLAutoDrawable drawable) {
    // REQUIRED METHOD (part of GLEventListener)
      manager = new draw_manager();
    /* TODO:
     * This is where initialisation code goes (obviously).
     */
  }

  public void display (GLAutoDrawable drawable) {
    // REQUIRED METHOD (part of GLEventListener)
	
	/* The rendering loop here will be called 60x a second or so. To reduce
	 * resource load, we should render the diagram to a buffer, then display it
	 * to the screen every frame, but only re-render the buffer when the diagram
	 * has changed. We could also potentially do the re-rendering of the diagram
	 * asynchronously. */
	
	if (manager.updated) {
	  // Diagram has changed, re-render the buffer.
	}
	
	// Copy the buffer to the screen.
  }


  public void dispose (GLAutoDrawable drawable) {
    // REQUIRED METHOD (part of GLEventListener)

    /* TODO:
     * Cleanup code.
     */
  }

  public void reshape (GLAutoDrawable drawable,
    int x, int y, int width, int height) {
    // REQUIRED METHOD (part of GLEventListener)
      
//** Nothing needs to change except:
//	glViewport(0,0,width,height);
//      but maybe Swift's window system & Java's GL bindings need more work
//**
      
    /* TODO:
     * This method is called whenever the user resizes the window.
     */
  }

}
