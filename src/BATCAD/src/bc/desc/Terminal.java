package bc.desc;

import bc.error.connection.*;


public enum TerminalType {
  POSITIVE, NEGATIVE
}

public class Connection {
  Circuit circ;
  Component component;
  TerminalType type;
  float xPos; // relative to centre of component
  float yPos;
  int wire;

  public Terminal (Component comp, Circuit c) {
    component = comp;
    circ = c;
    wire = -1; // Default value indicating not connected
  }

  public void connect (Terminal to, boolean allowOverride)
  throws InvalidPolarityError, ConnectionOverrideError {
    if (type == to.type) {
      throw new InvalidPolarityError(component.abbr);
    } else if (wire != -1 || to.wire != -1 && !allowOverride) {
      throw new ConnectionOverrideError();
    } else {
      if (to.wire != -1) {
        wire = to.wire;
      } else {
        wire = circ.wireCount;
        to.wire = circ.wireCount;
        circ.wireCount++;
      }
    }
  }
}
