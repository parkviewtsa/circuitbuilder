package bc.renderer;

import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;

public class SVGRenderer implements GLEventListener {

  private GLCanvas canvas;

  public SVGRenderer () {
    GLProfile glp = GLProfile.getDefault();
    GLCapabilities caps = new GLCapabilities(glp);
    canvas = new GLCanvas(caps);

    /* Once `this` is added as an event listener, the methods below will be
     * called with `canvas` as their argument whenever a GLEvent occurs
     * (GLEvents will be part of the rendering loop, read the tutorials). */
    canvas.addGLEventListener(this);
  }

  // *** NOTE: GLAutoDrawable is a superclass of GLCanvas.

  public void init (GLAutoDrawable drawable) {
    // REQUIRED METHOD (part of GLEventListener)

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

    /* TODO:
     * This method is called whenever the user resizes the window.
     */
  }

}
