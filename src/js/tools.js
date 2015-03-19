function selectTool () {
  componentOnclick = function (id) {
    console.log("onclick fired")
    index = circuit.getIndexById(id);
    renderer.svg.onmousemove = function (e) {
      var pos = renderer.getRealCoords({x: e.clientX, y: e.clientY});
      circuit.components[index].posX = pos.x;
      circuit.components[index].posY = pos.y;
      circuit.components[index].draw();
    }
    setTimeout(function () {
      renderer.svg.onclick = function (e) {
        renderer.svg.onmousemove = null;
        setTimeout(function () {renderer.svg.onclick = null;}, 10);
      }
    }, 100);
  }
}

function wireTool () {
  console.log("Using Wire tool");
  var start, pos, offset, wire;
  startClick = function (id, e) {
    c = circuit.getComponentById(id);
    wire = new Wire();
    wire.from = c;
    start = renderer.getRealCoords({x: e.clientX, y: e.clientY});
    renderer.svg.onmousemove = function (e) {
      pos = renderer.getRealCoords({x: e.clientX, y: e.clientY});
      offset = {x: pos.x - start.x, y: pos.y - start.x};
      wire.x1 = start.x;
      wire.y1 = start.y;
      if (Math.abs(offset.y/offset.x) < 1.0) {
        wire.x2 = pos.x;
        wire.y2 = start.y;
      } else {
        wire.y2 = pos.y;
        wire.x2 = start.x;
      }
      wire.draw();
    }
    setTimeout(function () {
      renderer.svg.onclick = function (e) {
        start = {x: wire.x2, y: wire.y2};
        wire = new Wire();
      }
    }, 100);
    componentOnclick = endClick;
  }
  endClick = function (id, e) {
    c = circuit.getComponentById(id);
    wire.to = c;
    renderer.svg.onclick = null;
    renderer.svg.onmousemove = null;
    componentOnclick = startClick;
  }
  componentOnclick = startClick;
}
