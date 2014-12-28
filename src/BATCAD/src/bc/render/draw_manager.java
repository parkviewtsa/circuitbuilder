package bc.render;
// not quite sure if all these imports are necessary, but they can't hurt so just clean them up later
import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
public class draw_manager
{
    // This doesn't really do any rendering itself, but relies on draw_cw for that
    public class imgproto
    {
        // MOVED from its own space into this class, since buffers and such will be context-specific
        
        // This will be a prototype for an SVG so it can be drawn onto an image.
        // will hold geometry & color data, probably in the form of GL buffers
        // imgproto objects should *NOT* be alloc'ed and then destroyed every frame!
        
        public String loadedfrom; // avoid having duplicates of the same prototype
        public imgproto(String filepath)
        {
            // if this one's already loaded, that one
            
            // Load & parse the SVG
            // alloc some buffers 'n stuff
        }
        public void free ()
        {
            // Free resources like GL buffers
            // This MUST be called to avoid memory leaks!
            // Only call it once, and after that the object is useless
        }
    }
    
    // The protos need to be logged to deallocate them later.
    imgproto[] imgprotos;
    int imgprotocount;
    void add_imgproto (imgproto proto)
    {
        imgproto[] prev = imgprotos;
        imgprotos = new imgproto [imgprotocount + 1];
        for (int cur = 0; cur < imgprotocount; cur++) imgprotos[cur] = prev[cur];
        imgproto out = proto;
        imgprotocount++;
    }
    
    public class draw_instance
    {
        // should be created and tracked by the Circuit Component class.
        public float posx,posy;
        imgproto proto;
        public draw_instance (String imgpath)
        {
            // Checks imgprotos to see if the SVG is already loaded
            // otherwise loads it anew 
            for (int cur = 0; cur < imgprotocount; cur++)
                if (imgprotos[cur].loadedfrom == imgpath)
                {
                    // great, it's already loaded, use the existing copy
                    proto = imgprotos[cur];
                    break;
                };
            proto = new imgproto(imgpath);
            add_imgproto(proto); // keep track of it
        }
    }
    draw_instance[] instances;
    int instancecount = 0;
    public draw_instance create_instance ()
    {
        draw_instance[] prev = instances;
        instances = new draw_instance [instancecount + 1];
        for (int cur = 0; cur < instancecount; cur++) instances[cur] = prev[cur];
        draw_instance out = new draw_instance();
        instances[instancecount] = out;
        instancecount++;
        return out;
    }
    public void remove_instance (draw_instance toremove)
    {
        draw_instance[] prev = instances;
        instances = new draw_instance [instancecount - 1];
        int cur = 0;
        while (cur < instancecount)
        {
            if (prev[cur] == toremove) break;
            instances[cur] = prev[cur];
            cur++;
        }
        for (; cur < instancecount; cur++) instances[cur] = prev[cur + 1];
    }
    public void DisplayToPanel (JPanel put_in)
    {
        draw_cw renderer = new draw_cw(this);
    }
    public void free ()
    {
        // Deallocate all the memory resources that Java's GC won't get
        // (ie the GL buffers)
        for (int n = 0; n < imgprotocount; n++) imgprotos[n].free();
    }
}
