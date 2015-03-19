function selectTool () {
  componentOnclick = function (id) {

  }
}

function wireTool () {
  componentOnclick = function (id) {
    console.log("onclick run by" + id);
    c = circuit.getComponentById(id);
    wire = new Wire();
    wire.from = c;
    renderer.svg.onmousemove = function (e) {
      console.log(wire);
      pos = renderer.getRealCoords({x: e.clientX, y: e.clientY});
      offset = {x: pos.x - c.posX, y: pos.y - c.posY};
      if (offset > 0) {
        wire.x1 = c.posX + Math.max(c.t1offset.x, c.t2offset.x);
        wire.y1 = c.posY + Math.max(c.t1offset.y, c.t2offset.y);
      } else {
        wire.x1 = c.posX + Math.min(c.t1offset.x, c.t2offset.x);
        wire.y1 = c.posY + Math.min(c.t1offset.y, c.t2offset.y);
      }
      if (Math.abs(offset.y/offset.x) < 1.0) {
        wire.x2 = pos.x;
        y2 = wire.y1;
      } else {
        wire.y2 = pos.y;
        wire.x2 = wire.x1;
      }
      wire.draw();
    }
  }
}
