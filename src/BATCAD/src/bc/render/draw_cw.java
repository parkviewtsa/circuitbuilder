// draw_cw: Draw circuit window.
package bc.render;
// not quite sure if all these imports are necessary, but they can't hurt so just clean them up later

import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;

public class draw_cw {

  GLPbuffer buf; // An offscreen buffer; we will render to this.
  /* TODO:
   * Figure out why NetBeans says GLPbuffer doesn't exist, when the JOGL docs
   * say it does, in javax.media.opengl.
   */

  public draw_cw(draw_manager what) {
	/* This function will begin the rendering into an off-screen image, probably
	 * into a context owned by draw_manager. */

	GL2 gl = buf.getGL().getGL2();
	// start rendering here
	for (int i = 0; i < what.instancecount; i++) {
	  draw_manager.draw_instance instance = what.instances[i];
	  for (int j = 0; j < instance.proto.componentcount; j++) {
		draw_manager.imgcomponent component = instance.proto.components[j];
		// Calculate relative position and size.
		// Issue draw commands.
	  }
	}
  }

  public GLPbuffer WaitForImage() {
	/* This function will wait for the rendering to complete. This might be
	 * called by the draw_manager. */
	// something like "glFinish();", and done.
	return buf;
	// draw_manager will handle putting the buffer on-screen into the JPanel
  }
}
