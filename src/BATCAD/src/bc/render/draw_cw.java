// draw_cw: Draw circuit window.
package bc.render;
// not quite sure if all these imports are necessary, but they can't hurt so just clean them up later
import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
public class draw_cw
{
    public draw_cw (draw_manager what)
    {
        // This function will begin the rendering into an off-screen image, probably into a context owned by draw_manager.
    }
    public void WaitForImage ()
    {
        // This function will wait for the rendering to complete. This might be called by the draw_manager.
    }
}
