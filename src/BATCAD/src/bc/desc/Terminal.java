package bc.desc;

import bc.error.connection.*;


public enum TerminalType {
  POSITIVE, NEGATIVE
}

public class Terminal {
  Circuit circ;
  Component component;
  TerminalType type;
  float xPos; // relative to centre of component
  float yPos;
  Terminal dest;

  public Terminal (Component comp, Circuit c) {
    component = comp;
    circ = c;
  }

  public void connect (Terminal to, boolean allowOverride)
  throws InvalidPolarityError, ConnectionOverrideError {
    if (type == to.type) {
      throw new InvalidPolarityError(component.abbr);
    } else if (dest || to.dest && !allowOverride) {
      throw new ConnectionOverrideError();
    } else {
      dest = to;
      to.dest = this;
    }
  }
}
