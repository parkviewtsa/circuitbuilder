// imgproto: Image prototype.
package bc.render;
// not quite sure if all these imports are necessary, but they can't hurt so just clean them up later
import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
public class imgproto
{
    // This will be a prototype for an SVG so it can be drawn onto an image.
    // will hold geometry & color data, probably in the form of GL buffers
    // imgproto objects should *NOT* be alloc'ed and then destroyed every frame!
    public imgproto(String filepath)
    {
        // Load & parse the SVG
        // alloc some buffers 'n stuff
    }
    public void imgproto_free ()
    {
        // Free resources like GL buffers
        // This MUST be called to avoid memory leaks!
        // Only call it once, and after that the object is useless
    }
}
