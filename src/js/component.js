// Definition for the Component class.
function Component (name, id, x, y) {
  this.id = id;
  this.name = name;
  this.g = renderer.getBlankGroup();
  this.orientation = 0;
  this.symbol = null;
  this.xPos = x;
  this.yPos = y;

  this.draw = function () {
    if (this.symbol != null) {
      this.g.innerHTML = this.symbol.getElementById('main').innerHTML;
    }
    // TODO buggy
    this.g.setAttribute('style',
      'transform:rotate(' + this.orientation * 90 + 'deg) ' +
      'translate('+ this.xPos + 'px, ' + this.yPos + 'px);'
    );
    this.g.classList.add('component');
    this.g.classList.add(this.name);
  }
}
