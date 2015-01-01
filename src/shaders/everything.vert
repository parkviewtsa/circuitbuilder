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

attribute vec2 v_pos;
varying vec2 pos;

void main (void)
{
  pos = v_pos;
  gl_Position = v_pos;
}
