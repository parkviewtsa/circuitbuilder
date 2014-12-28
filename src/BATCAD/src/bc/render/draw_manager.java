package bc.render;
public class draw_manager
{
    public class draw_instance
    {
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
}
