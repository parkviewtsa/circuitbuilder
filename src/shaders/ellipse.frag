/*
 * Copyright (C) 2014 Brent Bessemer, Seth Carter.
 *
 * This file is part of BATCAD, which is licensed under the GNU General Public
 * Licence. See LICENCE for more information. Please note that if you use this
 * file in your own project in accordance with the terms of the GNU GPL, you
 * must include the first section of LICENCE in this comment.
 */

uniform vec4 stroke_color;
uniform vec4 fill_color;
uniform vec2 f_1; // first focus
uniform vec2 f_2; // second focus
uniform float maj_axis;
/* major axis, or, the critical value of the sum of the
 * distance to the two foci */
//uniform mat4 rotation;

varying vec4 sc_pos;

void main (void)
{
  mat4 rotation = mat4(
    1.0, 0.0, 0.0, 0.0,
    0.0, 1.0, 0.0, 0.0,
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
  )
  // A trailing underscore is my equivalent of a prime symbol.
  vec2 pos_ = (inverse(rotation)*sc_pos).xy;
  float res = length(f_1-pos_) + length(f_2-pos_);
  if (abs(res-maj_axis) < stroke_width/2) {
    gl_FragColor = stroke_color;
  } else if (res-maj_axis < -stroke_width/2) {
    gl_FragColor = fill_color;
  } else {
    gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
  }
}
