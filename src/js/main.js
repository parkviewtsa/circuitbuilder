

// Global constants
const WIDTH = 800;
const HEIGHT = 600

// Load some important modules.
var app = require('app');
var BrowserWindow = require('browser-window');

// Quit when all windows are closed.
app.on('window-all-closed', function () {
  if (process.platform != 'darwin') { // Because OS X doesn't do that
    app.quit();
  }
});

app.on('ready', function main () {
  // Create the main window and load the UI.
  mainWindow = new BrowserWindow({width: WIDTH, height: HEIGHT});
  mainWindow.loadUrl('file://' + __dirname + '/index.html');
});


// Set the main window to be dereferenced when closed.
mainWindow.on('closed', function () {
  mainWindow = null;
});
