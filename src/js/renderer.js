function SVGRenderer () {
  this.svgNS = 'http://www.w3.org/2000/svg'

  this.setup = function () {
    this.svg = document.getElementById('canvas');
  }

  this.getBlankGroup = function () {
    var g = document.createElementNS(svgNS, 'g');
    svg.appendChild(g);
    return g;
  }

}
