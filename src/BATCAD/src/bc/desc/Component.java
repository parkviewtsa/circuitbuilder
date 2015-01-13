/*
* Copyright (C) 2014 Brent Bessemer, Gokul Venkatesebaba, Asimm Hirani,
* Seth Carter, Alexandra Marlette, Linus Fennell.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package bc.desc;

import bc.App;
import bc.render.draw_manager;

public class Component {
  
  public String name;
  public String abbr;
  public int globalId;
  public int perTypeId;

  public String symbolUri;
  public float width;
  public float height;
  public float xPos; // centre of component
  public float yPos;
  public int orientation; // 0 to 3; basically (rotation CCW from standard)/90
	public draw_manager.draw_instance painter;
  public Terminal[] terminals;
  //LispExpression rulebook;
	
	public void draw () {
		painter = App.renderer.mgr.create_instance(symbolUri, xPos, yPos);
	}
}
