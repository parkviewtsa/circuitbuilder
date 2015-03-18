function SVGRenderer () {

  this.setup = function () {
    this.svg = document.getElementById('canvas');
    return this;
  }

  this.getBlankGroup = function () {
    var g = document.createElementNS(svgNS, 'g');
    this.svg.appendChild(g);
    return g;
  }

  this.addElement = function (name, appendTo) {
    var e = document.createElementNS(svgNS, name);
    appendTo.appendChild(e);
    return e;
  }

  this.getRealCoords = function (coords) {
    vpoffset = this.svg.getBoundingClientRect();
    return {
      x: coords.x,
      y: coords.y
    };
  }

}
