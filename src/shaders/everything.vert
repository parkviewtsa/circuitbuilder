/*
 * Copyright (C) 2014 Brent Bessemer, Seth Carter.
 * See LICENSE for licensing info.
 */

/* We only need one vertex shader. All it has to do is transform the screen
 * coordinates into viewport coordinates. */

attribute vec2 v_pos;
varying vec2 pos;

void main (void)
{
  pos = v_pos;
  gl_Position = v_pos;
}
