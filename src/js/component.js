// Definition for the Component class.
function Component (name, id, x, y) {
  this.id = id;
  this.name = name;
  this.g = renderer.getBlankGroup();
  this.orientation = 0;
  this.symbol = null;
  this.xPos = x;
  this.yPos = y;
  this.width = 30;
  this.height = 20;
  this.t1offset = {
    x: -15,
    y: 0
  };
  this.t2offset = {
    x: 15,
    y: 0
  };

  this.draw = function () {
    if (this.symbol != null) {
      this.g.innerHTML = this.symbol.getElementById('main').innerHTML;
    } else {
      this.g.innerHTML = document.getElementById('test').innerHTML;
    }
    this.g.setAttribute('style',
      'transform:rotate(' + this.orientation * 90 + 'deg) ' +
      'translate('+ (this.xPos-this.width/2) + 'px, ' + (this.yPos-this.height/2) + 'px);'
    );
    this.g.classList.add('component');
    this.g.classList.add(this.name);
    var id = this.id;
    this.g.onclick = function () {
      componentOnclick(id);
      console.log("clicked");
    }
  }
}
