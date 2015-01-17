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
	public draw_manager.draw_instance painter;

	public Terminal from;
	public Terminal to;
	float height;
	float width;

	public Wire(Terminal a, Terminal b) {
		from = a;
		to = b;
		draw();
	}

	public final void draw() {
		/*
		 * Input: void
		 * Output: void
		 * Brief summary: Creates an SVG image of the wire and passes that image to
		 * the renderer.
		 */
		width = Math.abs(from.getGlobalXPos() - to.getGlobalXPos());
		height = Math.abs(from.getGlobalYPos() - to.getGlobalYPos());
		float pos_x = Math.min(from.getGlobalXPos(), to.getGlobalXPos());
		float pos_y = Math.min(from.getGlobalYPos(), to.getGlobalYPos());
		float rotation = to.component.orientation - from.component.orientation;
		
		// Find a coterminal angle in the range (-pi, pi].
		while (rotation > 2) {
			rotation -= 4;
		}
		while (rotation <= -2) {
			rotation += 4;
		}

		Element g = svg.createElement("g");
		g.setAttribute("class", "bc-wire");
		g.setAttribute("style",
						"transform:translate(" + pos_x + "," + pos_y + ");");

		Element e = svg.createElement("polyline");
		if ((width == 0.0 || height == 0.0) && rotation != 2) {
			/* If the two components we are trying to connect line up either
			 * horizontally or vertically and we don't have to do a 180 flip, it's
			 * easy. We simply draw a line from one terminal to the other. */
			e.setAttribute("points", "0,0 " + width + "," + height);
		} else if (rotation == 0) {
			/* Otherwise, we have to do a little more work. Typically in circuit
			 * diagrams, lines can only be horizontal, vertical, or at a 45 degree
			 * angle to the horizontal. This algorithm attempts to break up the wire
			 * into three line segments, each of these in a permitted orientation.
			 * P and Q represent the junction points between the segments. */
			float p_x, p_y, q_x, q_y;
			float aRatio = width / height;
			/* TODO:
			 * Eventually this will be a conditional based on user settings. */
			if (aRatio < App.userSettings.min90ARatio) {
				// Draw a horizontal line with a 45 in the middle.
				p_x = (width / 2) - (height / 2);
				p_y = 0.0f;
				q_x = (width / 2) + (height / 2);
				q_y = height;
			} else if (aRatio > App.userSettings.max90ARatio
							|| App.userSettings.max90ARatio == -1.0f) // Stand-in for undefined
			{
				// Draw a vertical line with a 45 in the middle.
				p_x = 0.0f;
				p_y = (height / 2) - (width / 2);
				q_x = width;
				q_y = (height / 2) + (width / 2);
			} else if (aRatio > 1) {
				// Draw a horizontal line with a perpendicular segment in the middle.
				p_x = width / 2;
				p_y = 0.0f;
				q_x = width / 2;
				q_y = height;
			} else { // aRatio < 1
				// Draw a vertical line with a perpendicular segment in the middle.
				p_x = 0.0f;
				p_y = height / 2;
				q_x = width;
				q_y = height / 2;
			}

			e.setAttribute("points", "0,0 "
							+ p_x + "," + p_y + " "
							+ q_x + "," + q_y
			);
		}
		else if (rotation == 1) {
			// TODO
		}
		else if (rotation == -1) {
			// TODO: will be very similar to the above case
		}
		else if (rotation == 2) {
			// 180-degree flip
			// TODO
		}
		g.appendChild(e);
		svg.appendChild(g);

		/* TODO:
		 * Overload draw_manager::create_instance with a form that accepts a
		 * Document. */
		painter = App.renderer.mgr.create_instance(svg, pos_x, pos_y);
	}

}
