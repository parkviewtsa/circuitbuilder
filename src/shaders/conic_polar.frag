/*
 * This version should have the stroke width problem corrected.
 */

uniform vec4 stroke_color;
uniform vec4 fill_color;
uniform mat4 transform; // covers location and rotation
uniform float a; // semi-major axis (aka radius)
uniform float e; // eccentricity

varying vec4 sc_pos;

void main (void)
{
  // This is temporary.
  transform = mat4(
    1.0, 0.0, 0.0, 0.0,
    0.0, 1.0, 0.0, 0.0,
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
  )
  // A trailing underscore is my equivalent of a prime symbol.
  vec2 pos_ = (inverse(transform)*sc_pos).xy;
  float d = length(pos_)
  float r;
  if (e == 0) {
    r = a;
  } else {
    float p;
    if (e < 1.0) {
      p = a*(1-e*e)/e;
    } else { // e > 1.0
      p = a*(e*e-1)/e;
    }
    r = e*p/(1-(dot(pos_, vec2(1.0, 0.0))/d);
  }
  if (abs(d-r) < stroke_width/2) {
    gl_FragColor = stroke_color;
  } else if (r-d > stroke_width) {
    gl_FragColor = fill_color;
  } else {
    gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
  }
}
