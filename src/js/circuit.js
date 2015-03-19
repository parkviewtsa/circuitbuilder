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

  this.getIndexById = function (id) {
    if (this.components[id].id == id) {
      return id;
    } else {
      for (i=0; i<this.components.length; i++) {
        if (this.components[i].id == id) return i;
      }
      return null;
    }
  }

}
