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
var IPC = require('ipc');

// Keep global references of some important things
var fc = null;
var mainWindow = null;
var renderer = null;
var circuit = null;

// Quit when all windows are closed.
app.on('window-all-closed', function () {
  if (process.platform != 'darwin') { // Because OS X doesn't do that
    app.quit();
  }
});

app.on('ready', function main () {
  fc = new FileController();
  renderer = (new SVGRenderer()).setup();
  circuit = new Circuit();

  // Create the main window and load the UI.
  mainWindow = new BrowserWindow({width: WIDTH, height: HEIGHT});
  mainWindow.loadUrl('file://' + __dirname + '/index.html');

});


// Set the main window to be dereferenced when closed.
mainWindow.on('closed', function () {
  mainWindow = null;
});
