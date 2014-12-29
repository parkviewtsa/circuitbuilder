/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bc.desc;

import bc.App;
import bc.render.*;
import org.w3c.dom.*;

/**
 *
 * @author Brent
 */
public class Wire {

  public Document svg;
  public draw_manager.draw_instance draw;

  public Terminal from;
  public Terminal to;
  float height;
  float width;

  public Wire (Terminal a, Terminal b) {
	from = a;
	to = b;
	width = Math.abs(a.getGlobalXPos() - b.getGlobalXPos());
	height = Math.abs(a.getGlobalYPos() - b.getGlobalYPos());
	float pos_x = a.getGlobalXPos();
	float pos_y = a.getGlobalYPos();
	
	Element g = svg.createElement("g");
	g.setAttribute("style",
			"transform:translate(" + pos_x + "," + pos_y + ");" );
	Element e = svg.createElement("polyline");

	if (width == 0.0 || height == 0.0) {
	  /* If the two components we are trying to connect line up either
	   * horizontally or vertically, it's easy. We simply draw a line from
	   * one terminal to the other. */
	 
	  e.setAttribute("points", "0,0 " + width + "," + height);
	  g.appendChild(e);
	} else {
	  /* Otherwise, we have to do a little more work. */
	  
	  float p_x, p_y, q_x, q_y;
	
	  /* TODO:
	   * Eventually this will be a conditional based on user settings. */
	  if (true) {
		p_x = (width / 2) - (height / 2);
		p_y = 0.0f;
		q_x = (width / 2) + (height / 2);
		q_y = height;
	  }

	  e.setAttribute("points", "0,0 " +
			  p_x + "," + p_y + " " +
			  q_x + "," + q_y
	  );
	}
	g.appendChild(e);
	svg.appendChild(g);
	
	/* TODO:
	 * Overload draw_manager::create_instance with a form that accepts a
	 * Document. */
	draw = App.renderer.mgr.create_instance(svg);
  }

}
