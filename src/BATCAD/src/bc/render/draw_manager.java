package bc.render;
// not quite sure if all these imports are necessary, but they can't hurt so just clean them up later
import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.*;
public class draw_manager
{
    // This doesn't really do any rendering itself, but relies on draw_cw for that
    public class draw_instance
    {
        // should be created and tracked by the Circuit Component class.
        float posx,posy;
        imgproto proto;
    }
    draw_instance[] instances;
    int instancecount = 0;
    public draw_instance create_instance ()
    {
        draw_instance[] prev = instances;
        instances = new draw_instance [instancecount + 1];
        for (int cur = 0; cur < instancecount; cur++) instances[cur] = prev[cur];
        instances[instancecount] = new draw_instance();
        instancecount++;
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
}
