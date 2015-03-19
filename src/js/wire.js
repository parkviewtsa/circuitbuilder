function Wire () {

  this.g = renderer.getBlankGroup();
  var e = renderer.addElement('line', this.g);

  this.draw = function () {
    e.setAttribute('x1', this.x1);
    e.setAttribute('x2', this.x2);
    e.setAttribute('y1', this.y1);
    e.setAttribute('y2', this.y2);
  }

}
