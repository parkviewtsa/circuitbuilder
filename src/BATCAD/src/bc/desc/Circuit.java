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


/**
 *
 * @author Brent
 */

/*
 * This class isn't totally done yet, but nothing that's going to go in here
 * will affect the API. 99% of the API is in the Component, Terminal and Wire
 * classes.
*/
public class Circuit {
  public Component[] components;
	public Wire[] wires;
  
  public Circuit () {
	//
  }
	
	public Circuit (String filename) {
		
	}
}
