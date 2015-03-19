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

// IPC stuff.

// Definition and constructor for the Circuit class
function Circuit () {

  this.components = [];
  this.maxId = -1;

  this.addNew = function (type, pos) {
    id = this.maxId+1;
    component = new Component(type, id, pos.x, pos.y);
    this.components.push(component);
    this.maxId = id;
    this.components[this.components.length-1].draw();
  }

  this.getComponentById = function (id) {
    if (this.components[id].id == id) {
      return this.components[id];
    } else {
      for (i=0; i<this.components.length; i++) {
        if (this.components[i].id == id) return this.components[i];
      }
      return null;
    }
  }

}
