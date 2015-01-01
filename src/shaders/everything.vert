/*
 * Copyright (C) 2014 Brent Bessemer, Seth Carter.
 *
 * This file is part of BATCAD, which is licensed under the GNU General Public
 * Licence. See LICENCE for more information. Please note that if you use this
 * file in your own project in accordance with the terms of the GNU GPL, you
 * must include the first section of LICENCE in this comment.
 */

/* We only need one vertex shader. All it has to do is transform the screen
 * coordinates into viewport coordinates. */

uniform mat4 scvp; // transforms screen to viewport coords
attribute vec2 in_pos; // uploaded from the CPU
varying vec2 sc_pos; // sent to the fragment shader

void main (void)
{
  sc_pos = vec4(v_pos, 0.0, 1.0);
  gl_Position = scvp*sc_pos;
}
