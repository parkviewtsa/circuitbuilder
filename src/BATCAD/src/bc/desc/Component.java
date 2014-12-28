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

import line.*;
import bc.layout.SVGDocument;

public enum Orientation {
  NORMAL, CLOCKWISE, COUNTER_CW, INVERTED
}

public class Component {
  String name;
  String abbr;
  int globalID;
  int perTypeID;

  SVGDocument symbol;
  float width;
  float height;
  float xPos; // centre of component
  float yPos;
  Orientation orientation;

  Terminal[] terminals;
  LispExpression rulebook;
}
