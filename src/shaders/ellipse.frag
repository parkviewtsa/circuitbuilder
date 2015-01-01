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
uniform vec2 f_1;
uniform vec2 f_2;
uniform float maj_axis;

varying vec2 pos;

void main (void)
{
  float res = length(f_1-pos) + length(f_2-pos);
  if (abs(res-maj_axis) < stroke_width) {
    gl_FragColor = stroke_color;
  } else if (res-maj_axis < -stroke_color) {
    gl_FragColor = fill_color;
  } else {
    gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
  }
}
