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

// Global constants
const WIDTH = 800;
const HEIGHT = 600

// Load some important modules.
var app = require('app');
var BrowserWindow = require('browser-window');

// Load application 'classes'
// TODO load other files

// Keep global references of some important things
var fc = null;
var mainWindow = null;
var renderer = null;
var openCircuits = [];

// Quit when all windows are closed.
app.on('window-all-closed', function () {
  if (process.platform != 'darwin') { // Because OS X doesn't do that
    app.quit();
  }
});

app.on('ready', function main () {
  fc = new FileController();
  renderer = (new SVGRenderer()).setup();
  var filenames = processArgs(args).filenames;
  if (filenames.length > 0) {
    for (int i=0; i<filenames.length; i++) {
      openCircuits.add(new Circuit(filenames.[i]));
    }
  } else {
    openCircuits.add(new Circuit());
  }

  // Create the main window and load the UI.
  mainWindow = new BrowserWindow({width: WIDTH, height: HEIGHT});
  mainWindow.loadUrl('file://' + __dirname + '/index.html');
  
});

  window.onload = function() {

	var dropZoneOne = document.querySelector('#drop-target-one');
	var dragElements = document.querySelectorAll('#drag-elements li');
	var elementDragged = null;

	for (var i = 0; i < dragElements.length; i++) {

		// Event Listener for when the drag interaction starts.
		dragElements[i].addEventListener('dragstart', function(e) {
			e.dataTransfer.effectAllowed = 'move';
			e.dataTransfer.setData('text', this.innerHTML);
			elementDragged = this;
		});

		// Event Listener for when the drag interaction finishes.
		dragElements[i].addEventListener('dragend', function(e) {
			elementDragged = null;
		});
	};

	// Event Listener for when the dragged element is over the drop zone.
	dropZoneOne.addEventListener('dragover', function(e) {
		if (e.preventDefault) {
			e.preventDefault();
		}

		e.dataTransfer.dropEffect = 'move';

		return false;
	});

	// Event Listener for when the dragged element enters the drop zone.
	dropZoneOne.addEventListener('dragenter', function(e) {
		this.className = "over";
	});

	// Event Listener for when the dragged element leaves the drop zone.
	dropZoneOne.addEventListener('dragleave', function(e) {
		this.className = "";
	});

	// Event Listener for when the dragged element dropped in the drop zone.
	dropZoneOne.addEventListener('drop', function(e) {
		if (e.preventDefault) e.preventDefault(); 
  	if (e.stopPropagation) e.stopPropagation(); 

		this.className = "";
		this.innerHTML = "Dropped " + e.dataTransfer.getData('text');

		// Remove the element from the list.
		document.querySelector('#drag-elements').removeChild(elementDragged);
		elementDragged = null;

		return false;
	});
  });
  
  // Set the main window to be dereferenced when closed.
  mainWindow.on('closed', function () {
    mainWindow = null;
  });
});
});
