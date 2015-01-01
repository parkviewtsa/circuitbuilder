/*
 * Copyright (C) 2014 Brent Bessemer, Seth Carter.
 * See LICENSE for licensing info.
 */

/* We only need one vertex shader. All it has to do is transform the screen
 * coordinates into viewport coordinates. */

uniform vec2 screen_size;

attribute vec2 sc_pos;

void main (void)
{
  /* This could potentially be done in one step if it improves performance,
   * but I found this more readable. */
  float x_pos = 2.0*(sc_pos.x/screen_size.x) - 1.0;
  float y_pos = 2.0*(sc_pos.y/screen_size.y) - 1.0;
  gl_Position = vec4(x_pos, y_pos, 0.0, 1.0);
}
