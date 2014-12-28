package bc.desc;

import bc.error.*;

public class Terminal {
  Circuit circ;
  Component component;
  public TerminalType type;
  public float xPos; // relative to centre of component
  public float yPos;
  public Terminal dest;

  public Terminal (Component comp, Circuit c) {
    component = comp;
    circ = c;
  }

  public void connect (Terminal to, boolean allowOverride)
  throws InvalidPolarityError, ConnectionOverrideError
  {
    if (type == to.type) throw new InvalidPolarityError(component.abbr);
    else if (dest || to.dest && !allowOverride) throw new ConnectionOverrideError();
    else
    {
      dest = to;
      to.dest = this;
    };
  };
  public float getGlobalXPos () {
    return xPos + component.xPos;
  }

  public float getGlobalYPos () {
    return yPos + component.yPos;
  }
}
