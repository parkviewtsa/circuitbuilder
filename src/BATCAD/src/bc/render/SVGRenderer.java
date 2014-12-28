package bc.render;

import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;

public class SVGRenderer implements GLEventListener {

  private GLCanvas canvas;
  private SVGPrimitive[] primitives;

  public SVGPrimitive[] processSVG (SVGDocument doc,
    Map<String[], Map<String, String>[]>) {
    /* TODO:
     * Here is where you'll process the SVG (which will be passed to this method
     * as a DOM) and store it in the `primitives` field of this class. You may
     * change the type of `primitives` if you would like to store the primitives
     * differently, but please change the return type of this method to match.
     *
     * IMPORTANT: This method will only be called once for each time the
     * diagram is modified by the user, whereas display() will be called
     * once per frame (60 times a second or whatever the computer can handle).
     * Get the SVG into a much simpler format here -- process all CSS styling
     * rules, and get it into a format that basically consists of a series of
     * lines and curves, each with its own colour and stroke-width info. You
     * will then use the value stored in `primitives` to render to the screen in
     * the display() method.
     *
     * A NOTE ON CSS: I am passing you the CSS as a map mapping an array of
     * strings (representing selectors) to an array of further string-string
     * maps (representing the style rules). You will need to parse and apply
     * those style rules to the appropriate SVG elements before decomposing them
     * into primitives. If you need help with SVG or CSS, let me know.
     */

     return primitives;
  }

  public SVGRenderer () {
    GLProfile glp = GLProfile.getDefault();
    GLCapabilities caps = new GLCapabilities(glp);
    canvas = new GLCanvas(caps);

    /* Once `this` is added as an event listener, the methods below will be
     * called with `canvas` as their argument whenever a GLEvent occurs
     * (GLEvents will be part of the rendering loop, read the tutorials). */
    canvas.addGLEventListener(this);
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

    GL2 gl = drawable.getGL().getGL2();
    //start rendering here
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
