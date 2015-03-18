function Wire (x1, y1, x2, y2, rot1, rot2) {

  this.draw = function (x1, y1, x2, y2, rotation) {
    var width = Math.abs(x1 - x2);
    var height = Math.abs(y1 - y2);
    var pos_x = Math.min(x1, x2);
    var pos_y = Math.min(y1, y2);

    // Find a coterminal angle in the range (-pi, pi].
    while (rotation > 2) {
      rotation -= 4;
    }
    while (rotation <= -2) {
      rotation += 4;
    }

    var g = renderer.getBlankGroup();
    g.setAttribute('class', 'bc-wire');
    g.setAttribute('style',
    'transform:translate(' + pos_x + ',' + pos_y + ');');

    var e = svg.createElement('polyline');
    if ((width == 0.0 || height == 0.0) && rotation != 2) {
      /* If the two components we are trying to connect line up either
      * horizontally or vertically and we don't have to do a 180 flip, it's
      * easy. We simply draw a line from one terminal to the other. */
      e.setAttribute('points', '0,0 ' + width + ',' + height);
    } else if (rotation == 0) {
      /* Otherwise, we have to do a little more work. Typically in circuit
      * diagrams, lines can only be horizontal, vertical, or at a 45 degree
      * angle to the horizontal. This algorithm attempts to break up the wire
      * into three line segments, each of these in a permitted orientation.
      * P and Q represent the junction points between the segments. */
      float p_x, p_y, q_x, q_y;
      float aRatio = width / height;
      /* TODO:
      * Eventually this will be a conditional based on user settings. */
      if (aRatio < App.userSettings.min90ARatio) {
        // Draw a horizontal line with a 45 in the middle.
        p_x = (width / 2) - (height / 2);
        p_y = 0.0;
        q_x = (width / 2) + (height / 2);
        q_y = height;
      } else if (aRatio > App.userSettings.max90ARatio
        || App.userSettings.max90ARatio == -1.0) // Stand-in for undefined
      {
        // Draw a vertical line with a 45 in the middle.
        p_x = 0.0;
        p_y = (height / 2) - (width / 2);
        q_x = width;
        q_y = (height / 2) + (width / 2);
      } else if (aRatio > 1) {
        // Draw a horizontal line with a perpendicular segment in the middle.
        p_x = width / 2;
        p_y = 0.0;
        q_x = width / 2;
        q_y = height;
      } else { // aRatio < 1
        // Draw a vertical line with a perpendicular segment in the middle.
        p_x = 0.0;
        p_y = height / 2;
        q_x = width;
        q_y = height / 2;
      }

      e.setAttribute('points', '0,0 '
      + p_x + ',' + p_y + ' '
      + q_x + ',' + q_y
    );
  }
  else if (rotation == 1) {
    // TODO
  }
  else if (rotation == -1) {
    // TODO: will be very similar to the above case
  }
  else if (rotation == 2) {
    // 180-degree flip
    // TODO
  }
  g.appendChild(e);
  svg.appendChild(g);
}

}
